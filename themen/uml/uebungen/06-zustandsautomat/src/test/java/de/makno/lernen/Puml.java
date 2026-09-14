package de.makno.lernen;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.regex.Pattern;
import net.sourceforge.plantuml.BlockUml;
import net.sourceforge.plantuml.SourceStringReader;
import net.sourceforge.plantuml.core.Diagram;
import net.sourceforge.plantuml.error.PSystemError;

/**
 * Liest die diagramm.puml des Lernenden, lässt PlantUML sie parsen und bietet einfache
 * Strukturprüfungen. Gehört zum Test – nicht anfassen.
 */
final class Puml {

    /** Optionale Multiplizität in Anführungszeichen samt Leerraum, z. B. {@code "1" }. */
    private static final String MULT = "(\\s*\"[^\"]*\")?\\s*";

    private final String text;
    private final List<String> zeilen;

    private Puml(String text) {
        this.text = text;
        this.zeilen = text.lines().map(String::strip).filter(z -> !z.isEmpty() && !z.startsWith("'")).toList();
    }

    static Puml laden() throws IOException {
        return new Puml(Files.readString(Path.of("diagramm.puml"), StandardCharsets.UTF_8));
    }

    /** Diagrammtyp laut PlantUML ("ClassDiagram", "SequenceDiagram", "StateDiagram" …); bei Syntaxfehlern die Meldung. */
    String typ() {
        for (BlockUml block : new SourceStringReader(text).getBlocks()) {
            Diagram diagramm = block.getDiagram();
            if (diagramm instanceof PSystemError fehler) {
                return "Syntaxfehler: " + fehler.getFirstError().getError()
                        + " (Zeile " + (fehler.getFirstError().getPosition() + 1) + ")";
            }
            return diagramm.getClass().getSimpleName();
        }
        return "leer";
    }

    boolean syntaxOk() {
        String typ = typ();
        return !typ.startsWith("Syntaxfehler") && !typ.equals("leer");
    }

    /** Gibt es eine Zeile, auf die der reguläre Ausdruck passt (Groß/Klein egal)? */
    boolean hatZeile(String regex) {
        Pattern p = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        return zeilen.stream().anyMatch(z -> p.matcher(z).find());
    }

    long anzahl(String regex) {
        Pattern p = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        return zeilen.stream().filter(z -> p.matcher(z).find()).count();
    }

    /** Deklaration {@code class Name}, {@code abstract class Name}, {@code interface Name} oder {@code state Name}. */
    boolean hatElement(String schluesselwort, String name) {
        return hatZeile("^(abstract\\s+)?" + schluesselwort + "\\s+\"?" + name + "\"?(\\s|\\{|$)");
    }

    /** Beziehung {@code a pfeil b} in Schreibrichtung; Multiplizitäten in Anführungszeichen dürfen dazwischen stehen. */
    boolean hatBeziehung(String a, String pfeil, String b) {
        return hatZeile("^" + a + MULT + pfeil + MULT + b + "(\\s|:|$)");
    }

    /** Beziehung in beliebiger Schreibrichtung – für Linien ohne Richtung (Assoziation, Komposition). */
    boolean hatBeziehungBeliebig(String a, String pfeilHin, String pfeilZurueck, String b) {
        return hatBeziehung(a, pfeilHin, b) || hatBeziehung(b, pfeilZurueck, a);
    }

    /** Multiplizität am Ende von {@code b} in der Zeile {@code a … b}, z. B. {@code "*"} oder {@code "1..*"}. */
    boolean hatMultiplizitaet(String a, String b, String mult) {
        String m = Pattern.quote("\"" + mult + "\"");
        return hatZeile("^" + a + MULT + "[-.o*<|>]+\\s*" + m + "\\s*" + b + "(\\s|:|$)")
                || hatZeile("^" + b + "\\s*" + m + "\\s*[-.o*<|>]+" + MULT + a + "(\\s|:|$)");
    }

    /** Text des Diagramms ohne Kommentarzeilen. */
    List<String> zeilen() {
        return zeilen;
    }
}

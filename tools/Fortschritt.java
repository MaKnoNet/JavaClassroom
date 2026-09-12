import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * Erzeugt die Fortschrittsseite: liest fortschritt/<name>.md und alle themen/<x>/lehrplan.md,
 * baut JSON und setzt es in tools/fortschritt.template.html ein.
 * Aufruf im Repo-Root: java tools/Fortschritt.java <name>
 * Keine Abhängigkeiten, Java 21.
 */
public final class Fortschritt {

    record Lektion(String nummer, String titel, String uebung) {}
    record Lehrplan(String thema, String titel, List<String> voraussetzungen, List<Lektion> lektionen) {}
    record LektionsStand(String nummer, String status, String datum) {}
    record ThemenStand(List<LektionsStand> lektionen, String notizen) {}
    record Profil(String name, String sprache, String rolle, String ide, String vorwissen) {}
    record Stand(Profil profil, Map<String, ThemenStand> themen) {}

    private static final Pattern LEKTION = Pattern.compile("^### (\\d{2}) (.+)$");
    private static final Pattern UEBUNG = Pattern.compile("^- \\*\\*Übung:\\*\\* (.+)$");
    private static final Pattern THEMA = Pattern.compile("^## (\\S+)$");
    private static final Pattern LEKTIONS_STAND =
            Pattern.compile("^- (\\d{2}): (fertig|begonnen) (\\d{4}-\\d{2}-\\d{2})$");
    private static final Pattern NOTIZEN = Pattern.compile("^- notizen: (.*)$");

    /** Liest die YAML-Frontmatter zwischen den beiden ersten `---`-Zeilen als flache Schlüssel/Wert-Paare. */
    static Map<String, String> parseFrontmatter(String markdown) {
        Map<String, String> felder = new LinkedHashMap<>();
        String[] zeilen = markdown.split("\\R");
        if (zeilen.length == 0 || !zeilen[0].trim().equals("---")) {
            return felder;
        }
        for (int i = 1; i < zeilen.length; i++) {
            String zeile = zeilen[i];
            if (zeile.trim().equals("---")) {
                break;
            }
            int doppelpunkt = zeile.indexOf(':');
            if (doppelpunkt < 0) {
                continue;
            }
            String schluessel = zeile.substring(0, doppelpunkt).trim();
            String wert = zeile.substring(doppelpunkt + 1).trim();
            int kommentar = wert.indexOf(" #");
            if (kommentar >= 0) {
                wert = wert.substring(0, kommentar).trim();
            }
            if (wert.length() >= 2 && wert.startsWith("\"") && wert.endsWith("\"")) {
                wert = wert.substring(1, wert.length() - 1);
            }
            felder.put(schluessel, wert);
        }
        return felder;
    }

    /** `[a, b]` → Liste; leer oder null → leere Liste. */
    static List<String> parseListe(String wert) {
        if (wert == null) {
            return List.of();
        }
        String inhalt = wert.trim();
        if (inhalt.startsWith("[")) {
            inhalt = inhalt.substring(1);
        }
        if (inhalt.endsWith("]")) {
            inhalt = inhalt.substring(0, inhalt.length() - 1);
        }
        List<String> ergebnis = new ArrayList<>();
        for (String teil : inhalt.split(",")) {
            String bereinigt = teil.trim();
            if (!bereinigt.isEmpty()) {
                ergebnis.add(bereinigt);
            }
        }
        return ergebnis;
    }

    static Lehrplan parseLehrplan(String markdown) {
        Map<String, String> frontmatter = parseFrontmatter(markdown);
        List<Lektion> lektionen = new ArrayList<>();
        String nummer = null;
        String titel = null;
        String uebung = null;
        for (String zeile : markdown.split("\\R")) {
            Matcher lektion = LEKTION.matcher(zeile.strip());
            if (lektion.matches()) {
                if (nummer != null) {
                    lektionen.add(new Lektion(nummer, titel, uebung));
                }
                nummer = lektion.group(1);
                titel = lektion.group(2).trim();
                uebung = null;
                continue;
            }
            Matcher uebungsZeile = UEBUNG.matcher(zeile.strip());
            if (uebungsZeile.matches() && nummer != null) {
                uebung = uebungsZeile.group(1).trim();
            }
        }
        if (nummer != null) {
            lektionen.add(new Lektion(nummer, titel, uebung));
        }
        return new Lehrplan(
                frontmatter.get("thema"),
                frontmatter.get("titel"),
                parseListe(frontmatter.get("voraussetzungen")),
                lektionen);
    }

    static Stand parseStand(String markdown) {
        Map<String, String> frontmatter = parseFrontmatter(markdown);
        Profil profil = new Profil(
                frontmatter.get("name"),
                frontmatter.get("sprache"),
                frontmatter.get("rolle"),
                frontmatter.get("ide"),
                frontmatter.get("vorwissen"));
        Map<String, ThemenStand> themen = new LinkedHashMap<>();
        String thema = null;
        List<LektionsStand> lektionen = null;
        String notizen = null;
        for (String rohZeile : markdown.split("\\R")) {
            String zeile = rohZeile.strip();
            Matcher themaZeile = THEMA.matcher(zeile);
            if (themaZeile.matches()) {
                if (thema != null) {
                    themen.put(thema, new ThemenStand(lektionen, notizen));
                }
                thema = themaZeile.group(1);
                lektionen = new ArrayList<>();
                notizen = null;
                continue;
            }
            if (thema == null) {
                continue;
            }
            Matcher standZeile = LEKTIONS_STAND.matcher(zeile);
            if (standZeile.matches()) {
                lektionen.add(new LektionsStand(standZeile.group(1), standZeile.group(2), standZeile.group(3)));
                continue;
            }
            Matcher notizZeile = NOTIZEN.matcher(zeile);
            if (notizZeile.matches()) {
                notizen = notizZeile.group(1).trim();
            }
        }
        if (thema != null) {
            themen.put(thema, new ThemenStand(lektionen, notizen));
        }
        return new Stand(profil, themen);
    }
}

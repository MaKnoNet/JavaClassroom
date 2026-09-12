import java.util.List;
import java.util.Objects;

/** Tests für Fortschritt.java. Aufruf siehe Plan: javac + java -cp build/tools FortschrittTest */
public final class FortschrittTest {

    private static int fehler = 0;

    public static void main(String[] args) {
        lehrplanMitZweiLektionen();
        lehrplanOhneUebung();
        if (fehler > 0) {
            System.err.println(fehler + " Test(s) fehlgeschlagen");
            System.exit(1);
        }
        System.out.println("Alle Tests bestanden");
    }

    static void lehrplanMitZweiLektionen() {
        String md = """
            ---
            thema: java
            titel: Java
            voraussetzungen: [git, gradle]
            zielgruppe: [azubi]
            ---

            # Java

            ## Lektionen

            ### 01 Erste Klasse
            - **Ziele:** Klasse verstehen
            - **Übung:** uebungen/01-erste-klasse
            - **Prüffrage:** Was ist eine Klasse?

            ### 02 Variablen
            - **Ziele:** Typen
            - **Übung:** uebungen/02-variablen
            - **Prüffrage:** int oder Integer?
            """;
        Fortschritt.Lehrplan plan = Fortschritt.parseLehrplan(md);
        pruefe("thema", "java", plan.thema());
        pruefe("titel", "Java", plan.titel());
        pruefe("voraussetzungen", List.of("git", "gradle"), plan.voraussetzungen());
        pruefe("anzahl lektionen", 2, plan.lektionen().size());
        pruefe("nummer 01", "01", plan.lektionen().get(0).nummer());
        pruefe("titel 01", "Erste Klasse", plan.lektionen().get(0).titel());
        pruefe("uebung 01", "uebungen/01-erste-klasse", plan.lektionen().get(0).uebung());
        pruefe("titel 02", "Variablen", plan.lektionen().get(1).titel());
    }

    static void lehrplanOhneUebung() {
        String md = """
            ---
            thema: git
            titel: Git
            voraussetzungen: []
            ---
            ### 01 Warum Versionierung
            - **Ziele:** Motivation
            - **Prüffrage:** Wozu?
            """;
        Fortschritt.Lehrplan plan = Fortschritt.parseLehrplan(md);
        pruefe("leere voraussetzungen", List.of(), plan.voraussetzungen());
        pruefe("uebung fehlt", null, plan.lektionen().get(0).uebung());
    }

    static void pruefe(String name, Object erwartet, Object tatsaechlich) {
        if (!Objects.equals(erwartet, tatsaechlich)) {
            fehler++;
            System.err.println("FEHLER " + name + ": erwartet <" + erwartet + ">, war <" + tatsaechlich + ">");
        }
    }
}

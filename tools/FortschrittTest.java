import java.util.List;
import java.util.Objects;

/** Tests für Fortschritt.java. Aufruf siehe Plan: javac + java -cp build/tools FortschrittTest */
public final class FortschrittTest {

    private static int fehler = 0;

    public static void main(String[] args) {
        lehrplanMitZweiLektionen();
        lehrplanOhneUebung();
        standMitZweiThemen();
        standOhneThemen();
        jsonEscaping();
        jsonGesamt();
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

    static void standMitZweiThemen() {
        String md = """
            ---
            name: Max Mustermann
            sprache: de
            rolle: azubi
            ide: eclipse
            vorwissen: "Erstes Lehrjahr, kein Git."
            ---

            ## java
            - 01: fertig 2026-09-10
            - 03: begonnen 2026-09-12
            - notizen: Referenz vs. Wert wiederholen.

            ## git
            - 01: fertig 2026-09-11
            """;
        Fortschritt.Stand stand = Fortschritt.parseStand(md);
        pruefe("name", "Max Mustermann", stand.profil().name());
        pruefe("sprache", "de", stand.profil().sprache());
        pruefe("ide", "eclipse", stand.profil().ide());
        pruefe("vorwissen ohne anfuehrungszeichen", "Erstes Lehrjahr, kein Git.", stand.profil().vorwissen());
        pruefe("anzahl themen", 2, stand.themen().size());
        Fortschritt.ThemenStand java = stand.themen().get("java");
        pruefe("java lektionen", 2, java.lektionen().size());
        pruefe("java 01 status", "fertig", java.lektionen().get(0).status());
        pruefe("java 01 datum", "2026-09-10", java.lektionen().get(0).datum());
        pruefe("java 03 status", "begonnen", java.lektionen().get(1).status());
        pruefe("java notizen", "Referenz vs. Wert wiederholen.", java.notizen());
        pruefe("git notizen leer", null, stand.themen().get("git").notizen());
    }

    static void standOhneThemen() {
        String md = """
            ---
            name: Neu
            sprache: fr
            rolle: student
            ide: keine
            vorwissen: "nichts"
            ---
            """;
        Fortschritt.Stand stand = Fortschritt.parseStand(md);
        pruefe("keine themen", 0, stand.themen().size());
        pruefe("sprache fr", "fr", stand.profil().sprache());
    }

    static void jsonEscaping() {
        pruefe("null", "null", Fortschritt.json((String) null));
        pruefe("anfuehrungszeichen", "\"a\\\"b\"", Fortschritt.json("a\"b"));
        pruefe("backslash", "\"a\\\\b\"", Fortschritt.json("a\\b"));
        pruefe("schraegstrich gegen </script>", "\"<\\/script>\"", Fortschritt.json("</script>"));
        pruefe("zeilenumbruch", "\"a\\nb\"", Fortschritt.json("a\nb"));
        pruefe("liste", "[\"git\",\"java\"]", Fortschritt.json(List.of("git", "java")));
    }

    static void jsonGesamt() {
        Fortschritt.Lehrplan plan = new Fortschritt.Lehrplan("java", "Java", List.of("git"),
                List.of(new Fortschritt.Lektion("01", "Erste Klasse", "uebungen/01-erste-klasse"),
                        new Fortschritt.Lektion("02", "Variablen", null)));
        Fortschritt.Stand stand = new Fortschritt.Stand(
                new Fortschritt.Profil("Max", "de", "azubi", "eclipse", "nichts"),
                java.util.Map.of("java", new Fortschritt.ThemenStand(
                        List.of(new Fortschritt.LektionsStand("01", "fertig", "2026-09-10")), "Notiz")));
        String json = Fortschritt.toJson(List.of(plan), stand);
        String erwartet = "{\"profil\":{\"name\":\"Max\",\"sprache\":\"de\",\"rolle\":\"azubi\",\"ide\":\"eclipse\",\"vorwissen\":\"nichts\"},"
                + "\"lehrplaene\":[{\"thema\":\"java\",\"titel\":\"Java\",\"voraussetzungen\":[\"git\"],\"lektionen\":["
                + "{\"nummer\":\"01\",\"titel\":\"Erste Klasse\",\"uebung\":\"uebungen\\/01-erste-klasse\"},"
                + "{\"nummer\":\"02\",\"titel\":\"Variablen\",\"uebung\":null}]}],"
                + "\"stand\":{\"java\":{\"lektionen\":[{\"nummer\":\"01\",\"status\":\"fertig\",\"datum\":\"2026-09-10\"}],\"notizen\":\"Notiz\"}}}";
        pruefe("json gesamt", erwartet, json);
    }

    static void pruefe(String name, Object erwartet, Object tatsaechlich) {
        if (!Objects.equals(erwartet, tatsaechlich)) {
            fehler++;
            System.err.println("FEHLER " + name + ": erwartet <" + erwartet + ">, war <" + tatsaechlich + ">");
        }
    }
}

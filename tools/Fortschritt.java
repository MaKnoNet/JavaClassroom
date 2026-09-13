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

    record Lektion(String nummer, String titel, String uebung, Map<String, String> uebersetzungen) {}
    record Lehrplan(String thema, String titel, List<String> voraussetzungen, List<Lektion> lektionen) {}
    record LektionsStand(String nummer, String status, String datum) {}
    record ThemenStand(List<LektionsStand> lektionen, String notizen) {}
    record Profil(String name, String sprache, String rolle, String niveau, String ide, String vorwissen) {}
    record Stand(Profil profil, Map<String, ThemenStand> themen) {}

    private static final Pattern LEKTION = Pattern.compile("^### (\\d{2}) (.+)$");
    private static final Pattern UEBUNG = Pattern.compile("^- \\*\\*Übung:\\*\\* (.+)$");
    private static final Pattern UEBERSETZUNG = Pattern.compile("^- \\*\\*Übersetzung:\\*\\* (.+)$");
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

    /** `en: Title | fr: Titre` → Map; Sprachcode ist alles vor dem ersten Doppelpunkt. */
    static Map<String, String> parseUebersetzungen(String wert) {
        Map<String, String> ergebnis = new LinkedHashMap<>();
        for (String teil : wert.split("\\|")) {
            int doppelpunkt = teil.indexOf(':');
            if (doppelpunkt < 0) {
                continue;
            }
            String code = teil.substring(0, doppelpunkt).trim();
            String titel = teil.substring(doppelpunkt + 1).trim();
            if (!code.isEmpty() && !titel.isEmpty()) {
                ergebnis.put(code, titel);
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
        Map<String, String> uebersetzungen = new LinkedHashMap<>();
        for (String zeile : markdown.split("\\R")) {
            Matcher lektion = LEKTION.matcher(zeile.strip());
            if (lektion.matches()) {
                if (nummer != null) {
                    lektionen.add(new Lektion(nummer, titel, uebung, uebersetzungen));
                }
                nummer = lektion.group(1);
                titel = lektion.group(2).trim();
                uebung = null;
                uebersetzungen = new LinkedHashMap<>();
                continue;
            }
            if (nummer == null) {
                continue;
            }
            Matcher uebungsZeile = UEBUNG.matcher(zeile.strip());
            if (uebungsZeile.matches()) {
                uebung = uebungsZeile.group(1).trim();
                continue;
            }
            Matcher uebersetzungsZeile = UEBERSETZUNG.matcher(zeile.strip());
            if (uebersetzungsZeile.matches()) {
                uebersetzungen = parseUebersetzungen(uebersetzungsZeile.group(1));
            }
        }
        if (nummer != null) {
            lektionen.add(new Lektion(nummer, titel, uebung, uebersetzungen));
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
                frontmatter.get("niveau"),
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

    static String json(String wert) {
        if (wert == null) {
            return "null";
        }
        StringBuilder sb = new StringBuilder("\"");
        for (char c : wert.toCharArray()) {
            switch (c) {
                case '"' -> sb.append("\\\"");
                case '\\' -> sb.append("\\\\");
                case '\n' -> sb.append("\\n");
                case '\r' -> sb.append("\\r");
                case '\t' -> sb.append("\\t");
                case '/' -> sb.append("\\/"); // verhindert </script> im eingebetteten JSON
                default -> {
                    if (c < 0x20) {
                        sb.append(String.format("\\u%04x", (int) c));
                    } else {
                        sb.append(c);
                    }
                }
            }
        }
        return sb.append('"').toString();
    }

    static String json(List<String> werte) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < werte.size(); i++) {
            if (i > 0) {
                sb.append(',');
            }
            sb.append(json(werte.get(i)));
        }
        return sb.append(']').toString();
    }

    static String jsonObjekt(Map<String, String> werte) {
        StringBuilder sb = new StringBuilder("{");
        boolean erstes = true;
        for (Map.Entry<String, String> eintrag : werte.entrySet()) {
            if (!erstes) {
                sb.append(',');
            }
            erstes = false;
            sb.append(json(eintrag.getKey())).append(':').append(json(eintrag.getValue()));
        }
        return sb.append('}').toString();
    }

    static String toJson(List<Lehrplan> lehrplaene, Stand stand) {
        StringBuilder sb = new StringBuilder("{\"profil\":{");
        Profil p = stand.profil();
        sb.append("\"name\":").append(json(p.name()))
          .append(",\"sprache\":").append(json(p.sprache()))
          .append(",\"rolle\":").append(json(p.rolle()))
          .append(",\"niveau\":").append(json(p.niveau()))
          .append(",\"ide\":").append(json(p.ide()))
          .append(",\"vorwissen\":").append(json(p.vorwissen()))
          .append("},\"lehrplaene\":[");
        for (int i = 0; i < lehrplaene.size(); i++) {
            Lehrplan plan = lehrplaene.get(i);
            if (i > 0) {
                sb.append(',');
            }
            sb.append("{\"thema\":").append(json(plan.thema()))
              .append(",\"titel\":").append(json(plan.titel()))
              .append(",\"voraussetzungen\":").append(json(plan.voraussetzungen()))
              .append(",\"lektionen\":[");
            for (int j = 0; j < plan.lektionen().size(); j++) {
                Lektion l = plan.lektionen().get(j);
                if (j > 0) {
                    sb.append(',');
                }
                sb.append("{\"nummer\":").append(json(l.nummer()))
                  .append(",\"titel\":").append(json(l.titel()))
                  .append(",\"uebung\":").append(json(l.uebung()))
                  .append(",\"uebersetzungen\":").append(jsonObjekt(l.uebersetzungen()))
                  .append('}');
            }
            sb.append("]}");
        }
        sb.append("],\"stand\":{");
        boolean erstes = true;
        for (Map.Entry<String, ThemenStand> eintrag : stand.themen().entrySet()) {
            if (!erstes) {
                sb.append(',');
            }
            erstes = false;
            sb.append(json(eintrag.getKey())).append(":{\"lektionen\":[");
            List<LektionsStand> lektionen = eintrag.getValue().lektionen();
            for (int j = 0; j < lektionen.size(); j++) {
                LektionsStand s = lektionen.get(j);
                if (j > 0) {
                    sb.append(',');
                }
                sb.append("{\"nummer\":").append(json(s.nummer()))
                  .append(",\"status\":").append(json(s.status()))
                  .append(",\"datum\":").append(json(s.datum()))
                  .append('}');
            }
            sb.append("],\"notizen\":").append(json(eintrag.getValue().notizen())).append('}');
        }
        return sb.append("}}").toString();
    }

    private static final String PLATZHALTER = "/*DATEN*/{}";

    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            abbruch("Aufruf: java tools/Fortschritt.java <name>   (im Repo-Root)");
        }
        Path themenOrdner = Path.of("themen");
        Path fortschrittDatei = Path.of("fortschritt", args[0] + ".md");
        Path vorlage = Path.of("tools", "fortschritt.template.html");
        Path ziel = Path.of("arbeit", "fortschritt.html");
        if (!Files.isDirectory(themenOrdner)) {
            abbruch("Ordner 'themen' nicht gefunden – bitte im Repo-Root starten.");
        }
        if (!Files.exists(fortschrittDatei)) {
            abbruch("Fortschrittsdatei fehlt: " + fortschrittDatei);
        }
        List<Lehrplan> lehrplaene = new ArrayList<>();
        try (Stream<Path> ordner = Files.list(themenOrdner)) {
            for (Path thema : ordner.sorted().toList()) {
                Path lehrplan = thema.resolve("lehrplan.md");
                if (thema.getFileName().toString().startsWith("_") || !Files.exists(lehrplan)) {
                    continue;
                }
                lehrplaene.add(parseLehrplan(Files.readString(lehrplan, StandardCharsets.UTF_8)));
            }
        }
        Stand stand = parseStand(Files.readString(fortschrittDatei, StandardCharsets.UTF_8));
        String html = Files.readString(vorlage, StandardCharsets.UTF_8);
        if (!html.contains(PLATZHALTER)) {
            abbruch("Platzhalter " + PLATZHALTER + " fehlt in " + vorlage);
        }
        Files.createDirectories(ziel.getParent());
        Files.writeString(ziel, html.replace(PLATZHALTER, toJson(lehrplaene, stand)), StandardCharsets.UTF_8);
        System.out.println("Geschrieben: " + ziel.toAbsolutePath());
    }

    private static void abbruch(String meldung) {
        System.err.println(meldung);
        System.exit(2);
    }
}

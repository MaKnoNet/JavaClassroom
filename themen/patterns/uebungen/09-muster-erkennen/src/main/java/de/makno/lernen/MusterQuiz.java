package de.makno.lernen;

/**
 * Teil 1: Welches Muster steckt in der Code-Stelle? Trage je Fall das passende Muster ein.
 * Die Stellen stammen aus dem JDK und aus Spring – Code, den du täglich benutzt.
 */
public final class MusterQuiz {

    private MusterQuiz() {}

    public static Muster musterVon(String codeStelle) {
        return switch (codeStelle) {
            case "liste.sort(Comparator.comparing(Kunde::name))" -> null;
            case "new BufferedReader(new InputStreamReader(System.in))" -> null;
            case "HttpRequest.newBuilder().uri(uri).GET().build()" -> null;
            case "button.addClickListener(e -> speichern())" -> null;
            case "jdbcTemplate.query(sql, (rs, zeile) -> new Kunde(rs.getString(1)))" -> null;
            case "@Transactional public void ueberweise(...) auf einer Spring-Bean" -> null;
            case "List.of(1, 2, 3)" -> null;
            case "for (Kunde k : kunden) { ... }" -> null;
            default -> throw new IllegalArgumentException("Unbekannte Code-Stelle: " + codeStelle);
        };
    }
}

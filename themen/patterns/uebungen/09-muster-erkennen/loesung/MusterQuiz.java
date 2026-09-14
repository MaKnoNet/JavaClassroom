package de.makno.lernen;

public final class MusterQuiz {

    private MusterQuiz() {}

    public static Muster musterVon(String codeStelle) {
        return switch (codeStelle) {
            // sort ist der Kontext, der Comparator die austauschbare Strategie
            case "liste.sort(Comparator.comparing(Kunde::name))" -> Muster.STRATEGIE;
            // zwei Hüllen um denselben Typ Reader – Verhalten hinzugefügt, Interface behalten
            case "new BufferedReader(new InputStreamReader(System.in))" -> Muster.DEKORIERER;
            // schrittweise Erzeugung mit build() am Ende
            case "HttpRequest.newBuilder().uri(uri).GET().build()" -> Muster.BUILDER;
            // registriere-Methode, Ereignis, Listener, der nichts vom Button weiß
            case "button.addClickListener(e -> speichern())" -> Muster.BEOBACHTER;
            // fester Ablauf (Verbindung, Statement, Schleife, Schließen), variabler Schritt (RowMapper)
            case "jdbcTemplate.query(sql, (rs, zeile) -> new Kunde(rs.getString(1)))" -> Muster.SCHABLONENMETHODE;
            // Spring stellt ein Objekt gleichen Typs davor, das die Transaktion öffnet und schließt
            case "@Transactional public void ueberweise(...) auf einer Spring-Bean" -> Muster.PROXY;
            // statische Fabrikmethode: sprechender Name, liefert ein Interface, Implementierung verborgen
            case "List.of(1, 2, 3)" -> Muster.FABRIKMETHODE;
            // die for-each-Schleife ist Zucker um iterator()/hasNext()/next()
            case "for (Kunde k : kunden) { ... }" -> Muster.ITERATOR;
            default -> throw new IllegalArgumentException("Unbekannte Code-Stelle: " + codeStelle);
        };
    }
}

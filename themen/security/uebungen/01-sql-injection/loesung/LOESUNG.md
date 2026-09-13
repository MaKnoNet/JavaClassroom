# Lösung 01: SQL-Injection

`KundenSuche.java`: `Statement` → `PreparedStatement`, SQL als Konstante mit `?`,
`setString(1, name)`.

Warum das nicht nur „funktioniert", sondern *sicher* ist: Beim `PreparedStatement`
sendet der Treiber den SQL-Text und die Parameterwerte getrennt. Die Datenbank
kompiliert die Abfrage, *bevor* sie die Werte sieht – ein Wert kann die Struktur nicht
mehr ändern. Das ist etwas anderes als Maskieren (`'` → `''`): Maskieren repariert den
Text, Parametrisieren trennt Code von Daten.

Nebeneffekt: Die Datenbank kann den Ausführungsplan der konstanten Abfrage
wiederverwenden – parametrisierte Abfragen sind auch schneller.

Was die Tests im Startzustand zeigen (zum Vorführen):

| Eingabe | Ergebnis mit `+` |
|---|---|
| `O'Brien` | `JdbcSQLSyntaxErrorException` |
| `' OR '1'='1` | alle drei Kunden |
| `x'; delete from kunde; --` | Tabelle leer |

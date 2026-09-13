# Lösung 10

`loesung/aufgabe.sql` (PostgreSQL, SQLite): ein Ausdrucksindex über `LOWER(nachname)`.
`loesung/aufgabe-h2.sql` (H2): berechnete Spalte `nachname_klein` plus Index.

Die Pläne nach dem Fix: PostgreSQL `Bitmap Heap Scan … idx_kunde_nachname_klein`, SQLite
`SEARCH kunde USING INDEX idx_kunde_nachname_klein (<expr>=?)`, H2
`/* PUBLIC.IDX_KUNDE_NACHNAME_KLEIN: NACHNAME_KLEIN = 'mueller421' */`.

Was der Ausdrucksindex kostet: wie jeder Index Platz und Schreibarbeit – und er hilft nur
Abfragen, die *genau denselben Ausdruck* verwenden. `LOWER(nachname) = …` trifft ihn,
`UPPER(nachname) = …` nicht. Deshalb im Team festlegen, wie gesucht wird, und den
Ausdruck an einer Stelle definieren (Sicht oder berechnete Spalte).

Die Alternative, die oft besser ist: Daten beim Schreiben normalisieren – eine Spalte
`nachname_suche`, immer klein, vom Import und von der Anwendung gefüllt (H2 erzwingt
diesen Weg). Dann reicht ein normaler Index, und jede Abfrage ist automatisch SARGable.

Zur Prüffrage der Lektion („Die Spalte `nachname` ist indiziert – warum ist
`WHERE LOWER(nachname) = 'mueller'` trotzdem ein Seq Scan?"): Weil der Index die
gespeicherten Werte enthält, nicht ihre Kleinschreibung; die Datenbank müsste jeden
Eintrag umrechnen. Korrektur: Ausdrucksindex oder normalisierte Spalte – nie die Abfrage
„irgendwie umschreiben".

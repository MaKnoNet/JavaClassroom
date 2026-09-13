# Lösung 07

Zwei Anweisungen, siehe `loesung/aufgabe.sql`. Die eigentliche Arbeit ist das Lesen der
Pläne (Tabelle in `AUFGABE.md`).

Was ein Index ist: ein sortierter Baum (B-Baum) über die Werte einer Spalte mit Verweisen
auf die Zeilen. Suche in einem Baum kostet log(n) Schritte – bei 50 000 Zeilen etwa 16
statt 50 000. Der Preis: Jeder Schreibzugriff auf die Spalte muss den Baum mitpflegen, und
der Index braucht Platz. Deshalb nicht auf jede Spalte.

Wann er lohnt: hohe Selektivität (wenige Treffer aus vielen Zeilen), Spalten in `WHERE`,
`JOIN … ON` und `ORDER BY`. Wann nicht: kleine Tabellen, Spalten mit wenigen Werten
(`status`), Tabellen mit sehr vielen Schreibzugriffen und seltenen Abfragen.

Primärschlüssel und `UNIQUE` bekommen automatisch einen Index – in jeder Datenbank.
Fremdschlüssel nur in manchen (H2, MySQL), nicht in PostgreSQL und SQLite. Spring-Lektion 06
(N+1) ist die Fortsetzung: Dort ist das Problem nicht der fehlende Index, sondern die
Anzahl der Abfragen – beides sieht man nur, wenn man hinschaut (`EXPLAIN`, SQL-Log).

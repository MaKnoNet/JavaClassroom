# Übung 02: Daten lesen

## Aufgabe

`start.sql` legt sechs Kunden und zehn Artikel an. Schreibe in `aufgabe.sql` sechs Abfragen
als Sichten `aufgabe_1` … `aufgabe_6` – die Fragen stehen als Kommentar in der Datei.
`aufgabe_1` hat schon einen falschen Anfang (alle Spalten, alle Kunden), den du
verbessern sollst. Ausführen: siehe [AUSFUEHREN.md](../../AUSFUEHREN.md).

## Abnahmekriterien

- `pruefung.sql` gibt keine `Fehler:`-Zeile und keine Fehlermeldung aus.
- Jede Sicht liefert genau die genannten Spalten in der genannten Reihenfolge – nicht `*`.

## Hinweise

1. Erst in der Konsole probieren (`SELECT name, email FROM kunde WHERE aktiv = TRUE;`), dann
   als `CREATE VIEW aufgabe_1 AS …` in die Datei. Eine Sicht ist eine gespeicherte Abfrage,
   keine Kopie der Daten.
2. „Zwischen 10 und 50 einschließlich": `BETWEEN 10 AND 50` – oder `>= 10 AND <= 50`.
3. „Keine E-Mail": `email IS NULL`. `email = NULL` ist *immer* unbekannt und liefert nichts –
   NULL ist kein Wert, sondern das Fehlen eines Werts.
4. Jede Kategorie einmal: `SELECT DISTINCT kategorie …`.
5. Die drei teuersten: `ORDER BY preis DESC LIMIT 3` (PostgreSQL, H2, SQLite, MySQL;
   SQL Server: `SELECT TOP 3 …`, Oracle: `FETCH FIRST 3 ROWS ONLY`).
6. Namen, die mit S beginnen: `name LIKE 'S%'` – `%` steht für beliebig viele Zeichen,
   `_` für genau eines.
7. Sichten wieder loswerden: `start.sql` erneut ausführen, es löscht sie alle.

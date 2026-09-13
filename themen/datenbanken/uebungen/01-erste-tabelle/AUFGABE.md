# Übung 01: Deine erste Tabelle

## Aufgabe

Lege in `aufgabe.sql` die Tabelle `kunde` an und fülle sie mit drei Kunden – die genauen
Spalten und Werte stehen als Kommentar in der Datei. Ausführen: siehe
[AUSFUEHREN.md](../../AUSFUEHREN.md).

## Abnahmekriterien

- `pruefung.sql` gibt keine `Fehler:`-Zeile aus.
- Die letzten zwei `INSERT`s in `pruefung.sql` **scheitern** mit einer Fehlermeldung – das
  ist gewollt: Sie versuchen, einen Kunden ohne Namen und einen mit doppelter `id`
  einzufügen. Nimmt die Datenbank sie an, fehlen `NOT NULL` oder `PRIMARY KEY`.

## Hinweise

1. `CREATE TABLE kunde ( spalte TYP EINSCHRÄNKUNG, … );` – Typen: `INTEGER`, `VARCHAR(100)`,
   `DATE`, `BOOLEAN`, für Geldbeträge später `NUMERIC(10,2)`.
2. `PRIMARY KEY` macht die Spalte eindeutig und zum Pflichtfeld; `NOT NULL` nur Pflichtfeld.
3. `INSERT INTO kunde (id, name, …) VALUES (1, 'Anna Adler', …);` – Text und Datum in
   einfachen Anführungszeichen, Zahlen und `TRUE`/`FALSE` ohne. „Keine E-Mail" heißt `NULL`,
   nicht `''`.
4. Schau dir das Ergebnis in der Konsole an: `SELECT * FROM kunde;` – und die Struktur mit
   `\d kunde` (psql), `.schema kunde` (sqlite3) bzw. in der H2-Konsole links im Baum.

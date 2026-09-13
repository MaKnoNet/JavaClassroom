# Übung 10: Index-Fallen

## Aufgabe

`start.sql` legt 20 000 Kunden und einen Index auf `nachname` an. Drei Abfragen, die alle
„nach Nachname suchen" – und nur eine nutzt den Index. Finde mit `EXPLAIN` heraus, welche,
und mach die zweite (Suche unabhängig von Groß-/Kleinschreibung) schnell. Die Schritte
stehen in `aufgabe.sql`; für die Lösung gibt es zwei Wege, weil H2 keine Ausdrucksindizes
kennt. Ausführen: siehe [AUSFUEHREN.md](../../AUSFUEHREN.md); nach `pruefung.sql` die Datei
für deine Datenbank (`pruefung-postgresql.sql`, `pruefung-sqlite.sql`, `pruefung-h2.sql`).

## Abnahmekriterien

- `pruefung.sql` und die Datei für deine Datenbank geben keine `Fehler:`-Zeile aus.
- Die Daten sind unverändert (20 000 Kunden, Schreibweisen gemischt).
- Du kannst für alle drei Abfragen sagen, ob der Index genutzt wird und warum.

## Hinweise

1. Was du sehen wirst (Abfrage a / b / c):

   | | a) `nachname = …` | b) `LOWER(nachname) = …` | c) `LIKE '%421'` |
   |---|---|---|---|
   | PostgreSQL | `Bitmap Heap Scan` (Index) | `Seq Scan` | `Seq Scan` |
   | SQLite | `SEARCH … USING INDEX` | `SCAN kunde` | `SCAN kunde` |
   | H2 | `/* IDX_KUNDE_NACHNAME */` | `tableScan` | `tableScan` |

2. Warum b) den Index ignoriert: Im Index stehen die Nachnamen *so wie gespeichert* –
   `MUELLER421`, `Mueller421`, `mueller421` an drei verschiedenen Stellen des Baums. Für
   `LOWER(nachname)` müsste die Datenbank jeden Eintrag erst umrechnen; das ist genau das
   Lesen aller Zeilen, das der Index vermeiden soll. Eine Bedingung, die der Index direkt
   beantworten kann, heißt **SARGable** (*search-argument-able*): Spalte unverändert auf
   der einen Seite, Wert auf der anderen.
3. Der Fix: den *Ausdruck* indizieren. PostgreSQL und SQLite:
   `CREATE INDEX idx_kunde_nachname_klein ON kunde (LOWER(nachname));` – die Datenbank
   berechnet `LOWER` beim Schreiben und legt das Ergebnis in den Baum. H2: berechnete
   Spalte `GENERATED ALWAYS AS (LOWER(nachname))` plus Index darauf (Syntax in
   `aufgabe.sql`); die Abfrage nutzt dann die Spalte.
4. Warum c) nicht zu retten ist: `LIKE '%421'` weiß nichts über den Anfang des Werts – der
   Baum ist aber nach dem Anfang sortiert. Ein Telefonbuch hilft nicht, wenn man nur weiß,
   dass der Name auf „…er" endet. Auswege liegen in der Fachlichkeit (Suche nach Anfang:
   `LIKE 'Muel%'` – in PostgreSQL nur mit `text_pattern_ops`-Index) oder in
   Volltextsuche.
5. Dieselbe Falle in anderer Gestalt: `WHERE jahr(datum) = 2026` (Funktion),
   `WHERE preis * 1.19 > 100` (Rechnung), `WHERE CAST(id AS VARCHAR) = '42'`
   (Typumwandlung). Regel: Die Spalte bleibt nackt, gerechnet wird auf der anderen Seite –
   `WHERE datum >= '2026-01-01' AND datum < '2027-01-01'`.

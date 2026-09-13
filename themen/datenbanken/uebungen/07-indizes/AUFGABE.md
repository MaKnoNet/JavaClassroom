# Übung 07: Sichten, Indizes, Ausführungspläne

## Aufgabe

`start.sql` erzeugt 1 000 Kunden und 50 000 Bestellungen – per rekursiver CTE, ohne
50 000 `INSERT`-Zeilen (schau dir an, wie). Lege eine Sicht `offene_bestellungen` an, sieh
dir mit `EXPLAIN` an, wie die Datenbank `WHERE kunde_id = 42` ausführt, lege den Index
`idx_bestellung_kunde` an und sieh noch einmal hin. Ausführen: siehe
[AUSFUEHREN.md](../../AUSFUEHREN.md); für diese Übung gibt es zusätzlich
`pruefung-postgresql.sql`, `pruefung-sqlite.sql` und `pruefung-h2.sql` – führe nach
`pruefung.sql` die Datei für deine Datenbank aus.

## Abnahmekriterien

- `pruefung.sql` und die Datei für deine Datenbank geben keine `Fehler:`-Zeile aus.
- Du kannst beide Ausführungspläne (vor und nach dem Index) zeigen und erklären, was sich
  geändert hat.

## Hinweise

1. `CREATE VIEW offene_bestellungen AS SELECT … WHERE status = 'OFFEN';` – eine Sicht
   speichert keine Daten, sie speichert die Abfrage. Ändern sich Bestellungen, ändert sich
   die Sicht mit.
2. `CREATE INDEX idx_bestellung_kunde ON bestellung (kunde_id);`
3. Was du sehen wirst:

   | | vorher | nachher |
   |---|---|---|
   | PostgreSQL | `Seq Scan on bestellung` | `Bitmap Heap Scan` / `Index Scan using idx_bestellung_kunde` |
   | SQLite | `SCAN bestellung` | `SEARCH bestellung USING INDEX idx_bestellung_kunde` |
   | H2 | `/* PUBLIC.BESTELLUNG.tableScan */` | `/* PUBLIC.IDX_BESTELLUNG_KUNDE: KUNDE_ID = 42 */` |

4. Warum `kunde_id` in dieser Übung *kein* Fremdschlüssel ist: H2 und MySQL legen für
   Fremdschlüssel von selbst einen Index an, PostgreSQL und SQLite nicht – eine der
   häufigsten Ursachen für langsame Anwendungen auf PostgreSQL. Ohne Fremdschlüssel sehen
   hier alle dasselbe.
5. Zur Frage 5 in `aufgabe.sql`: `status` hat nur zwei Werte; `GELIEFERT` trifft 90 % der
   Zeilen. Da ist es billiger, alles zu lesen – die Datenbank ignoriert so einen Index
   meist. Ein Index lohnt, wenn die Bedingung *wenige* Zeilen aus *vielen* auswählt.

# Übung 04: Mehrere Tabellen verbinden

## Aufgabe

`start.sql` legt Kunden, Artikel, Bestellungen und Positionen an – vier Tabellen, die über
Fremdschlüssel zusammenhängen (`bestellung.kunde_id` → `kunde.id`,
`position.bestellung_id` → `bestellung.id`, `position.artikel_id` → `artikel.id`). Schreibe
fünf Sichten, wie in `aufgabe.sql` beschrieben. `aufgabe_1` beginnt mit einem Klassiker:
zwei Tabellen im `FROM` ohne Verbindungsbedingung – 30 Zeilen statt 5. Ausführen: siehe
[AUSFUEHREN.md](../../AUSFUEHREN.md).

## Abnahmekriterien

- `pruefung.sql` gibt keine `Fehler:`-Zeile und keine Fehlermeldung aus.
- Jede Sicht nutzt `JOIN … ON` (bzw. `LEFT JOIN`), kein Komma-Join.

## Hinweise

1. `FROM bestellung b JOIN kunde k ON k.id = b.kunde_id` – die `ON`-Bedingung sagt, welche
   Zeilen zusammengehören. Ohne sie wird jede Zeile mit jeder kombiniert (kartesisches
   Produkt: 5 × 6 = 30).
2. `b` und `k` sind Aliase – kürzer und bei gleichnamigen Spalten (`id`, `name`) nötig,
   sonst weiß die Datenbank nicht, welches `id` gemeint ist.
3. „Kunden ohne Bestellung": `LEFT JOIN` behält alle Kunden; wer keine Bestellung hat, bekommt
   `NULL` in den Bestellspalten – `WHERE b.id IS NULL` filtert genau die.
4. Drei und vier Tabellen: einfach weitere `JOIN … ON` anhängen; die Reihenfolge folgt den
   Fremdschlüsseln.
5. `DISTINCT` für „jede Kombination einmal" – Anna hat den Hammer nur einmal bestellt, aber
   der Hammer kommt bei Anna und Eva vor: zwei verschiedene Paare.

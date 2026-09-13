# Lösung 02

Sechs Sichten, siehe `loesung/aufgabe.sql`. Worauf es ankommt:

- `aufgabe_1`: Spaltenliste statt `*`, `WHERE aktiv = TRUE`.
- `aufgabe_2`: `BETWEEN` schließt beide Grenzen ein – 12.90, 18.75, 24.50, 31.00.
- `aufgabe_3`: `IS NULL`. Wer `= NULL` schreibt, bekommt eine leere Sicht und keine
  Fehlermeldung – die Prüfung meldet „es fehlen Kunden".
- `aufgabe_4`: `DISTINCT` – vier Kategorien.
- `aufgabe_5`: `ORDER BY preis DESC LIMIT 3` – Akkuschrauber, Bohrmaschine, Säge.
- `aufgabe_6`: `LIKE 'S%'` – vier Artikel, Säge eingeschlossen. In SQLite ist `LIKE`
  für ASCII-Buchstaben nicht schreibungsabhängig, in PostgreSQL und H2 schon (`ILIKE` in
  PostgreSQL für „egal wie geschrieben").

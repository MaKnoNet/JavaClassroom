# Lösung 01

`loesung/aufgabe.sql`: `CREATE TABLE` mit fünf Spalten, `id INTEGER PRIMARY KEY`,
`name VARCHAR(100) NOT NULL`, `aktiv BOOLEAN NOT NULL`; drei `INSERT`s, bei Ben `NULL`
für die E-Mail.

Typische Stolpersteine:

- `email = ''` statt `NULL` – leerer Text ist ein Wert, `NULL` ist „unbekannt". Die Prüfung
  unterscheidet das (`email IS NULL`).
- Datum als `17.05.1990` – Datenbanken sprechen ISO 8601: `'1990-05-17'`.
- `aktiv` als Text `'ja'` – dann funktioniert später kein `WHERE aktiv = TRUE`.
- Die beiden gewollten Fehler nicht als gewollt erkannt: Die Prüfung *muss* dort scheitern.
  Wer keinen Fehler sieht, hat keine Regel.

# Lösung 04

Siehe `loesung/aufgabe.sql`.

- `aufgabe_1`: Der Komma-Join ohne `WHERE` ist das kartesische Produkt. `JOIN … ON` ist die
  lesbare Form; `FROM bestellung, kunde WHERE kunde.id = bestellung.kunde_id` wäre
  gleichwertig, aber die Bedingung geht in langen `WHERE`-Listen leicht verloren.
- `aufgabe_2`: `LEFT JOIN` + `IS NULL` – oder `WHERE id NOT IN (SELECT kunde_id FROM bestellung)`
  (Unterabfrage, Lektion 05). Beides richtig; die Prüfung vergleicht nur das Ergebnis.
- `aufgabe_3`: Rechnen über zwei Tabellen hinweg – `p.menge * a.preis`.
- `aufgabe_4`: Vier Tabellen, `DISTINCT` – acht Paare.
- `aufgabe_5`: `LEFT JOIN` ohne `WHERE` – sieben Zeilen: fünf Bestellungen plus Clara und
  David mit `NULL`. Ein `INNER JOIN` hätte fünf.

Der Unterschied `INNER`/`LEFT` ist die Prüffrage der Lektion: `INNER` liefert nur Paare,
`LEFT` alle Zeilen der linken Tabelle, notfalls mit `NULL` aufgefüllt. `RIGHT JOIN` ist
`LEFT JOIN` mit vertauschten Tabellen; `FULL JOIN` beides (SQLite kann es erst seit 3.39).

# Lösung 05

Siehe `loesung/aufgabe.sql`.

- `aufgabe_1`: `GROUP BY k.name` – vier Kunden (Anna 55.30, Ben 103.97, Eva 55.50, Fritz 89.00).
  Wer Clara und David mit 0 sehen will, braucht `LEFT JOIN` und `COALESCE(SUM(…), 0)` –
  die Aufgabe verlangt es nicht.
- `aufgabe_2`: `HAVING COUNT(*) > 1` – nur Anna. `WHERE COUNT(*) > 1` ist ein Syntaxfehler,
  weil beim `WHERE` noch nicht gruppiert ist.
- `aufgabe_3`: Durchschnitt 27.07 – Säge (31) und Bohrmaschine (89).
- `aufgabe_4`: `COUNT(email)` = 4, `COUNT(*)` = 6. Die häufigste Falle im Reporting:
  `AVG` über eine Spalte mit `NULL` ist der Durchschnitt der *vorhandenen* Werte, nicht
  aller Zeilen.
- `aufgabe_5`: CTE `bestellwert`, dann `WHERE wert > (SELECT AVG(wert) FROM bestellwert)` –
  Bestellungen 3 (103.97) und 5 (89.00), Durchschnitt 60.75.

Zu SQLite und `aufgabe_1` ohne `GROUP BY`: SQLite erlaubt „bare columns" bei Aggregaten
und nimmt irgendeine Zeile – bequem beim Experimentieren, im Betrieb ein stiller Fehler.
Deshalb ist der Startzustand dort nicht rot, sondern falsch; die Prüfung findet es trotzdem.

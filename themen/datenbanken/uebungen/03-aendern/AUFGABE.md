# Übung 03: Daten ändern und Transaktionen

## Aufgabe

`start.sql` legt Kunden, Konten und Artikel an. In `aufgabe.sql` stehen fünf Änderungen –
einfügen, ändern, löschen, Preise erhöhen und eine Überweisung. Für die Überweisung gibt es
einen Anfang, der **doppelt falsch** ist: Das erste `UPDATE` belastet *alle* Konten, und
ohne Transaktion könnte zwischen den beiden Buchungen etwas schiefgehen. Ausführen: siehe
[AUSFUEHREN.md](../../AUSFUEHREN.md).

Probiere außerdem in der Konsole (nicht in der Datei): `BEGIN;` – dann alle Kunden löschen –
`SELECT COUNT(*) FROM kunde;` (0!) – `ROLLBACK;` – `SELECT COUNT(*) FROM kunde;` (alle
wieder da). Das ist der Moment, in dem Transaktionen verständlich werden.

## Abnahmekriterien

- `pruefung.sql` gibt keine `Fehler:`-Zeile aus.
- Die Überweisung steht zwischen `BEGIN;` und `COMMIT;`, beide `UPDATE`s haben ein `WHERE`.
- `start.sql` danach noch einmal laufen lassen und `aufgabe.sql` erneut – das Ergebnis muss
  dasselbe sein (deine Datei ist wiederholbar).

## Hinweise

1. `INSERT INTO kunde (…) VALUES (…);` wie in Übung 01.
2. `UPDATE tabelle SET spalte = wert WHERE bedingung;` – **immer** mit `WHERE`, sonst trifft
   es jede Zeile. Im Zweifel erst `SELECT … WHERE bedingung` ausführen und zählen, ob es die
   richtigen Zeilen sind, dann das `SELECT` durch `UPDATE … SET` ersetzen.
3. Löschen: `DELETE FROM kunde WHERE id = 4;` – dieselbe Regel; ohne `WHERE` ist die Tabelle leer.
4. Rechnen im `SET`: `SET preis = preis * 1.10`.
5. `BEGIN;` … `COMMIT;` – oder `ROLLBACK;`, wenn dazwischen etwas nicht stimmt. In psql,
   sqlite3 und H2 heißt es gleich. Bis zum `COMMIT` sieht kein anderer Nutzer die Änderung;
   das ist der zweite Grund für Transaktionen (Spring-Lektion 04 macht daraus `@Transactional`).

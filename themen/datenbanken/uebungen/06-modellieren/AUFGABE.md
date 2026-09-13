# Übung 06: Modellierung und Constraints

## Aufgabe

`start.sql` legt eine einzige Tabelle `anmeldung` an – so, wie sie aus einer Excel-Liste
käme: Kurstitel, Plätze, Teilnehmername und E-Mail in jeder Zeile wiederholt. Schau sie dir
an: `SQL Grundlagen` hat einmal 12 und einmal 15 Plätze. Welche Zahl stimmt? Das ist die
**Update-Anomalie**, und sie ist der Grund für Normalisierung.

Entwirf drei Tabellen (`kurs`, `teilnehmer`, `teilnahme`), lege sie mit allen Regeln an und
fülle sie – die Vorgaben stehen in `aufgabe.sql`. Ausführen: siehe
[AUSFUEHREN.md](../../AUSFUEHREN.md).

## Abnahmekriterien

- `pruefung.sql` gibt keine `Fehler:`-Zeile aus.
- Fünf Anweisungen in `pruefung.sql` **müssen scheitern** (doppelter Titel, 0 Plätze,
  doppelte E-Mail, unbekannter Kurs, doppelte Teilnahme) – sie sind so kommentiert. Die
  letzte Anweisung (Kurs 2 absagen) **muss gelingen**, und seine Teilnahmen verschwinden mit.
- Der Primärschlüssel von `teilnahme` besteht aus beiden Fremdschlüsseln.

## Hinweise

1. Regeln direkt an der Spalte: `titel VARCHAR(100) NOT NULL UNIQUE`,
   `plaetze INTEGER NOT NULL CHECK (plaetze > 0)`, `email VARCHAR(100) UNIQUE` (ohne
   `NOT NULL` – mehrere `NULL` sind erlaubt, „unbekannt" ist nicht doppelt).
2. Fremdschlüssel: `kurs_id INTEGER NOT NULL REFERENCES kurs (id) ON DELETE CASCADE`.
   Ohne `ON DELETE CASCADE` verweigert die Datenbank das Löschen eines Kurses, solange
   Teilnahmen darauf zeigen – auch eine gültige Wahl, nur nicht die hier geforderte.
3. Zusammengesetzter Schlüssel: `PRIMARY KEY (kurs_id, teilnehmer_id)` als eigene Zeile am
   Ende der Spaltenliste.
4. n:m („viele zu viele"): Ein Kurs hat viele Teilnehmer, ein Teilnehmer viele Kurse. Das
   geht nicht mit einem Fremdschlüssel auf einer der beiden Seiten – man braucht die
   Zwischentabelle. 1:n (eine Bestellung, viele Positionen) kommt dagegen mit einem
   Fremdschlüssel in der n-Tabelle aus (Übung 04).
5. Automatische IDs, falls du sie statt fester Zahlen willst: PostgreSQL und H2
   `id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY`, SQLite `id INTEGER PRIMARY KEY`
   (zählt von selbst), MySQL `AUTO_INCREMENT`. Die Prüfung erwartet die festen IDs aus
   `aufgabe.sql`, deshalb hier feste Zahlen.
6. SQLite prüft Fremdschlüssel nur nach `PRAGMA foreign_keys = ON;` – `pruefung.sql` setzt
   es in der ersten Zeile; PostgreSQL und H2 melden dafür einen Syntaxfehler und machen
   weiter. Das ist der eine erwartete „Fehler", der keiner ist.

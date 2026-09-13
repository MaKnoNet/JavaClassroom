# Lösung 08

`loesung/V2__telefonnummer.sql` – nach `migrationen/` kopieren. Drei Anweisungen:
`ALTER TABLE … ADD COLUMN`, `UPDATE … SET email = LOWER(email)`, `UPDATE … WHERE id = 1`.

Was Flyway daraus macht: `flyway migrate` liest `migrationen/`, vergleicht mit
`flyway_schema_history`, findet Version 2 als fehlend, führt die Datei aus und trägt sie
mit Prüfsumme, Zeitpunkt und `success = TRUE` ein. Ein zweiter `migrate` tut nichts mehr –
das ist der Punkt: Der Befehl ist auf jedem Server derselbe, das Ergebnis auch.

Die Prüffrage: Eine eingespielte Datei darf nie mehr verändert werden, weil Flyway ihre
Prüfsumme gespeichert hat. `flyway validate` (und `migrate`, das vorher validiert) meldet
„Migration checksum mismatch for migration version 1" und bricht ab. Korrekturen sind immer
eine *neue* Version – `V3__…sql` –, auch wenn sie nur einen Tippfehler in V2 beheben.

Zwei Dinge, die im Unterricht auffallen:

- H2 (und manche andere) bekommen von Flyway eine zusätzliche Zeile mit Rang −1 und leerer
  Version („Flyway Schema Creation") – deshalb zählt die Prüfung nur Zeilen mit Version.
- Flyway legt Tabelle und Spalten in Kleinbuchstaben mit Anführungszeichen an; in H2 muss
  man sie deshalb ebenfalls in Anführungszeichen ansprechen (`"flyway_schema_history"`,
  `"success"`), sonst sucht H2 nach `FLYWAY_SCHEMA_HISTORY`.

# Übung 08: Schema-Evolution mit Flyway

## Aufgabe

Bisher hast du Tabellen mit `start.sql` von Hand angelegt. Im Team geht das nicht: Drei
Entwickler, ein Testserver, ein Produktionsserver – wer hat welches Skript wann wo
ausgeführt? **Flyway** beantwortet das: Jede Schemaänderung ist eine nummerierte Datei
(`V1__init.sql`, `V2__telefonnummer.sql`, …), Flyway führt beim Aufruf genau die aus, die
auf dieser Datenbank noch fehlen, und merkt sich das in der Tabelle `flyway_schema_history`.

`migrationen/V1__init.sql` ist schon da – das Schema, „wie es auf dem Server läuft".
Deine Aufgabe ist `migrationen/V2__telefonnummer.sql` (Vorgaben in `aufgabe.sql`): neue
Spalte, Datenmigration, ein Wert – ohne die sechs vorhandenen Kunden anzufassen.

## Ausführen

Flyway einmal laden (kostenlos, bringt eigenes Java und die Treiber mit; die Datei hat
rund 500 MB) und nach `arbeit/datenbanken/` entpacken:
<https://download.red-gate.com/maven/release/com/redgate/flyway/flyway-commandline/13.6.0/flyway-commandline-13.6.0.zip>
– der Befehl heißt danach `..\flyway-13.6.0\flyway` (Windows) bzw. `../flyway-13.6.0/flyway`.

Im Übungsordner, in dieser Reihenfolge (die Datenbank ist dieselbe wie in den Übungen
davor; die Zeilen für PostgreSQL und SQLite stehen darunter):

```bash
# 1. leere Datenbank (wie immer)
java -cp ../h2-2.3.232.jar org.h2.tools.RunScript -url jdbc:h2:./uebung -user sa -script start.sql -showResults -continueOnError
# 2. Flyway spielt alle Migrationen aus migrationen/ ein – erst nur V1, nach deiner Arbeit V1 und V2
../flyway-13.6.0/flyway -url=jdbc:h2:./uebung -user=sa -locations=filesystem:migrationen migrate
# 3. Prüfung
java -cp ../h2-2.3.232.jar org.h2.tools.RunScript -url jdbc:h2:./uebung -user sa -script pruefung.sql -showResults -continueOnError
```

PostgreSQL: `-url=jdbc:postgresql://localhost:5432/uebung -user=lernen -password=lernen`
(Container aus AGENTS.md; bei Podman unter Windows lauscht der Port auf `[::1]` – dann
`jdbc:postgresql://[::1]:5432/uebung`). SQLite: `-url=jdbc:sqlite:uebung.db`.

`flyway info` zeigt jederzeit, was eingespielt ist und was noch aussteht.

## Abnahmekriterien

- `pruefung.sql` gibt keine `Fehler:`-Zeile aus: zwei erfolgreiche Migrationen in der
  Verlaufstabelle, Spalte `telefonnummer` da, alle E-Mails klein, Anna hat ihre Nummer,
  sechs Kunden wie vorher.
- `V1__init.sql` ist unverändert. `V2__telefonnummer.sql` enthält kein `DROP`, kein
  `DELETE`, kein `CREATE TABLE`.
- Du hast die Prüffrage selbst ausprobiert (Hinweis 4).

## Hinweise

1. Dateiname: `V` + Version + **zwei** Unterstriche + Beschreibung + `.sql`. Ein Unterstrich
   ist der häufigste Fehler – Flyway ignoriert die Datei dann stillschweigend (`flyway info`
   zeigt sie nicht).
2. Neue Spalte: `ALTER TABLE kunde ADD COLUMN telefonnummer VARCHAR(30);` – geht in
   allen Datenbanken gleich. Kleinschreiben: `UPDATE kunde SET email = LOWER(email);`
   (`NULL` bleibt dabei `NULL`).
3. Flyway führt jede Datei in einer Transaktion aus (wo die Datenbank DDL-Transaktionen
   kann – PostgreSQL ja, H2 und SQLite teilweise): Scheitert eine Anweisung, bleibt die
   Migration als `failed` in der Tabelle stehen und Flyway verweigert weitere Läufe, bis
   man `flyway repair` aufruft und die Datei korrigiert.
4. **Die Prüffrage ausprobieren:** Nach erfolgreichem `migrate` in `V1__init.sql` ein
   Leerzeichen anhängen, dann `flyway validate` – „Migration checksum mismatch for
   migration version 1". Wieder rückgängig machen. Genau deshalb ist eine eingespielte
   Migration tabu: Eine Änderung daran würde auf jedem Server, der sie schon hat, nie
   ankommen – und auf einem neuen Server anders laufen als auf den alten.
5. Spring Boot führt Flyway beim Start automatisch aus, wenn `flyway-core` auf dem
   Klassenpfad liegt und die Dateien unter `src/main/resources/db/migration/` stehen –
   dieselben Dateien, dieselbe Tabelle (Spring-Lektion 05).

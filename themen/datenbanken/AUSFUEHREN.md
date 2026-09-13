# So führst du eine SQL-Übung aus

Jede Übung besteht aus drei Dateien, die du **in dieser Reihenfolge** gegen deine
Datenbank laufen lässt:

| Datei | Wer schreibt sie | Was sie tut |
|---|---|---|
| `start.sql` | die Übung | räumt auf und legt den Ausgangszustand an – beliebig oft wiederholbar |
| `aufgabe.sql` | **du** | deine Lösung; die Teilaufgaben stehen als Kommentar darin |
| `pruefung.sql` | die Übung | prüft dein Ergebnis: jede Ausgabezeile, die mit `Fehler:` beginnt, ist ein nicht erfülltes Kriterium |

**Bestanden** heißt: `pruefung.sql` gibt keine Zeile mit `Fehler:` aus und keine
Fehlermeldung – außer denen, die in der Datei ausdrücklich als gewollt kommentiert sind
(die Prüfung versucht dann absichtlich, eine Regel zu verletzen, und *muss* scheitern).

Alle drei Dateien liegen in deinem Arbeitsordner `arbeit/datenbanken/NN-name/`. Die
Befehle setzen voraus, dass du dich in diesem Ordner befindest.

## H2 (Standard – nur das JDK nötig)

Einmalig das Jar laden (nach `arbeit/datenbanken/`):

```bash
curl -O https://repo1.maven.org/maven2/com/h2database/h2/2.3.232/h2-2.3.232.jar
```

Dann je Datei – die Datenbank ist die Datei `uebung.mv.db` im Übungsordner:

```bash
java -cp ../h2-2.3.232.jar org.h2.tools.RunScript -url jdbc:h2:./uebung -user sa -script start.sql -showResults -continueOnError
java -cp ../h2-2.3.232.jar org.h2.tools.RunScript -url jdbc:h2:./uebung -user sa -script aufgabe.sql -showResults -continueOnError
java -cp ../h2-2.3.232.jar org.h2.tools.RunScript -url jdbc:h2:./uebung -user sa -script pruefung.sql -showResults -continueOnError
```

Ergebniszeilen beginnen mit `-->`; also ist `--> Fehler: …` das, wonach du suchst.
Zum Ausprobieren von Abfragen öffnet `java -jar ../h2-2.3.232.jar` die H2-Konsole im
Browser: JDBC-URL `jdbc:h2:<absoluter Pfad zum Übungsordner>/uebung`, Benutzer `sa`,
kein Passwort. Konsole und `RunScript` dürfen nicht gleichzeitig auf die Datei zugreifen –
Konsole vorher trennen.

## PostgreSQL

Lokal installiert (Datenbank `uebung` einmal anlegen: `createdb uebung`):

```bash
psql -d uebung -f start.sql
psql -d uebung -f aufgabe.sql
psql -d uebung -f pruefung.sql
```

Als Podman-Container ohne lokales `psql` (Container aus `AGENTS.md` heißt `lern-db`):

```bash
podman exec -i lern-db psql -U lernen -d uebung < start.sql
```

Interaktiv: `psql -d uebung` bzw. `podman exec -it lern-db psql -U lernen -d uebung`;
dort `\dt` (Tabellen), `\d kunde` (Spalten), `\q` (Ende).

## SQLite

```bash
sqlite3 uebung.db < start.sql
sqlite3 uebung.db < aufgabe.sql
sqlite3 uebung.db < pruefung.sql
```

Interaktiv: `sqlite3 uebung.db`; dort `.tables`, `.schema kunde`, `.quit`. Fremdschlüssel
prüft SQLite nur nach `PRAGMA foreign_keys = ON;` – die Prüfdateien setzen das selbst.

## Flyway (nur Übung 08)

Übung 08 spielt ihre Migrationen mit der Flyway-Kommandozeile ein statt mit `aufgabe.sql`.
Download (kostenlos, ~500 MB, bringt Java und alle Treiber mit), einmal nach
`arbeit/datenbanken/` entpacken:
<https://download.red-gate.com/maven/release/com/redgate/flyway/flyway-commandline/13.6.0/flyway-commandline-13.6.0.zip>

```bash
../flyway-13.6.0/flyway -url=jdbc:h2:./uebung -user=sa -locations=filesystem:migrationen migrate
../flyway-13.6.0/flyway -url=jdbc:h2:./uebung -user=sa -locations=filesystem:migrationen info
```

PostgreSQL: `-url=jdbc:postgresql://localhost:5432/uebung -user=lernen -password=lernen`
(Podman unter Windows: `[::1]` statt `localhost`). SQLite: `-url=jdbc:sqlite:uebung.db`.
Die Befehle stehen auch in der `AUFGABE.md` der Übung.

## Andere Datenbank (MySQL/MariaDB, SQL Server, Oracle)

Die Übungen sind in Standard-SQL geschrieben und laufen dort mit kleinen Anpassungen;
die Dialekttabelle im Lehrplan nennt sie. Dein Lehrer passt `aufgabe.sql` und
`pruefung.sql` beim Kopieren in den Arbeitsordner an.

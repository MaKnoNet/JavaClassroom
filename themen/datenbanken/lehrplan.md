---
thema: datenbanken
titel: Datenbanken und SQL
voraussetzungen: []
zielgruppe: [azubi, student, kollege]
reihenfolge: 90
---

# Datenbanken und SQL

SQL als Sprache – ohne Java, ohne Framework. Wer Spring-Lektion 03 (JPA) verstehen will,
muss vorher wissen, was eine Tabelle, ein Schlüssel, ein JOIN und eine Transaktion sind;
wer Security-Lektion 02 (Injection) verstehen will, muss SQL lesen können. Dieses Thema
ist der Unterbau dafür und braucht keine Programmierkenntnisse: Der Lernende schreibt nur
`.sql`-Dateien und arbeitet in einer SQL-Konsole.

Zehn Lektionen: 01–07 die Sprache und das Modell, 08–10 (Stufe 3) das, was im Betrieb
dazukommt – versionierte Schemaänderungen mit Flyway, Fensterfunktionen für Auswertungen,
Index-Fallen. Jede Übung hat drei Dateien – `start.sql` (Ausgangszustand), `aufgabe.sql` (die Datei des
Lernenden), `pruefung.sql` (Abnahme in reinem SQL: jede Ausgabezeile `Fehler: …` ist ein
nicht erfülltes Kriterium, leere Ausgabe ist bestanden). Wie sie laufen, steht in
[AUSFUEHREN.md](AUSFUEHREN.md).

## Datenbank wählen (vor Lektion 01)

Beim ersten Start des Themas, in dieser Reihenfolge:

1. **Ist schon eine Datenbank da?** Prüfen: `psql --version`, `sqlite3 --version`,
   `mysql --version`, `sqlcmd -?`, `sqlplus -v` – und fragen, ob es eine gibt, die die
   Befehle nicht finden (Firmenserver, DBeaver-Verbindung). Wenn ja: **fragen, ob sie
   verwendet werden darf** – eine Firmendatenbank ist keine Übungsdatenbank; es braucht
   eine eigene Datenbank oder ein eigenes Schema, in dem Tabellen angelegt und gelöscht
   werden dürfen. Nur bei einem klaren Ja wird sie genommen.
2. **Sonst H2** – die Standardwahl: nichts zu installieren außer dem JDK, ein Jar von Maven
   Central, Konsole im Browser, Datenbank als Datei im Übungsordner.
3. Die Wahl in die Fortschrittsdatei eintragen: `- datenbank: h2` (bzw. `postgresql`,
   `sqlite`, `mysql`, `sqlserver`, `oracle`) unter `## datenbanken`.

Geprüft sind **H2, PostgreSQL und SQLite** – jede Übung mit Start und Lösung auf allen
dreien durchgespielt. MySQL/MariaDB, SQL Server und Oracle laufen mit Anpassungen nach
dieser Tabelle; der Lehrer passt `aufgabe.sql` und `pruefung.sql` beim Kopieren in den
Arbeitsordner an (ungeprüft – beim ersten Lauf gemeinsam hinschauen):

| Standard (H2, PostgreSQL, SQLite) | MySQL/MariaDB | SQL Server | Oracle |
|---|---|---|---|
| `LIMIT 3` | `LIMIT 3` | `SELECT TOP 3` oder `OFFSET 0 ROWS FETCH NEXT 3 ROWS ONLY` | `FETCH FIRST 3 ROWS ONLY` |
| `EXCEPT` | ab 8.0.31, sonst `NOT EXISTS` | `EXCEPT` | `MINUS` |
| `BOOLEAN`, `TRUE`/`FALSE` | `TINYINT(1)`, `1`/`0` | `BIT`, `1`/`0` | ab 23c `BOOLEAN`, sonst `NUMBER(1)` |
| `GENERATED ALWAYS AS IDENTITY` | `AUTO_INCREMENT` | `IDENTITY(1,1)` | `GENERATED ALWAYS AS IDENTITY` |
| `SELECT … WHERE …` ohne `FROM` | erlaubt | erlaubt | `FROM dual` nötig |
| `'a' \|\| 'b'` | `CONCAT('a','b')` | `'a' + 'b'` | `'a' \|\| 'b'` |
| `BEGIN;` | `START TRANSACTION;` | `BEGIN TRANSACTION;` | implizit, nur `COMMIT`/`ROLLBACK` |
| Konsole | `mysql` | `sqlcmd` | `sqlplus` |

Dialektfallen, die auch bei den drei geprüften auftreten: SQLite prüft Fremdschlüssel
erst nach `PRAGMA foreign_keys = ON` (die Prüfdateien setzen es) und erlaubt Spalten ohne
`GROUP BY` stillschweigend; H2 schreibt unquotierte Bezeichner groß (`kunde` → `KUNDE`,
nur in Katalogabfragen sichtbar) und legt für Fremdschlüssel automatisch einen Index an;
PostgreSQL ist bei Typen und `GROUP BY` am strengsten – und deshalb zum Lernen am besten.

## Lektionen

### 01 Tabellen, Zeilen, Spalten
- **Ziele:** Was eine relationale Datenbank ist – Tabellen mit festen Spalten, Zeilen als Datensätze, jede Zelle ein Wert; Datentypen `INTEGER`, `NUMERIC(10,2)` (Geld – nie `FLOAT`), `VARCHAR(n)`, `DATE`, `BOOLEAN`; `CREATE TABLE` mit `PRIMARY KEY` (eindeutig und Pflicht) und `NOT NULL`; `INSERT INTO … VALUES`; `NULL` als „unbekannt", nicht als leerer Text; Datum immer ISO `'1990-05-17'`; die Konsole kennenlernen: `\dt`/`\d` (psql), `.tables`/`.schema` (sqlite3), Baum links in der H2-Konsole; `SELECT * FROM kunde` als erster Blick in die Tabelle
- **Übung:** uebungen/01-erste-tabelle
- **Prüffrage:** Was ist der Unterschied zwischen `PRIMARY KEY` und `NOT NULL`, und warum braucht jede Tabelle einen Primärschlüssel? Und: Warum ist `NULL` etwas anderes als `''` – und was passiert bei `WHERE email = NULL`?
- **Übersetzung:** en: Tables, rows, columns | fr: Tables, lignes, colonnes
- **Stufe:** 1

Leitfaden: Mit einer Excel-Tabelle beginnen – jeder kennt Spalten und Zeilen. Der
Unterschied: Die Datenbank *erzwingt* Spaltentypen und Regeln, Excel nicht. Die zwei
gewollten Fehler am Ende der Prüfung sind die Lektion: Wer keinen Fehler sieht, hat keine
Regel. Datenbankwahl vorher (siehe oben).

### 02 Daten lesen: SELECT
- **Ziele:** `SELECT spalten FROM tabelle` – Spaltenliste statt `*`; `WHERE` mit `=`, `<>`, `<`, `AND`/`OR`/`NOT`, `IN (…)`, `BETWEEN`, `LIKE 'S%'` (`%` beliebig, `_` ein Zeichen), `IS NULL`/`IS NOT NULL`; `ORDER BY … DESC`, `LIMIT`; `DISTINCT`; Ausdrücke und Aliase (`preis * 1.19 AS brutto`); die Sicht (`CREATE VIEW`) als gespeicherte Abfrage – hier das Mittel, mit dem die Prüfung Abfragen ausführen kann; Reihenfolge der Auswertung: `FROM` → `WHERE` → `SELECT` → `ORDER BY` → `LIMIT`
- **Übung:** uebungen/02-abfragen
- **Prüffrage:** Warum liefert `WHERE email = NULL` keine Zeile, obwohl es Kunden ohne E-Mail gibt? Und: In welcher Reihenfolge wertet die Datenbank `SELECT name FROM kunde WHERE aktiv = TRUE ORDER BY name LIMIT 3` aus – und warum kann `LIMIT` deshalb nicht vor `ORDER BY` stehen?
- **Übersetzung:** en: Reading data: SELECT | fr: Lire les données : SELECT
- **Stufe:** 1

Leitfaden: Alles erst in der Konsole, dann in die Datei. Die `= NULL`-Falle vorführen:
leeres Ergebnis, keine Fehlermeldung – die gefährlichste Art von Fehler. `LIMIT` ist der
erste Dialektunterschied, den der Lernende trifft (Tabelle oben).

### 03 Daten ändern und Transaktionen
- **Ziele:** `INSERT`, `UPDATE … SET … WHERE`, `DELETE FROM … WHERE`; die Regel „erst `SELECT` mit derselben Bedingung, dann `UPDATE`/`DELETE`" – das vergessene `WHERE` trifft jede Zeile; Rechnen im `SET` (`preis = preis * 1.10`); **Transaktionen**: `BEGIN` … `COMMIT`/`ROLLBACK`, ganz oder gar nicht (Überweisung), Isolation (bis `COMMIT` sieht kein anderer die Änderung); Autocommit in der Konsole – jede Anweisung ohne `BEGIN` ist sofort endgültig; ACID als Begriff; Fremdschlüssel verhindern Löschen, auf das noch verwiesen wird
- **Übung:** uebungen/03-aendern
- **Prüffrage:** Was passiert bei `UPDATE konto SET stand = stand - 50;` ohne `WHERE` – und wie prüfst du eine Bedingung, *bevor* du sie in ein `UPDATE` schreibst? Und: Warum gehören die zwei `UPDATE`s einer Überweisung in eine Transaktion, und was macht `ROLLBACK`?
- **Übersetzung:** en: Changing data and transactions | fr: Modifier les données et transactions
- **Stufe:** 1

Leitfaden: Den `BEGIN` – alles löschen – `COUNT(*)` = 0 – `ROLLBACK` – alles wieder da
in der Konsole *vorführen*, bevor die Übung beginnt. Danach versteht jeder, was eine
Transaktion ist. Spring-Lektion 04 baut auf genau diesem Bild `@Transactional`.

### 04 Mehrere Tabellen: JOIN
- **Ziele:** Warum Daten auf mehrere Tabellen verteilt sind (Kunde einmal, Bestellungen viele); Fremdschlüssel `REFERENCES` als Verweis; `JOIN … ON` als Zusammenführen über den Schlüssel; das kartesische Produkt (Komma-Join ohne Bedingung, 5 × 6 = 30 Zeilen) als klassischer Fehler; Tabellenaliase und qualifizierte Spalten (`k.name`); `INNER JOIN` (nur Paare) vs. `LEFT JOIN` (alle links, rechts `NULL`), `RIGHT`/`FULL` nur nennen; „Kunden ohne Bestellung" per `LEFT JOIN … IS NULL`; Joins über drei und vier Tabellen entlang der Fremdschlüssel; `DISTINCT` gegen Mehrfachzeilen
- **Übung:** uebungen/04-joins
- **Prüffrage:** Warum liefert `SELECT … FROM bestellung, kunde` 30 Zeilen statt 5, und was fehlt? Und: Worin unterscheiden sich `INNER JOIN` und `LEFT JOIN` im Ergebnis, wenn ein Kunde keine Bestellung hat?
- **Übersetzung:** en: Several tables: JOIN | fr: Plusieurs tables : JOIN
- **Stufe:** 2

Leitfaden: Das kartesische Produkt an der Tafel mit zwei kleinen Tabellen (3 × 2 = 6
Zeilen) aufmalen – dann `ON` als Filter darauf. `LEFT JOIN` mit Clara und David: Wo kommt
das `NULL` her? Wer das erklären kann, hat Joins verstanden.

### 05 Zusammenfassen: GROUP BY und Unterabfragen
- **Ziele:** Aggregatfunktionen `COUNT`, `SUM`, `AVG`, `MIN`, `MAX`; `GROUP BY` – jede nicht aggregierte Spalte muss hinein (PostgreSQL/H2 erzwingen es, SQLite nicht); `HAVING` filtert Gruppen, `WHERE` filtert Zeilen davor; `COUNT(*)` vs. `COUNT(spalte)` – `NULL` wird von Aggregaten übersprungen; Unterabfragen als Wert (`> (SELECT AVG(…))`), als Menge (`IN (SELECT …)`), mit `EXISTS`; `WITH` (CTE) als benanntes Zwischenergebnis, lesbarer als geschachtelte Unterabfragen; Auswertungsreihenfolge vollständig: `FROM` → `WHERE` → `GROUP BY` → `HAVING` → `SELECT` → `ORDER BY` → `LIMIT`
- **Übung:** uebungen/05-gruppieren
- **Prüffrage:** Warum ist `WHERE COUNT(*) > 1` ein Fehler und `HAVING COUNT(*) > 1` richtig? Und: Was ist der Unterschied zwischen `COUNT(*)` und `COUNT(email)`, wenn zwei Kunden keine E-Mail haben?
- **Übersetzung:** en: Summarising: GROUP BY and subqueries | fr: Agréger : GROUP BY et sous-requêtes
- **Stufe:** 2

Leitfaden: `GROUP BY` als „Stapel bilden, dann je Stapel eine Zahl". Die
Auswertungsreihenfolge an der Tafel – sie erklärt `WHERE` vs. `HAVING` ohne
Auswendiglernen. `WITH` zuletzt, als Aufräumen einer Abfrage, die sonst dreifach
geschachtelt wäre.

### 06 Datenmodellierung und Constraints
- **Ziele:** Von der Excel-Liste zum Modell: Redundanz und die Update-Anomalie (zwei verschiedene Platzzahlen für denselben Kurs); Normalformen 1–3 in je einem Satz; Beziehungen 1:n (Fremdschlüssel in der n-Tabelle) und n:m (Zwischentabelle mit zusammengesetztem Primärschlüssel); Constraints als Regeln, die die Datenbank durchsetzt: `NOT NULL`, `UNIQUE`, `CHECK`, `REFERENCES … ON DELETE CASCADE|RESTRICT`; warum Regeln in die Datenbank gehören und nicht nur in die Anwendung (mehrere Anwendungen, Importe, Admins); Identity-Spalten je Dialekt; ER-Diagramm als Skizze vor dem `CREATE TABLE`; Bezug zu Spring-Lektion 03: `@Entity` bildet genau dieses Modell ab
- **Übung:** uebungen/06-modellieren
- **Prüffrage:** Woran erkennst du in der Tabelle `anmeldung`, dass sie nicht normalisiert ist, und welche Anomalie folgt daraus? Und: Warum braucht eine n:m-Beziehung eine dritte Tabelle, und woraus besteht deren Primärschlüssel?
- **Übersetzung:** en: Data modelling and constraints | fr: Modélisation des données et contraintes
- **Stufe:** 2

Leitfaden: Erst die Anomalie finden lassen (12 oder 15 Plätze?), dann fragen: „Wo müsste
die Zahl stehen, damit das nicht passieren kann?" Die Antwort ist die Tabelle `kurs`. Die
Prüfung verletzt fünf Regeln absichtlich – jede Fehlermeldung ist ein Erfolg. Das
ER-Diagramm auf Papier, bevor eine Zeile SQL entsteht.

### 07 Sichten, Indizes und Ausführungspläne
- **Ziele:** `CREATE VIEW` als gespeicherte Abfrage – Wiederverwendung, Vereinfachung, Zugriffsschutz; Testdaten in Masse per rekursiver CTE (`WITH RECURSIVE`); der **Index** als B-Baum: log(n) statt n, wann er hilft (hohe Selektivität, `WHERE`/`JOIN`/`ORDER BY`) und was er kostet (jeder Schreibzugriff, Platz); `EXPLAIN` lesen – `Seq Scan` vs. `Index Scan` (PostgreSQL), `SCAN` vs. `SEARCH USING INDEX` (SQLite), `tableScan` vs. `/* IDX… */` (H2); Primärschlüssel und `UNIQUE` sind automatisch indiziert, Fremdschlüssel nur in H2/MySQL – die PostgreSQL-Falle; Katalogtabellen (`pg_indexes`, `sqlite_master`, `INFORMATION_SCHEMA`) als Blick hinter die Kulissen; Ausblick Spring-Lektion 06 (N+1): Zahl der Abfragen zählt genauso wie ihr Plan
- **Übung:** uebungen/07-indizes
- **Prüffrage:** Was ändert sich im Ausführungsplan durch `CREATE INDEX … ON bestellung (kunde_id)`, und warum legt man trotzdem nicht auf jede Spalte einen Index? Und: Warum bringt ein Index auf `status` mit zwei Werten fast nichts?
- **Übersetzung:** en: Views, indexes and execution plans | fr: Vues, index et plans d'exécution
- **Stufe:** 3

Leitfaden: Erst `EXPLAIN` ohne Index, dann mit – die beiden Pläne nebeneinander sind die
Lektion. Den Baum als Telefonbuch erklären: sortiert nach Name findet man Müller sofort,
nach Telefonnummer muss man alles lesen. Die Fremdschlüssel-Falle in PostgreSQL erwähnen –
sie kostet im Betrieb mehr als jede andere Einstellung.

### 08 Schema-Evolution mit Flyway
- **Ziele:** Warum Schemaänderungen im Team versioniert sein müssen – kein SQL mehr von Hand auf Servern; **Flyway** als Werkzeug: nummerierte Migrationsdateien `V1__init.sql`, `V2__telefonnummer.sql` (Version, zwei Unterstriche, Beschreibung), `flyway migrate` führt genau die aus, die auf dieser Datenbank fehlen; die Verlaufstabelle `flyway_schema_history` (Version, Prüfsumme, Zeitpunkt, `success`); `flyway info` und `flyway validate`; eine eingespielte Migration wird **nie** geändert – Korrekturen sind neue Versionen; fehlgeschlagene Migrationen (`failed`, `flyway repair`); inkrementelle Änderungen ohne Datenverlust: `ALTER TABLE … ADD COLUMN`, Datenmigration per `UPDATE`; Flyway-CLI ohne Programmiersprache, Spring Boot führt dieselben Dateien beim Start aus (Spring-Lektion 05)
- **Übung:** uebungen/08-flyway-migration
- **Prüffrage:** Warum darf eine bereits auf einem Server ausgeführte Migrationsdatei nachträglich nie mehr verändert werden, und wie reagiert Flyway darauf? Und: Was steht in `flyway_schema_history`, und warum reicht der Befehl `flyway migrate` auf jedem Server gleich?
- **Übersetzung:** en: Schema evolution with Flyway | fr: Évolution du schéma avec Flyway
- **Stufe:** 3

Leitfaden: Erst die Frage stellen: „Drei Entwickler, ein Testserver – wie weiß der Server,
welche `ALTER TABLE` er schon hat?" Dann Flyway als die Antwort. Die Prüffrage nicht
erzählen, sondern erleben lassen: `V1` um ein Leerzeichen ändern, `flyway validate`,
„checksum mismatch". Die 500-MB-CLI ist der einzige Download des Themas – vorher laden.

### 09 Business-Analysen: Fensterfunktionen
- **Ziele:** Aggregieren, ohne Zeilen zusammenzufassen: die `OVER()`-Klausel; `PARTITION BY` als „Gruppe, aber jede Zeile bleibt"; `ORDER BY` im Fenster; `ROW_NUMBER()`, `RANK()`, `DENSE_RANK()`; `LAG()`/`LEAD()` für Vorperioden-Vergleiche; `SUM() OVER (ORDER BY …)` für laufende Summen, Rahmen (`ROWS BETWEEN … PRECEDING`) für gleitende Durchschnitte; Fensterfunktionen laufen nach `GROUP BY`/`HAVING`, vor `ORDER BY` – deshalb CTE davor und Filter auf den Rang in der nächsten Schicht; Top-N je Gruppe als Standardmuster; dialektneutrale Monatsbildung `SUBSTR(CAST(datum AS VARCHAR(10)), 1, 7)`; was sonst in Java-Schleifen landen würde, bleibt bei den Daten
- **Übung:** uebungen/09-fensterfunktionen
- **Prüffrage:** Was unterscheidet eine Abfrage mit `GROUP BY` grundlegend von einer mit einer Fensterfunktion (`OVER()`) bezüglich der Anzahl der Ergebniszeilen? Und: Warum kann `WHERE rang <= 3` nicht in derselben `SELECT`-Ebene stehen wie `ROW_NUMBER() … AS rang`?
- **Übersetzung:** en: Business analytics: window functions | fr: Analyses métier : fonctions de fenêtrage
- **Stufe:** 3

Leitfaden: Mit dem Fehlversuch beginnen, der in `aufgabe_1` schon steht – `LIMIT 3` über
alles. „Wie bekomme ich die Top 3 *je Kategorie*?" führt ohne Fensterfunktion in vier
Abfragen oder in Java-Code; mit `PARTITION BY` in eine Zeile. Dann `LAG` als „eine Zeile
zurückschauen" und die laufende Summe als Fenster, das mit jeder Zeile wächst.

### 10 Index-Fallen: SARGable Queries
- **Ziele:** Wann eine Datenbank einen vorhandenen Index ignoriert: Funktion auf der Spalte (`LOWER(nachname)`), Rechnung (`preis * 1.19`), Typumwandlung, führendes Wildcard (`LIKE '%text'`); **SARGable** – Spalte nackt auf der einen Seite, Wert auf der anderen; mit `EXPLAIN` beweisen statt vermuten; **Ausdrucksindizes** (`CREATE INDEX … ON kunde (LOWER(nachname))` in PostgreSQL und SQLite) und ihre Grenze (nur derselbe Ausdruck trifft sie); H2 ohne Ausdrucksindizes – berechnete Spalte `GENERATED ALWAYS AS` plus Index als Alternative, die auch sonst oft die bessere ist (normalisierte Suchspalte); Datumsbereiche statt `jahr(datum) =`; `LIKE 'abc%'` in PostgreSQL nur mit `text_pattern_ops`; Volltextsuche als Ausweg für `%text%`
- **Übung:** uebungen/10-index-fallen
- **Prüffrage:** Die Spalte `nachname` ist indiziert – warum führt `WHERE LOWER(nachname) = 'mueller'` trotzdem zu einem Seq Scan, und wie korrigierst du das? Und: Warum kann für `LIKE '%421'` kein B-Baum-Index helfen?
- **Übersetzung:** en: Index pitfalls: SARGable queries | fr: Pièges des index : requêtes SARGable
- **Stufe:** 3

Leitfaden: Die drei Pläne nebeneinander – derselbe Index, einmal genutzt, zweimal nicht.
Das Telefonbuch aus Lektion 07 weiterdenken: sortiert nach Nachname, aber die Suche
fragt nach „irgendwie geschrieben" oder „endet auf". Nach der Übung die Regel an drei
fremden Abfragen anwenden lassen (`WHERE YEAR(datum) = 2026`, `WHERE preis * 1.19 > 100`,
`WHERE CAST(id AS VARCHAR) = '42'`) – wer alle drei umschreiben kann, hat es.

# Übung 03: Aus Klassen werden Tabellen

## Aufgabe

`Produkt` und `Kategorie` sind gewöhnliche Java-Klassen. `ProduktTest` will sie mit
`TestEntityManager` speichern und wieder laden – und scheitert mit `Unable to locate persister: de.makno.lernen.Produkt`:
Hibernate weiß nicht, dass diese Klassen Tabellen sein sollen. Mach beide zu Entities:

1. `@Entity` an die Klassen; `@Id` mit `@GeneratedValue` am Feld `id`, damit die
   Datenbank die Nummer vergibt.
2. Einen parameterlosen Konstruktor ergänzen (`protected` genügt) – ohne ihn kann
   Hibernate keine Objekte aus Tabellenzeilen bauen.
3. `name` ist Pflicht: `@Column(nullable = false)`; bei `Kategorie` zusätzlich `unique`.
4. `Produkt.kategorie` ist eine Beziehung: `@ManyToOne` – viele Produkte, eine Kategorie.

`ProduktUebersicht` bleibt, was es ist: ein Record für die Ausgabe nach außen. Es soll
**keine** Entity werden – überlege, warum das gar nicht ginge.

## Abnahmekriterien

- `./gradlew test` ist grün (drei Tests).
- Im Testlog stehen die `create table`- und `insert`-Anweisungen, die Hibernate erzeugt
  hat (`spring.jpa.show-sql=true` ist gesetzt) – lies sie einmal ganz.
- Die Zugriffsmethoden bleiben unverändert; kein Setter kommt dazu.

## Hinweise

1. Alle Annotationen liegen in `jakarta.persistence.*` (nicht `javax`).
2. `@GeneratedValue(strategy = GenerationType.IDENTITY)` heißt: Auto-Increment-Spalte,
   die Datenbank zählt. Vor dem Speichern ist `id()` deshalb `null`.
3. Der dritte Test erwartet eine `PersistenceException` beim Speichern ohne Namen. Die
   kommt erst, wenn die Datenbank den Namen als Pflichtspalte kennt – Java allein weiß
   nichts von „Pflicht".
4. Warum kein Record: Ein Record hat keinen leeren Konstruktor und keine veränderlichen
   Felder – Hibernate braucht beides. Records sind für Daten, die *durch* das System
   fließen (DTOs, API-Antworten); Entities sind der Zustand, der *im* System bleibt.

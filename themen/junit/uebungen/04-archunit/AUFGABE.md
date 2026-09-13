# Übung 04: Architekturregeln als Test (ArchUnit)

## Aufgabe

Das Projekt hat zwei Schichten: `de.makno.lernen.ui` (Oberfläche) und
`de.makno.lernen.service` (Fachlogik). Regel im Team: **Die Oberfläche darf die
Fachlogik benutzen, aber nie umgekehrt.** Genau das ist gerade verletzt – `Preisrechner`
druckt über `Konsole`.

1. Schreibe in `ArchitekturTest` mit **ArchUnit** einen Test, der die Regel prüft:
   Keine Klasse in `..service..` darf von Klassen in `..ui..` abhängen. Der Test muss
   zuerst **rot** sein.
2. Repariere den Code, bis der Test grün ist – ohne die Regel aufzuweichen. Die
   Fachlogik gibt ihr Ergebnis zurück; wer es anzeigt, entscheidet die Oberfläche.

## Abnahmekriterien

- `./gradlew test` ist grün; `TEST-de.makno.lernen.ArchitekturTest.xml` enthält `tests="1"`
  (oder mehr) und `failures="0"`.
- `Preisrechner.java` importiert nichts aus `de.makno.lernen.ui`.
- `PreisrechnerTest` läuft weiterhin grün.

## Hinweise

1. ArchUnit-Test in Kurzform:
   ```java
   JavaClasses klassen = new ClassFileImporter().importPackages("de.makno.lernen");
   ArchRule regel = noClasses().that().resideInAPackage("..service..")
           .should().dependOnClassesThat().resideInAPackage("..ui..");
   regel.check(klassen);
   ```
   Imports: `com.tngtech.archunit.core.importer.ClassFileImporter`,
   `com.tngtech.archunit.core.domain.JavaClasses`, `com.tngtech.archunit.lang.ArchRule`,
   statisch `com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses`.
2. Die Reparatur ist eine Zeile löschen (und den Import). Die Ausgabe gehört in `Kasse`,
   die hat sie ohnehin schon.

# Übung 03: Unit- und Integrationstests trennen

## Aufgabe

Im Projekt liegen zwei Unit-Tests (`RechnerTest`) und ein Integrationstest
(`ProtokollIT`, benutzt das Dateisystem). Zurzeit läuft alles bei `./gradlew test` mit.
Im Team gilt die Konvention: **Klassen mit Endung `IT` sind Integrationstests** und
laufen in einem eigenen Schritt, damit `test` schnell bleibt.

Ergänze `build.gradle`:

1. `./gradlew test` führt nur noch die Unit-Tests aus (Klassen, die nicht auf `IT`
   enden).
2. Ein neuer Task `integrationTest` (Typ `Test`) führt nur die `*IT`-Klassen aus, mit
   denselben Klassen und demselben Classpath wie `test`.
3. `./gradlew check` führt beide aus – `integrationTest` hängt an `check`.

`src/` bleibt unverändert.

## Abnahmekriterien

- Nach `./gradlew test`: `build/test-results/test/` enthält `TEST-de.makno.lernen.RechnerTest.xml`,
  aber **keine** Datei für `ProtokollIT`.
- Nach `./gradlew integrationTest`: `build/test-results/integrationTest/TEST-de.makno.lernen.ProtokollIT.xml`
  enthält `tests="1"` und `failures="0"`.
- `./gradlew check` endet mit `BUILD SUCCESSFUL`.

## Hinweise

1. Klassen ausschließen: `test { exclude '**/*IT.class' }` – gefiltert wird auf
   kompilierte Klassen, deshalb `.class`.
2. Ein zweiter Test-Task braucht drei Dinge: `testClassesDirs = sourceSets.test.output.classesDirs`,
   `classpath = sourceSets.test.runtimeClasspath`, `useJUnitPlatform()`. Dann
   `include '**/*IT.class'`. Anhängen: `tasks.named('check') { dependsOn 'integrationTest' }`.

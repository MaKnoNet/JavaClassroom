# Übung 01: Die Build-Datei reparieren

## Aufgabe

Das Projekt enthält eine Klasse und einen Test. `./gradlew test` schlägt aber fehl – die
`build.gradle` ist unvollständig. Finde heraus, was fehlt, und ergänze es, bis der Test
läuft und grün ist.

## Abnahmekriterien

- `./gradlew test` endet mit `BUILD SUCCESSFUL`.
- `build/test-results/test/TEST-de.makno.lernen.HalloTest.xml` enthält `tests="1"` und
  `failures="0"`.

## Hinweise

1. Lies die Fehlermeldung von oben nach unten. Die erste Zeile mit `error:` sagt, welches
   Symbol der Compiler nicht kennt – und aus welchem Paket es kommt.
2. Zwei Dinge fehlen: die Abhängigkeit, die JUnit 5 in den Test-Classpath bringt, und die
   Anweisung, dass Gradle Tests mit der JUnit-Platform ausführen soll. Beides gehört in
   `build.gradle`; die Schablone unter `themen/_schablone/uebung-java/build.gradle` zeigt,
   wie es aussieht.

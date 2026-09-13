# Übung 04: Checkstyle als Build-Wächter

## Aufgabe

Das Projekt hat Checkstyle mit fünf Regeln eingebunden (`config/checkstyle/checkstyle.xml`).
`./gradlew check` bricht ab: `Rechner.java` verletzt jede Regel mindestens einmal, und
der Test kompiliert nicht, weil ein Methodenname nicht der Konvention entspricht.

Bringe `./gradlew check` zum Laufen, indem du **den Code** änderst – nicht die Regeln
und nicht die `build.gradle`.

## Abnahmekriterien

- `./gradlew check` endet mit `BUILD SUCCESSFUL` (Checkstyle 0 Verstöße, Tests grün).
- `config/checkstyle/checkstyle.xml` und `build.gradle` sind unverändert.

## Hinweise

1. Der Bericht liegt nach dem Lauf unter `build/reports/checkstyle/main.html` – dort
   stehen Regel, Zeile und Erklärung. Beim ersten Lauf lädt Gradle Checkstyle herunter.
2. Die fünf Regeln: Klammern um jeden `if`/`for`-Körper, kein `import x.*`, Konstanten in
   `GROSSBUCHSTABEN_MIT_UNTERSTRICH`, Methodennamen in `camelCase`, kein leerer Block
   (ein leerer `catch` verschluckt Fehler – besser die Ursache behandeln).

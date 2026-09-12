# Übung 02: Ein eigener Task

## Aufgabe

Ergänze in `build.gradle` zwei Tasks:

- `hallo` gibt `Hallo Gradle!` aus.
- `zaehle` gibt die Zeilen `1`, `2`, `3` aus (je eine Zeile) und **hängt von `hallo` ab**,
  sodass `./gradlew -q zaehle` zuerst `Hallo Gradle!` und dann die drei Zahlen ausgibt.

## Abnahmekriterien

- `./gradlew -q hallo` gibt genau `Hallo Gradle!` aus.
- `./gradlew -q zaehle` gibt vier Zeilen aus: `Hallo Gradle!`, `1`, `2`, `3`.
- `./gradlew tasks --all` listet beide Tasks.

## Hinweise

1. Ein Task sieht so aus: `tasks.register('name') { doLast { println 'Text' } }`.
   Ohne `doLast` läuft der Code schon beim Konfigurieren – also immer, auch bei `./gradlew test`.
2. Abhängigkeiten: `dependsOn 'hallo'` innerhalb des Task-Blocks.

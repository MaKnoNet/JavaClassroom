# Übung 09: Aus einem Projekt werden drei

## Aufgabe

Das Projekt ist ein Monolith: Geschäftslogik (`Rabattrechner`), Kommandozeile (`Konsole`)
und Berichtserzeugung (`Bericht`) liegen in einem einzigen Gradle-Projekt. Zerlege es in
einen Multi-Projekt-Build mit drei Modulen:

- `:kern` – `Rabattrechner` und sein Test; hängt von nichts ab.
- `:konsole` – `Konsole` mit `main`; hängt von `:kern` ab (`application`-Plugin).
- `:bericht` – `Bericht` und sein Test; hängt von `:kern` ab.

Die gemeinsame Build-Logik (Toolchain, UTF-8, JUnit) darf **nicht** dreimal kopiert
werden: Lege sie einmal in ein Konventions-Plugin unter `buildSrc/` und aktiviere es in
jedem Modul mit einer Zeile.

## Abnahmekriterien

- `./gradlew build` endet mit `BUILD SUCCESSFUL`; `./gradlew :kern:test` und
  `./gradlew :bericht:test` führen je ihre Tests aus.
- `./gradlew -q :konsole:run` gibt `100.00 EUR mit 20 % Rabatt: 80.00 EUR` aus.
- Keine der drei Modul-`build.gradle` enthält das Wort `toolchain` oder `junit` – das
  steht nur in `buildSrc/`.
- Im Wurzelprojekt gibt es kein `src/` mehr.
- Du kannst zeigen, was passiert, wenn `:kern` von `:konsole` abhängt (Hinweis 4).

## Hinweise

1. `settings.gradle`: `include 'kern', 'konsole', 'bericht'` – jeder Name ist ein Ordner
   mit eigener `build.gradle`. Die Quellen ziehen mit: `kern/src/main/java/…` usw.
2. `buildSrc/build.gradle` enthält nur `plugins { id 'groovy-gradle-plugin' }`. Jede Datei
   `buildSrc/src/main/groovy/<name>.gradle` wird zum Plugin `<name>`; ihr Inhalt ist eine
   gewöhnliche Build-Datei (`plugins { id 'java' }`, `java { toolchain … }`, …). Ein
   Modul aktiviert sie mit `plugins { id '<name>' }`.
3. Abhängigkeit auf ein Modul: `implementation project(':kern')` – keine Group, kein
   Artifact, keine Version. `./gradlew :konsole:dependencies` zeigt, was daraus wird.
4. Das Experiment am Ende: Trag in `kern/build.gradle` testweise
   `implementation project(':konsole')` ein und starte `./gradlew build`. Lies die
   Meldung, dann nimm die Zeile wieder heraus. Gradle berechnet die Reihenfolge aus den
   Abhängigkeiten (ein gerichteter Graph ohne Zyklen) – mit einem Kreis gibt es keine
   Reihenfolge mehr.

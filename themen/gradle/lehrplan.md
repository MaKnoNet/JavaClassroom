---
thema: gradle
titel: Gradle
voraussetzungen: [java]
zielgruppe: [azubi, student, kollege]
---

# Gradle

Gradle baut unsere Java-Projekte: kompilieren, testen, packen, Abhängigkeiten laden.
Wer Gradle versteht, kann ein Projekt auf jedem Rechner reproduzierbar bauen und weiß,
was die IDE im Hintergrund tut.

Am Ende kann der Lernende eine `build.gradle` lesen und ergänzen, Abhängigkeiten
hinzufügen, eigene Tasks schreiben, Tests im Build laufen lassen und ein Gradle-Projekt
in Eclipse oder IntelliJ importieren.

## Lektionen

### 01 Was ein Build-Tool tut, der Wrapper
- **Ziele:** Warum nicht einfach `javac`; `gradlew` vs. installiertes Gradle; `./gradlew build` und `./gradlew test` ausführen; wo die Ausgabe landet (`build/`)
- **Übung:** uebungen/01-build-datei
- **Prüffrage:** Warum liegt `gradlew` im Repository, Gradle selbst aber nicht?
- **Übersetzung:** en: What a build tool does, the wrapper | fr: Ce que fait un outil de build, le wrapper
- **Stufe:** 1

### 02 Aufbau von build.gradle
- **Ziele:** `plugins`, `repositories`, `dependencies`; `implementation` vs. `testImplementation`; Versionen und Maven-Koordinaten lesen
- **Prüffrage:** Was bedeutet `org.junit.jupiter:junit-jupiter:5.10.2` Teil für Teil?
- **Übersetzung:** en: Anatomy of build.gradle | fr: Structure de build.gradle
- **Stufe:** 1

### 03 Tasks und Lebenszyklus
- **Ziele:** Tasks anzeigen (`./gradlew tasks`), Abhängigkeiten zwischen Tasks, `compileJava` → `test` → `build`; einen eigenen Task schreiben
- **Übung:** uebungen/02-eigener-task
- **Prüffrage:** Warum läuft `compileJava`, wenn man nur `test` aufruft?
- **Übersetzung:** en: Tasks and lifecycle | fr: Tâches et cycle de vie
- **Stufe:** 2

### 04 Projektstruktur, Toolchain, Encoding
- **Ziele:** `src/main/java`, `src/test/java`, `src/main/resources`; Java-Toolchain festlegen; UTF-8 erzwingen; `settings.gradle`
- **Prüffrage:** Was passiert, wenn auf dem Rechner ein anderes JDK installiert ist als in der Toolchain steht?
- **Übersetzung:** en: Project layout, toolchain, encoding | fr: Structure du projet, toolchain, encodage
- **Stufe:** 2

### 05 Tests und Berichte
- **Ziele:** `useJUnitPlatform()`, Testbericht unter `build/reports/tests`, JaCoCo-Coverage einbinden und lesen; Unit- und Integrationstests (`*IT`) in getrennte Tasks legen, damit `test` schnell bleibt und `check` alles ausführt
- **Übung:** uebungen/03-integrationstests
- **Prüffrage:** Wo findet man nach `./gradlew test` heraus, welcher Test warum fehlgeschlagen ist – und warum laufen Integrationstests nicht bei jedem `test`?
- **Übersetzung:** en: Tests and reports | fr: Tests et rapports
- **Stufe:** 2

### 06 IDE-Integration und Multi-Projekt
- **Ziele:** Import in Eclipse (Buildship) und IntelliJ; „Refresh Gradle Project" nach Änderungen; Grundidee Multi-Projekt (`include`)
- **Prüffrage:** Warum sieht Eclipse eine neue Abhängigkeit erst nach dem Refresh?
- **Übersetzung:** en: IDE integration and multi-project builds | fr: Intégration IDE et multi-projets
- **Stufe:** 2

### 07 Qualitätswächter im Build
- **Ziele:** Statische Analyse als Teil des Builds: Checkstyle, PMD, SpotBugs einbinden; Regeln als Datei im Repository; `check` bricht bei Verstößen ab, damit Reviews sich um Inhalt statt Form kümmern
- **Übung:** uebungen/04-checkstyle
- **Prüffrage:** Was prüft Checkstyle, was ein Test nie prüfen kann – und was prüft ein Test, was Checkstyle nie sieht?
- **Übersetzung:** en: Quality gates in the build | fr: Garde-fous qualité dans le build
- **Stufe:** 2

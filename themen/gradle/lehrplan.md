---
thema: gradle
titel: Gradle
voraussetzungen: [java]
zielgruppe: [azubi, student, kollege]
reihenfolge: 70
---

# Gradle

Gradle baut unsere Java-Projekte: kompilieren, testen, packen, Abhängigkeiten laden.
Wer Gradle versteht, kann ein Projekt auf jedem Rechner reproduzierbar bauen und weiß,
was die IDE im Hintergrund tut.

Am Ende kann der Lernende eine `build.gradle` lesen und ergänzen, Abhängigkeiten
hinzufügen, eigene Tasks schreiben, Tests im Build laufen lassen und ein Gradle-Projekt
in Eclipse oder IntelliJ importieren. Die Lektionen 08 bis 12 machen aus dem Nutzer jemanden, der
den Build *versteht*: warum er schnell ist, was ein Scope bedeutet, wo Versionen wohnen,
wie aus dem Projekt eine lauffähige Datei wird – und wie man eine `build.gradle.kts` liest.

## Lektionen

### 01 Was ein Build-Tool tut, der Wrapper
- **Ziele:** Warum nicht einfach `javac`; `gradlew` vs. installiertes Gradle; `./gradlew build` und `./gradlew test` ausführen; wo die Ausgabe landet (`build/`); das Projekt in der IDE öffnen – Eclipse: *Import → Existing Gradle Project* (Buildship), IntelliJ: Ordner öffnen, VS Code: Extension Pack for Java – und verstehen, dass die IDE denselben Wrapper aufruft
- **Übung:** uebungen/01-build-datei
- **Prüffrage:** Warum liegt `gradlew` im Repository, Gradle selbst aber nicht?
- **Übersetzung:** en: What a build tool does, the wrapper | fr: Ce que fait un outil de build, le wrapper
- **Stufe:** 1

### 02 Aufbau von build.gradle
- **Ziele:** `plugins`, `repositories`, `dependencies`; `implementation` vs. `testImplementation`; Versionen und Maven-Koordinaten lesen; nach jeder Änderung an `build.gradle` die IDE nachziehen lassen (Eclipse: *Gradle → Refresh Gradle Project*, IntelliJ: Reload) – die IDE liest die Datei nicht von selbst neu
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

### 06 Das Gradle-Monorepo: Multi-Projekt-Builds
- **Ziele:** Aufbau eines Monorepos (Multi-Projekt-Build) verstehen; Teilprojekte in `settings.gradle` mit `include` registrieren; Abhängigkeiten zwischen lokalen Modulen mit `implementation project(':kern')` statt Maven-Koordinaten; Build-Logik zentralisieren: Konventions-Plugin unter `buildSrc/` (`groovy-gradle-plugin`, eine `.gradle`-Datei = ein Plugin) statt dreimal kopierter `build.gradle`; `subprojects { }` als älterer Weg kennen; einzelne Module bauen (`./gradlew :kern:test`); Gradle berechnet die Reihenfolge aus dem Abhängigkeitsgraphen (DAG) – zirkuläre Abhängigkeiten sind verboten und brechen den Build ab
- **Übung:** uebungen/09-monorepo
- **Prüffrage:** Warum deklariert man die Abhängigkeit zu einem anderen Modul im selben Monorepo mit `implementation project(':kern')` statt über Maven-Koordinaten (Group, Artifact, Version)? Und: `:konsole` hängt von `:kern` ab – wer entscheidet, dass `kern` zuerst kompiliert wird, und was passiert, wenn `kern` zusätzlich von `konsole` abhängt?
- **Übersetzung:** en: The Gradle monorepo: multi-project builds | fr: Le monorepo Gradle : builds multi-projets
- **Stufe:** 2

Leitfaden: Visuell an der Tafel oder in der App mit einer Baumstruktur starten:

```
mein-monorepo/
├── settings.gradle        <-- include 'core', 'api', 'ui'
├── build.gradle           <-- globale Einstellungen (besser: buildSrc/)
├── core/
│   └── build.gradle       <-- reine Java-Logik
├── api/
│   └── build.gradle       <-- hängt von :core ab
└── ui/
    └── build.gradle       <-- hängt von :core ab (Vaadin)
```

So sehen unsere echten Projekte aus; in der Übung heißen die Module `kern`, `konsole`
und `bericht` und bleiben reines Java, weil Gradle *vor* Spring und Vaadin unterrichtet
wird. Zeige, wie Gradle die Build-Reihenfolge automatisch berechnet: Aus den
Abhängigkeiten entsteht ein gerichteter Graph ohne Zyklen (DAG). Wenn `:ui` von `:core`
abhängt, weiß Gradle von allein, dass `:core` zuerst kompiliert werden muss – und dass
`:api` und `:ui` parallel laufen dürfen. Führe hier das Verbot zirkulärer Abhängigkeiten
ein: A hängt von B ab und B von A – dann gibt es keine Reihenfolge mehr, und Gradle bricht
ab mit `Circular dependency between the following tasks`, noch bevor ein Compiler
startet. Die Übung lässt den Lernenden genau das am Ende absichtlich ausprobieren
(Hinweis 4). Frage zum Einstieg in die Übung: „Was passiert bei `./gradlew :bericht:test`,
wenn sich `kern` geändert hat?" – Antwort: `kern` wird neu gebaut, `konsole` nicht.

### 07 Qualitätswächter im Build
- **Ziele:** Statische Analyse als Teil des Builds: Checkstyle, PMD, SpotBugs einbinden; Regeln als Datei im Repository; `check` bricht bei Verstößen ab, damit Reviews sich um Inhalt statt Form kümmern
- **Übung:** uebungen/04-checkstyle
- **Prüffrage:** Was prüft Checkstyle, was ein Test nie prüfen kann – und was prüft ein Test, was Checkstyle nie sieht?
- **Übersetzung:** en: Quality gates in the build | fr: Garde-fous qualité dans le build
- **Stufe:** 2

### 08 Keine Zeit verschwenden: inkrementelle Builds und Caching
- **Ziele:** Warum der zweite Lauf schneller ist: Up-to-date-Prüfung über Fingerabdrücke von Inputs und Outputs; `inputs`/`outputs` an eigenen Tasks deklarieren; `[UP-TO-DATE]` vs. `[FROM-CACHE]`; lokaler Build-Cache (`org.gradle.caching=true`) und Remote-Cache in der Pipeline; `--info` erklärt, warum ein Task läuft; Alltagsschalter `--offline`, `--refresh-dependencies`, `-q`, `--scan`; die zwei `gradle.properties`: Projekt (versioniert, für alle gleich) und `~/.gradle` (maschinenlokal – Proxy, Truststore, Daemon-Speicher – nie ins Repo)
- **Übung:** uebungen/05-inkrementell
- **Prüffrage:** Warum steht neben manchen Tasks `UP-TO-DATE` oder `FROM-CACHE`, was ist der Unterschied – und was bedeutet das für die Build-Zeit? Und: Welche Einstellung gehört in die `gradle.properties` des Projekts, welche in die unter `~/.gradle`?
- **Übersetzung:** en: No wasted time: incremental builds and caching | fr: Pas de temps perdu : builds incrémentaux et cache
- **Stufe:** 2

Leitfaden: Mit Übung 02 beginnen – `zaehle` läuft jedes Mal, weil Gradle nichts über
Eingaben und Ausgaben weiß. Dann `./gradlew test` zweimal: `compileJava UP-TO-DATE`. Die
Frage „woher weiß Gradle das?" führt zur Übung.

### 09 Feinarbeit am Klassenpfad: Scopes und Konflikte
- **Ziele:** `implementation`, `compileOnly`, `runtimeOnly` und die `test`-Varianten – wer braucht die Bibliothek, und wann; typische Fälle: JDBC-Treiber `runtimeOnly` (Spring-Übung 03), Annotationen `compileOnly`, JUnit nur im Test; transitive Abhängigkeiten sichtbar machen: `./gradlew dependencies --configuration runtimeClasspath`, `dependencyInsight --dependency <name>`; Versionskonflikte: Gradle nimmt die höchste Version, `exclude group:/module:` wirft Mitgebrachtes raus (so hält die Vaadin-Übung die Pro-Komponenten draußen); `api` vs. `implementation` in Bibliotheken
- **Übung:** uebungen/06-scopes
- **Prüffrage:** Welchen Scope wählst du für einen PostgreSQL-Treiber, den du im Java-Code nie importierst, der aber zur Laufzeit da sein muss – und was passiert bei `compileOnly`?
- **Übersetzung:** en: Fine-tuning the classpath: scopes and conflicts | fr: Réglage fin du classpath : scopes et conflits
- **Stufe:** 2

### 10 Ordnung im System: Version Catalog
- **Ziele:** Weg von verstreuten Versionsnummern: `gradle/libs.versions.toml` mit `[versions]`, `[libraries]`, `[bundles]`, `[plugins]`; typsicherer Zugriff `libs.mockito.core`, `libs.bundles.test`, `platform(libs.junit.bom)`; Tippfehler fallen beim Laden auf; der Gewinn im Multi-Projekt (Lektion 06): eine Datei, alle Teilprojekte gleich; warum unsere Übungen trotzdem inline Versionen tragen (jede Übung allein lesbar)
- **Übung:** uebungen/07-version-catalog
- **Prüffrage:** Welchen Vorteil bietet `libs.versions.toml` in einem Build aus mehreren Teilprojekten – und was ändert sich, wenn JUnit angehoben werden soll?
- **Übersetzung:** en: Order in the system: version catalogs | fr: De l'ordre : le catalogue de versions
- **Stufe:** 2

### 11 Bereit für den Server: ausführbare Uber-JARs
- **Ziele:** Was im normalen `jar` steckt – und was nicht (`jar tf`); warum `java -jar` mit `NoClassDefFoundError` scheitert, obwohl IDE und `./gradlew run` funktionieren (Klassenpfad); Fat-/Uber-JAR von Hand (`Jar`-Task mit `zipTree` über `runtimeClasspath`) und mit dem Shadow-Plugin (`com.gradleup.shadow`, alte Kennung läuft mit Gradle 9 nicht mehr); `bootJar` bei Spring Boot als eingebaute Variante; wann *kein* Uber-JAR: Bibliotheken werden als normales JAR mit `maven-publish` veröffentlicht (Stufe 3)
- **Übung:** uebungen/08-uber-jar
- **Prüffrage:** Warum schlägt `java -jar app.jar` bei einem normalen Gradle-JAR fehl, sobald das Projekt externe Abhängigkeiten nutzt – und was enthält das Uber-JAR zusätzlich?
- **Übersetzung:** en: Ready for the server: executable uber-JARs | fr: Prêt pour le serveur : uber-JARs exécutables
- **Stufe:** 2

### 12 Kotlin DSL lesen und übersetzen
- **Ziele:** `build.gradle.kts` ist heute der Standard in Doku, Spring Initializr und vielen Projekten – lesen können ist Pflicht, schreiben Geschmackssache; die Unterschiede: `plugins { id("java") }`, `implementation("…")`, `tasks.register<Test>("integrationTest") { }`, `tasks.named<Test>("test") { useJUnitPlatform() }`, `val` statt `def`, doppelte Anführungszeichen; Vorteile (Typprüfung, IDE-Vervollständigung) und Preis (langsameres erstes Laden); eine bekannte `build.gradle` Zeile für Zeile übersetzen; keine Übung – wir bleiben in diesem Lernsystem bei Groovy
- **Prüffrage:** Wie sieht `tasks.register('integrationTest', Test) { include '**/*IT.class' }` aus Übung 03 in der Kotlin DSL aus – und woran erkennst du auf den ersten Blick, welche DSL eine Build-Datei verwendet?
- **Übersetzung:** en: Reading and translating the Kotlin DSL | fr: Lire et traduire le DSL Kotlin
- **Stufe:** 2

Leitfaden: Die `build.gradle` aus Übung 03 (Integrationstests) gemeinsam nach Kotlin
übersetzen, Block für Block; der Lernende tippt, Claude prüft nur die Syntax. Danach eine
fremde `build.gradle.kts` (Spring Initializr) vorlesen lassen.

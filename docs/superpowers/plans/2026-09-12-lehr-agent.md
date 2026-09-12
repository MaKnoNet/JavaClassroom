# Lehr-Agent – Umsetzungsplan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Ein Claude-Code-Repository, in dem `/lernen <thema>` Azubis, Studenten und Kollegen interaktiv unterrichtet – mit Themenordnern (Lehrplan + Übungen), persönlicher Fortschrittsdatei, HTML-Fortschrittsseite, Umgebungsprüfung vor jeder Lektion und einer Ausbilder-Skill `/thema-anlegen`.

**Architecture:** Die Lehrerrolle steht in `AGENTS.md`, der Einstieg in zwei Skills. Inhalte sind Markdown (`themen/<thema>/lehrplan.md`, `fortschritt/<name>.md`). Ein Single-File-Java-Programm `tools/Fortschritt.java` parst beide und schreibt `arbeit/fortschritt.html` aus einer Vorlage; die Darstellung rechnet JavaScript im Browser. Java-Übungen sind eigenständige Gradle-Projekte aus einer Schablone. Vor jeder Lektion prüft Claude per Shell, ob JDK, Git und Browser vorhanden sind, und leitet sonst die Installation an.

**Tech Stack:** Claude Code (Skills, AGENTS.md), Java 21 (Single-File-Launch, kein Build für das Tool), Gradle 9.6.1 Wrapper + JUnit 5 für Übungen, statisches HTML/CSS/JS ohne Bibliotheken.

**Spec:** `docs/superpowers/specs/2026-09-12-lehr-agent-design.md`. Ergänzung vom 2026-09-12 (nach Spec-Freigabe): Lernende haben nichts installiert; die Umgebung wird vor jeder Lektion geprüft.

**Arbeitsverzeichnis:** alle Befehle laufen im Repo-Root `C:\Entwicklung\KI\Claude\Teacher` (Bash-Syntax; unter PowerShell `.\gradlew.bat` statt `./gradlew`).

---

## Dateistruktur

| Datei | Verantwortung |
|---|---|
| `.gitignore` | `fortschritt/*.md` außer `_beispiel.md`, `arbeit/`, Build-Reste, `settings.local.json` |
| `CLAUDE.md` | nur `@AGENTS.md` |
| `AGENTS.md` | Lehrerrolle, Haltungsregeln, Umgebungsprüfung, Ordnerkonventionen, Dateiformate |
| `README.md` | Anleitung für Lernende (inkl. Installation) und Ausbilder |
| `fortschritt/_beispiel.md` | Schablone der Fortschrittsdatei |
| `themen/_schablone/lehrplan.md` | Schablone eines Lehrplans |
| `themen/_schablone/uebung-java/` | Gradle-Projektgerüst (Wrapper, build.gradle, JUnit 5) |
| `themen/_schablone/uebung-web/` | Gerüst für HTML/CSS/JS-Übungen |
| `tools/Fortschritt.java` | Parser Lehrplan + Fortschritt → JSON → HTML aus Vorlage |
| `tools/FortschrittTest.java` | Tests für die Parser (plain Java, `main` + Assertions) |
| `tools/fortschritt.template.html` | HTML-Vorlage mit JS-Rendering, Platzhalter `/*DATEN*/{}` |
| `.claude/skills/lernen/SKILL.md` | Ablauf einer Stunde inkl. Umgebungsprüfung |
| `.claude/skills/thema-anlegen/SKILL.md` | Ablauf zum Anlegen eines Themas |
| `themen/<thema>/lehrplan.md` | je Thema: git, java, gradle, junit, html, css, javascript |
| `themen/<thema>/uebungen/NN-name/` | je Thema zwei Übungen mit `AUFGABE.md`, Projekt, `loesung/` |

---

### Task 1: Grundgerüst des Repos

**Files:**
- Create: `.gitignore`
- Create: `CLAUDE.md`
- Create: `AGENTS.md`
- Create: `README.md`
- Create: `fortschritt/_beispiel.md`

- [ ] **Step 1: `.gitignore` anlegen**

```gitignore
# Persönlicher Lernfortschritt – bleibt lokal
fortschritt/*.md
!fortschritt/_beispiel.md

# Arbeitskopien der Übungen und generierte Fortschrittsseite
arbeit/

# Build-Reste
build/
.gradle/
bin/
out/
.idea/
.settings/
.project
.classpath

# Maschinenlokale Claude-Einstellungen
.claude/settings.local.json
```

- [ ] **Step 2: `CLAUDE.md` anlegen**

```markdown
@AGENTS.md
```

- [ ] **Step 3: `AGENTS.md` anlegen**

````markdown
# Lehr-Agent für Softwareentwicklung

Dieses Repository ist ein Lernsystem. Claude Code ist hier **Lehrer**, der Nutzer ist
**Lernender** (Azubi, Student, neuer Kollege) oder **Ausbilder**. Diese Datei ist die Quelle
der Wahrheit für Rolle, Regeln und Dateiformate.

## Rolle und Haltung

Du unterrichtest. Das gilt auch ohne `/lernen`, sobald jemand hier eine Frage zu einem
Thema stellt.

- **Fragen statt vorsagen.** Führe mit Fragen zur Lösung. Zeige die Lösung erst nach dem
  dritten Anlauf oder auf ausdrücklichen Wunsch.
- **Ein Schritt pro Nachricht.** Ein Konzept erklären, eine Frage stellen, eine Aufgabe
  geben – nie alles auf einmal.
- **Fehler sind Lernanlass, kein Vorwurf.** Benenne, was passiert ist und warum, ohne
  Wertung der Person.
- **Unterrichtssprache** ist die in der Fortschrittsdatei eingetragene (`sprache`). Code,
  Bezeichner, Befehle und Fachbegriffe bleiben Englisch; die Erklärung dazu in der
  Unterrichtssprache. Lehrpläne und Aufgabentexte sind Deutsch – übersetze sie live.
- **Nicht abschweifen.** Bleib bei der aktuellen Lektion, außer der Lernende fragt.
- **Tempo bestimmt der Lernende.** Frage, ob es weitergehen soll, statt vorauszueilen.
- **Code des Lernenden nie stillschweigend ändern.** Vorschläge immer als Vorschlag mit
  Begründung; die Änderung macht der Lernende selbst.
- **Rückmeldung auf das echte Ergebnis:** Tests laufen lassen (`./gradlew test`),
  HTML im Browser öffnen, `git log` lesen – nicht raten, ob es funktioniert.
- **Nichts als installiert voraussetzen.** Lernende kommen mit leeren Rechnern. Vor jeder
  Lektion wird die Umgebung geprüft (siehe unten). Fehlt etwas, ist die Installation Teil
  der Stunde – geführt, Schritt für Schritt, mit Prüfung danach.

## Umgebungsprüfung (vor jeder Lektion)

Prüfe per Shell, was das Thema braucht. Melde jedes Ergebnis kurz, leite bei Fehlen an.

| Thema | Braucht | Prüfbefehl | Erwartung |
|---|---|---|---|
| alle | Git | `git --version` | `git version 2.x` |
| java, gradle, junit | JDK 21 | `java -version` | Zeile mit `21.` |
| java, gradle, junit | Gradle via Wrapper | `./gradlew --version` in der Übung | lädt beim ersten Mal, dann `Gradle 9.6.1` |
| html, css, javascript | Browser-Ansicht von Claude Code oder ein Browser | – | Seite lässt sich öffnen |
| je nach `ide` | Eclipse / IntelliJ / VS Code | Lernenden fragen | – |

Installationshilfe, wenn etwas fehlt (immer den Befehl zeigen, ausführen lassen, danach
erneut prüfen; die Installation selbst macht der Lernende, nicht Claude):

| | Windows | macOS | Linux (Debian/Ubuntu) |
|---|---|---|---|
| JDK 21 | `winget install Amazon.Corretto.21.JDK` | `brew install --cask corretto@21` | `sudo apt install openjdk-21-jdk` |
| Git | `winget install Git.Git` | `brew install git` (oder Xcode CLT) | `sudo apt install git` |
| Eclipse | `winget install EclipseAdoptium.Temurin.21.JDK` ist **nicht** Eclipse; Eclipse IDE for Java Developers von eclipse.org laden | eclipse.org | eclipse.org oder Snap |
| IntelliJ | `winget install JetBrains.IntelliJIDEA.Community` | `brew install --cask intellij-idea-ce` | Snap `intellij-idea-community` |
| VS Code | `winget install Microsoft.VisualStudioCode` | `brew install --cask visual-studio-code` | Snap `code` |

Nach einer Installation ein **neues Terminal** öffnen lassen (PATH). Erst-Git-Konfiguration
gehört in die Git-Lektion 01, nicht in die Umgebungsprüfung: `git config --global user.name`
und `user.email`.

## Ordner

| Pfad | Inhalt |
|---|---|
| `themen/<thema>/lehrplan.md` | Lehrplan mit Lektionen in Reihenfolge |
| `themen/<thema>/uebungen/NN-name/` | Übung: `AUFGABE.md`, Projekt, `loesung/` |
| `themen/_schablone/` | Vorlagen für neue Themen und Übungen |
| `fortschritt/<name>.md` | persönlicher Fortschritt, nicht versioniert |
| `fortschritt/_beispiel.md` | Schablone dafür |
| `arbeit/<thema>/NN-name/` | Arbeitskopie einer Übung, nicht versioniert |
| `arbeit/fortschritt.html` | generierte Fortschrittsseite |
| `tools/Fortschritt.java` | erzeugt die Fortschrittsseite |

**Übungen werden immer nach `arbeit/` kopiert** und dort bearbeitet. `themen/` bleibt
unverändert, damit `git pull` neue Inhalte konfliktfrei bringt.

**Referenzlösungen** liegen unter `uebungen/NN-name/loesung/`. Öffne sie nicht ungefragt
im Unterricht; nutze sie, um Rückmeldung zu geben.

## Dateiformat Lehrplan (`themen/<thema>/lehrplan.md`)

```markdown
---
thema: java
titel: Java
voraussetzungen: []
zielgruppe: [azubi, student]
---

# Java

Worum es geht, was am Ende sitzen soll.

## Lektionen

### 01 Erste Klasse
- **Ziele:** …
- **Übung:** uebungen/01-erste-klasse
- **Prüffrage:** …
```

Konvention: Lektionsnummer zweistellig, Überschrift Ebene 3, die drei Aufzählungspunkte in
dieser Reihenfolge. `Übung:` darf fehlen. Weiterer Text ist frei und dient als Leitfaden –
unterrichte *entlang*, nicht *aus* dem Text.

## Dateiformat Fortschritt (`fortschritt/<name>.md`)

```markdown
---
name: Max Mustermann
sprache: de
rolle: azubi
ide: eclipse
vorwissen: "Zwei Sätze."
---

## java
- 01: fertig 2026-09-10
- 03: begonnen 2026-09-12
- notizen: Was beim nächsten Mal zu beachten ist.
```

`sprache`: ISO-Code (de, en, fr, …). `rolle`: azubi | student | kollege.
`ide`: eclipse | intellij | vscode | keine. Status je Lektion: `fertig` | `begonnen`, Datum
ISO. Nicht erwähnte Lektionen sind offen. Der Dateiname ist der Name in Kleinbuchstaben
ohne Leerzeichen (`max-mustermann.md`).

## Fortschrittsseite

```bash
java tools/Fortschritt.java <name>
```

liest `fortschritt/<name>.md` und alle Lehrpläne, schreibt `arbeit/fortschritt.html`.
Muss im Repo-Root laufen. Danach die Datei in der Browser-Ansicht öffnen.

## IDE-Hinweise

- **Eclipse:** Übung per *File → Import → Existing Gradle Project* öffnen. Nach Änderungen
  an `build.gradle`: Rechtsklick auf Projekt → *Gradle → Refresh Gradle Project*. Tests:
  Rechtsklick auf Testklasse → *Run As → JUnit Test*.
- **IntelliJ:** Ordner mit *Open* öffnen, Gradle-Import bestätigen. Tests: grüner Pfeil
  neben der Testklasse.
- **VS Code:** Ordner öffnen, Extension Pack for Java. Tests über die Testing-Ansicht.
- **Keine IDE:** alles über `./gradlew test` im Terminal und einen Editor.

## Für Ausbilder

Neues Thema: `/thema-anlegen <name>`. Firmencode gehört nicht in dieses Repo –
Framework-Lehrpläne verweisen per relativem Pfad oder Umgebungsvariable darauf.
````

- [ ] **Step 4: `README.md` anlegen**

````markdown
# Teacher – Lernen mit Claude Code

Interaktiver Unterricht zu Git, Java, Gradle, JUnit, HTML, CSS und JavaScript.

## Für Lernende

Du brauchst zu Beginn **nichts** außer Claude Code und einem Clone dieses Repos – alles
Weitere (JDK, Git, IDE) prüft der Lehrer vor jeder Lektion und hilft bei der Installation.

1. Claude Code installieren (Desktop-App oder Terminal, siehe claude.com/claude-code).
2. Dieses Repository klonen oder als ZIP herunterladen und entpacken.
3. Im Repo-Ordner Claude Code starten und tippen: `/lernen`.
4. Beim ersten Mal wirst du nach Name, Sprache, Rolle, IDE und Vorwissen gefragt.
   Das landet in `fortschritt/<dein-name>.md` – nur auf deinem Rechner.

Übungen werden nach `arbeit/` kopiert. Dort arbeitest du; `themen/` bleibt unverändert.

Deinen Stand siehst du in `arbeit/fortschritt.html` – die Datei kannst du deinem
Ausbilder schicken.

## Für Ausbilder

- Neues Thema: `/thema-anlegen <name>` in Claude Code.
- Lehrpläne liegen unter `themen/<thema>/lehrplan.md`, Übungen darunter in `uebungen/`.
- Alle Konventionen: `AGENTS.md`.

## Fortschrittsseite von Hand erzeugen

```bash
java tools/Fortschritt.java <name>
```
````

- [ ] **Step 5: `fortschritt/_beispiel.md` anlegen**

```markdown
---
name: Max Mustermann
sprache: de
rolle: azubi
ide: eclipse
vorwissen: "Erstes Lehrjahr, Grundlagen Java aus der Berufsschule, kein Git."
---

## java
- 01: fertig 2026-09-10
- 02: fertig 2026-09-10
- 03: begonnen 2026-09-12
- notizen: Tut sich mit Referenz vs. Wert schwer – nächstes Mal Beispiel mit Listen.

## git
- 01: fertig 2026-09-11
```

- [ ] **Step 6: Prüfen, dass `.gitignore` greift**

Run: `mkdir -p arbeit && echo test > fortschritt/test-person.md && git status --short`
Expected: `.gitignore`, `AGENTS.md`, `CLAUDE.md`, `README.md`, `fortschritt/_beispiel.md` als `??`; `fortschritt/test-person.md` und `arbeit/` erscheinen **nicht**.
Danach: `rm fortschritt/test-person.md`

- [ ] **Step 7: Commit**

```bash
git add .gitignore CLAUDE.md AGENTS.md README.md fortschritt/_beispiel.md
git commit -m "feat: Grundgerüst mit Lehrerrolle, Umgebungsprüfung und Fortschritts-Schablone"
```

---

### Task 2: Schablonen für Themen und Übungen

**Files:**
- Create: `themen/_schablone/lehrplan.md`
- Create: `themen/_schablone/uebung-java/` (gradlew, gradlew.bat, gradle/wrapper/*, settings.gradle, build.gradle, src/main/java/.gitkeep, src/test/java/.gitkeep)
- Create: `themen/_schablone/uebung-web/index.html`, `style.css`, `app.js`

- [ ] **Step 1: Lehrplan-Schablone**

```markdown
---
thema: NAME
titel: Titel des Themas
voraussetzungen: []
zielgruppe: [azubi, student, kollege]
---

# Titel des Themas

Worum es geht (ein Absatz). Was am Ende sitzen soll (ein Absatz).

Für Firmenthemen: Quellmaterial liegt außerhalb dieses Repos. Pfad: `../<framework-repo>`
oder Umgebungsvariable `FRAMEWORK_HOME`. Firmencode wird hier nicht abgelegt.

## Lektionen

### 01 Titel der ersten Lektion
- **Ziele:** Was der Lernende danach kann
- **Übung:** uebungen/01-name
- **Prüffrage:** Eine Frage, die das Verständnis prüft

### 02 Titel der zweiten Lektion
- **Ziele:** …
- **Prüffrage:** …
```

- [ ] **Step 2: Gradle-Wrapper aus XMLViewer kopieren**

```bash
mkdir -p themen/_schablone/uebung-java/gradle/wrapper themen/_schablone/uebung-java/src/main/java themen/_schablone/uebung-java/src/test/java
cp ../XMLViewer/gradlew ../XMLViewer/gradlew.bat themen/_schablone/uebung-java/
cp ../XMLViewer/gradle/wrapper/gradle-wrapper.jar ../XMLViewer/gradle/wrapper/gradle-wrapper.properties themen/_schablone/uebung-java/gradle/wrapper/
touch themen/_schablone/uebung-java/src/main/java/.gitkeep themen/_schablone/uebung-java/src/test/java/.gitkeep
```

- [ ] **Step 3: `settings.gradle` und `build.gradle` der Schablone**

`themen/_schablone/uebung-java/settings.gradle`:
```groovy
rootProject.name = 'uebung'
```

`themen/_schablone/uebung-java/build.gradle`:
```groovy
plugins {
    id 'java'
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

// Quellen enthalten Umlaute; ohne diese Zeile nimmt javac unter Windows Cp1252.
tasks.withType(JavaCompile).configureEach {
    options.encoding = 'UTF-8'
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation platform('org.junit:junit-bom:5.10.2')
    testImplementation 'org.junit.jupiter:junit-jupiter'
    testRuntimeOnly 'org.junit.platform:junit-platform-launcher'
}

test {
    useJUnitPlatform()
    testLogging {
        events 'passed', 'failed', 'skipped'
        exceptionFormat 'full'
    }
}
```

- [ ] **Step 4: Schablone bauen (leer, muss trotzdem durchlaufen)**

Run: `cd themen/_schablone/uebung-java && ./gradlew test --no-daemon -q; echo EXIT=$?; cd ../../..`
Expected: `EXIT=0` (beim ersten Lauf lädt der Wrapper Gradle 9.6.1 herunter).

- [ ] **Step 5: Web-Schablone**

`themen/_schablone/uebung-web/index.html`:
```html
<!DOCTYPE html>
<html lang="de">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Übung</title>
  <link rel="stylesheet" href="style.css">
</head>
<body>
  <h1>Übung</h1>
  <script src="app.js"></script>
</body>
</html>
```

`themen/_schablone/uebung-web/style.css`:
```css
body {
  font-family: system-ui, sans-serif;
  margin: 2rem;
}
```

`themen/_schablone/uebung-web/app.js`:
```javascript
// Hier kommt dein JavaScript hin.
```

- [ ] **Step 6: Ausführbarkeit von gradlew in Git eintragen und committen**

```bash
git add themen/_schablone
git update-index --chmod=+x themen/_schablone/uebung-java/gradlew
git commit -m "feat: Schablonen für Lehrplan, Java-Übung (Gradle/JUnit 5) und Web-Übung"
```

---

### Task 3: `Fortschritt.java` – Lehrplan parsen

**Files:**
- Create: `tools/Fortschritt.java`
- Create: `tools/FortschrittTest.java`

Tests laufen ohne Framework: `javac` + `java`, Assertions werfen `AssertionError`.

- [ ] **Step 1: Test schreiben**

`tools/FortschrittTest.java`:
```java
import java.util.List;
import java.util.Objects;

/** Tests für Fortschritt.java. Aufruf siehe Plan: javac + java -cp build/tools FortschrittTest */
public final class FortschrittTest {

    private static int fehler = 0;

    public static void main(String[] args) {
        lehrplanMitZweiLektionen();
        lehrplanOhneUebung();
        if (fehler > 0) {
            System.err.println(fehler + " Test(s) fehlgeschlagen");
            System.exit(1);
        }
        System.out.println("Alle Tests bestanden");
    }

    static void lehrplanMitZweiLektionen() {
        String md = """
            ---
            thema: java
            titel: Java
            voraussetzungen: [git, gradle]
            zielgruppe: [azubi]
            ---

            # Java

            ## Lektionen

            ### 01 Erste Klasse
            - **Ziele:** Klasse verstehen
            - **Übung:** uebungen/01-erste-klasse
            - **Prüffrage:** Was ist eine Klasse?

            ### 02 Variablen
            - **Ziele:** Typen
            - **Übung:** uebungen/02-variablen
            - **Prüffrage:** int oder Integer?
            """;
        Fortschritt.Lehrplan plan = Fortschritt.parseLehrplan(md);
        pruefe("thema", "java", plan.thema());
        pruefe("titel", "Java", plan.titel());
        pruefe("voraussetzungen", List.of("git", "gradle"), plan.voraussetzungen());
        pruefe("anzahl lektionen", 2, plan.lektionen().size());
        pruefe("nummer 01", "01", plan.lektionen().get(0).nummer());
        pruefe("titel 01", "Erste Klasse", plan.lektionen().get(0).titel());
        pruefe("uebung 01", "uebungen/01-erste-klasse", plan.lektionen().get(0).uebung());
        pruefe("titel 02", "Variablen", plan.lektionen().get(1).titel());
    }

    static void lehrplanOhneUebung() {
        String md = """
            ---
            thema: git
            titel: Git
            voraussetzungen: []
            ---
            ### 01 Warum Versionierung
            - **Ziele:** Motivation
            - **Prüffrage:** Wozu?
            """;
        Fortschritt.Lehrplan plan = Fortschritt.parseLehrplan(md);
        pruefe("leere voraussetzungen", List.of(), plan.voraussetzungen());
        pruefe("uebung fehlt", null, plan.lektionen().get(0).uebung());
    }

    static void pruefe(String name, Object erwartet, Object tatsaechlich) {
        if (!Objects.equals(erwartet, tatsaechlich)) {
            fehler++;
            System.err.println("FEHLER " + name + ": erwartet <" + erwartet + ">, war <" + tatsaechlich + ">");
        }
    }
}
```

- [ ] **Step 2: Test laufen lassen – muss scheitern**

Run: `javac -encoding UTF-8 -d build/tools tools/Fortschritt.java tools/FortschrittTest.java && java -cp build/tools FortschrittTest`
Expected: Compilerfehler `file not found: tools/Fortschritt.java` bzw. `cannot find symbol Fortschritt`.

- [ ] **Step 3: Parser implementieren**

`tools/Fortschritt.java`:
```java
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * Erzeugt die Fortschrittsseite: liest fortschritt/<name>.md und alle themen/<x>/lehrplan.md,
 * baut JSON und setzt es in tools/fortschritt.template.html ein.
 * Aufruf im Repo-Root: java tools/Fortschritt.java <name>
 * Keine Abhängigkeiten, Java 21.
 */
public final class Fortschritt {

    record Lektion(String nummer, String titel, String uebung) {}
    record Lehrplan(String thema, String titel, List<String> voraussetzungen, List<Lektion> lektionen) {}

    private static final Pattern LEKTION = Pattern.compile("^### (\\d{2}) (.+)$");
    private static final Pattern UEBUNG = Pattern.compile("^- \\*\\*\u00DCbung:\\*\\* (.+)$");

    /** Liest die YAML-Frontmatter zwischen den beiden ersten `---`-Zeilen als flache Schlüssel/Wert-Paare. */
    static Map<String, String> parseFrontmatter(String markdown) {
        Map<String, String> felder = new LinkedHashMap<>();
        String[] zeilen = markdown.split("\\R");
        if (zeilen.length == 0 || !zeilen[0].trim().equals("---")) {
            return felder;
        }
        for (int i = 1; i < zeilen.length; i++) {
            String zeile = zeilen[i];
            if (zeile.trim().equals("---")) {
                break;
            }
            int doppelpunkt = zeile.indexOf(':');
            if (doppelpunkt < 0) {
                continue;
            }
            String schluessel = zeile.substring(0, doppelpunkt).trim();
            String wert = zeile.substring(doppelpunkt + 1).trim();
            int kommentar = wert.indexOf(" #");
            if (kommentar >= 0) {
                wert = wert.substring(0, kommentar).trim();
            }
            if (wert.length() >= 2 && wert.startsWith("\"") && wert.endsWith("\"")) {
                wert = wert.substring(1, wert.length() - 1);
            }
            felder.put(schluessel, wert);
        }
        return felder;
    }

    /** `[a, b]` → Liste; leer oder null → leere Liste. */
    static List<String> parseListe(String wert) {
        if (wert == null) {
            return List.of();
        }
        String inhalt = wert.trim();
        if (inhalt.startsWith("[")) {
            inhalt = inhalt.substring(1);
        }
        if (inhalt.endsWith("]")) {
            inhalt = inhalt.substring(0, inhalt.length() - 1);
        }
        List<String> ergebnis = new ArrayList<>();
        for (String teil : inhalt.split(",")) {
            String bereinigt = teil.trim();
            if (!bereinigt.isEmpty()) {
                ergebnis.add(bereinigt);
            }
        }
        return ergebnis;
    }

    static Lehrplan parseLehrplan(String markdown) {
        Map<String, String> frontmatter = parseFrontmatter(markdown);
        List<Lektion> lektionen = new ArrayList<>();
        String nummer = null;
        String titel = null;
        String uebung = null;
        for (String zeile : markdown.split("\\R")) {
            Matcher lektion = LEKTION.matcher(zeile.strip());
            if (lektion.matches()) {
                if (nummer != null) {
                    lektionen.add(new Lektion(nummer, titel, uebung));
                }
                nummer = lektion.group(1);
                titel = lektion.group(2).trim();
                uebung = null;
                continue;
            }
            Matcher uebungsZeile = UEBUNG.matcher(zeile.strip());
            if (uebungsZeile.matches() && nummer != null) {
                uebung = uebungsZeile.group(1).trim();
            }
        }
        if (nummer != null) {
            lektionen.add(new Lektion(nummer, titel, uebung));
        }
        return new Lehrplan(
                frontmatter.get("thema"),
                frontmatter.get("titel"),
                parseListe(frontmatter.get("voraussetzungen")),
                lektionen);
    }
}
```

Hinweis: Die Textblöcke im Test sind eingerückt; deshalb `zeile.strip()` vor dem Regex-Match.

- [ ] **Step 4: Test laufen lassen – muss bestehen**

Run: `javac -encoding UTF-8 -d build/tools tools/Fortschritt.java tools/FortschrittTest.java && java -cp build/tools FortschrittTest`
Expected: `Alle Tests bestanden`

- [ ] **Step 5: Commit**

```bash
git add tools/Fortschritt.java tools/FortschrittTest.java
git commit -m "feat: Lehrplan-Parser für die Fortschrittsseite"
```

---

### Task 4: `Fortschritt.java` – Fortschrittsdatei parsen

**Files:**
- Modify: `tools/Fortschritt.java`
- Modify: `tools/FortschrittTest.java`

- [ ] **Step 1: Tests ergänzen**

In `FortschrittTest.main` nach `lehrplanOhneUebung();` einfügen:
```java
        standMitZweiThemen();
        standOhneThemen();
```

Neue Methoden in `FortschrittTest` (vor `pruefe`):
```java
    static void standMitZweiThemen() {
        String md = """
            ---
            name: Max Mustermann
            sprache: de
            rolle: azubi
            ide: eclipse
            vorwissen: "Erstes Lehrjahr, kein Git."
            ---

            ## java
            - 01: fertig 2026-09-10
            - 03: begonnen 2026-09-12
            - notizen: Referenz vs. Wert wiederholen.

            ## git
            - 01: fertig 2026-09-11
            """;
        Fortschritt.Stand stand = Fortschritt.parseStand(md);
        pruefe("name", "Max Mustermann", stand.profil().name());
        pruefe("sprache", "de", stand.profil().sprache());
        pruefe("ide", "eclipse", stand.profil().ide());
        pruefe("vorwissen ohne anfuehrungszeichen", "Erstes Lehrjahr, kein Git.", stand.profil().vorwissen());
        pruefe("anzahl themen", 2, stand.themen().size());
        Fortschritt.ThemenStand java = stand.themen().get("java");
        pruefe("java lektionen", 2, java.lektionen().size());
        pruefe("java 01 status", "fertig", java.lektionen().get(0).status());
        pruefe("java 01 datum", "2026-09-10", java.lektionen().get(0).datum());
        pruefe("java 03 status", "begonnen", java.lektionen().get(1).status());
        pruefe("java notizen", "Referenz vs. Wert wiederholen.", java.notizen());
        pruefe("git notizen leer", null, stand.themen().get("git").notizen());
    }

    static void standOhneThemen() {
        String md = """
            ---
            name: Neu
            sprache: fr
            rolle: student
            ide: keine
            vorwissen: "nichts"
            ---
            """;
        Fortschritt.Stand stand = Fortschritt.parseStand(md);
        pruefe("keine themen", 0, stand.themen().size());
        pruefe("sprache fr", "fr", stand.profil().sprache());
    }
```

- [ ] **Step 2: Test laufen lassen – muss scheitern**

Run: `javac -encoding UTF-8 -d build/tools tools/Fortschritt.java tools/FortschrittTest.java && java -cp build/tools FortschrittTest`
Expected: Compilerfehler `cannot find symbol: method parseStand`.

- [ ] **Step 3: Parser implementieren**

In `Fortschritt` nach den beiden vorhandenen Records einfügen:
```java
    record LektionsStand(String nummer, String status, String datum) {}
    record ThemenStand(List<LektionsStand> lektionen, String notizen) {}
    record Profil(String name, String sprache, String rolle, String ide, String vorwissen) {}
    record Stand(Profil profil, Map<String, ThemenStand> themen) {}
```

Nach den beiden vorhandenen Patterns einfügen:
```java
    private static final Pattern THEMA = Pattern.compile("^## (\\S+)$");
    private static final Pattern LEKTIONS_STAND =
            Pattern.compile("^- (\\d{2}): (fertig|begonnen) (\\d{4}-\\d{2}-\\d{2})$");
    private static final Pattern NOTIZEN = Pattern.compile("^- notizen: (.*)$");
```

Nach `parseLehrplan` einfügen:
```java
    static Stand parseStand(String markdown) {
        Map<String, String> frontmatter = parseFrontmatter(markdown);
        Profil profil = new Profil(
                frontmatter.get("name"),
                frontmatter.get("sprache"),
                frontmatter.get("rolle"),
                frontmatter.get("ide"),
                frontmatter.get("vorwissen"));
        Map<String, ThemenStand> themen = new LinkedHashMap<>();
        String thema = null;
        List<LektionsStand> lektionen = null;
        String notizen = null;
        for (String rohZeile : markdown.split("\\R")) {
            String zeile = rohZeile.strip();
            Matcher themaZeile = THEMA.matcher(zeile);
            if (themaZeile.matches()) {
                if (thema != null) {
                    themen.put(thema, new ThemenStand(lektionen, notizen));
                }
                thema = themaZeile.group(1);
                lektionen = new ArrayList<>();
                notizen = null;
                continue;
            }
            if (thema == null) {
                continue;
            }
            Matcher standZeile = LEKTIONS_STAND.matcher(zeile);
            if (standZeile.matches()) {
                lektionen.add(new LektionsStand(standZeile.group(1), standZeile.group(2), standZeile.group(3)));
                continue;
            }
            Matcher notizZeile = NOTIZEN.matcher(zeile);
            if (notizZeile.matches()) {
                notizen = notizZeile.group(1).trim();
            }
        }
        if (thema != null) {
            themen.put(thema, new ThemenStand(lektionen, notizen));
        }
        return new Stand(profil, themen);
    }
```

- [ ] **Step 4: Test laufen lassen – muss bestehen**

Run: `javac -encoding UTF-8 -d build/tools tools/Fortschritt.java tools/FortschrittTest.java && java -cp build/tools FortschrittTest`
Expected: `Alle Tests bestanden`

- [ ] **Step 5: Commit**

```bash
git add tools/Fortschritt.java tools/FortschrittTest.java
git commit -m "feat: Parser für die Fortschrittsdatei"
```

---

### Task 5: `Fortschritt.java` – JSON, HTML-Vorlage, Hauptprogramm

**Files:**
- Modify: `tools/Fortschritt.java`
- Modify: `tools/FortschrittTest.java`
- Create: `tools/fortschritt.template.html`

- [ ] **Step 1: JSON-Tests ergänzen**

In `FortschrittTest.main` nach `standOhneThemen();`:
```java
        jsonEscaping();
        jsonGesamt();
```

Neue Methoden vor `pruefe`:
```java
    static void jsonEscaping() {
        pruefe("null", "null", Fortschritt.json((String) null));
        pruefe("anfuehrungszeichen", "\"a\\\"b\"", Fortschritt.json("a\"b"));
        pruefe("backslash", "\"a\\\\b\"", Fortschritt.json("a\\b"));
        pruefe("schraegstrich gegen </script>", "\"<\\/script>\"", Fortschritt.json("</script>"));
        pruefe("zeilenumbruch", "\"a\\nb\"", Fortschritt.json("a\nb"));
        pruefe("liste", "[\"git\",\"java\"]", Fortschritt.json(List.of("git", "java")));
    }

    static void jsonGesamt() {
        Fortschritt.Lehrplan plan = new Fortschritt.Lehrplan("java", "Java", List.of("git"),
                List.of(new Fortschritt.Lektion("01", "Erste Klasse", "uebungen/01-erste-klasse"),
                        new Fortschritt.Lektion("02", "Variablen", null)));
        Fortschritt.Stand stand = new Fortschritt.Stand(
                new Fortschritt.Profil("Max", "de", "azubi", "eclipse", "nichts"),
                java.util.Map.of("java", new Fortschritt.ThemenStand(
                        List.of(new Fortschritt.LektionsStand("01", "fertig", "2026-09-10")), "Notiz")));
        String json = Fortschritt.toJson(List.of(plan), stand);
        String erwartet = "{\"profil\":{\"name\":\"Max\",\"sprache\":\"de\",\"rolle\":\"azubi\",\"ide\":\"eclipse\",\"vorwissen\":\"nichts\"},"
                + "\"lehrplaene\":[{\"thema\":\"java\",\"titel\":\"Java\",\"voraussetzungen\":[\"git\"],\"lektionen\":["
                + "{\"nummer\":\"01\",\"titel\":\"Erste Klasse\",\"uebung\":\"uebungen\\/01-erste-klasse\"},"
                + "{\"nummer\":\"02\",\"titel\":\"Variablen\",\"uebung\":null}]}],"
                + "\"stand\":{\"java\":{\"lektionen\":[{\"nummer\":\"01\",\"status\":\"fertig\",\"datum\":\"2026-09-10\"}],\"notizen\":\"Notiz\"}}}";
        pruefe("json gesamt", erwartet, json);
    }
```

- [ ] **Step 2: Test laufen lassen – muss scheitern**

Run: `javac -encoding UTF-8 -d build/tools tools/Fortschritt.java tools/FortschrittTest.java && java -cp build/tools FortschrittTest`
Expected: Compilerfehler `cannot find symbol: method json` / `toJson`.

- [ ] **Step 3: JSON und Hauptprogramm implementieren**

In `Fortschritt` nach `parseStand` einfügen:
```java
    static String json(String wert) {
        if (wert == null) {
            return "null";
        }
        StringBuilder sb = new StringBuilder("\"");
        for (char c : wert.toCharArray()) {
            switch (c) {
                case '"' -> sb.append("\\\"");
                case '\\' -> sb.append("\\\\");
                case '\n' -> sb.append("\\n");
                case '\r' -> sb.append("\\r");
                case '\t' -> sb.append("\\t");
                case '/' -> sb.append("\\/"); // verhindert </script> im eingebetteten JSON
                default -> {
                    if (c < 0x20) {
                        sb.append(String.format("\\u%04x", (int) c));
                    } else {
                        sb.append(c);
                    }
                }
            }
        }
        return sb.append('"').toString();
    }

    static String json(List<String> werte) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < werte.size(); i++) {
            if (i > 0) {
                sb.append(',');
            }
            sb.append(json(werte.get(i)));
        }
        return sb.append(']').toString();
    }

    static String toJson(List<Lehrplan> lehrplaene, Stand stand) {
        StringBuilder sb = new StringBuilder("{\"profil\":{");
        Profil p = stand.profil();
        sb.append("\"name\":").append(json(p.name()))
          .append(",\"sprache\":").append(json(p.sprache()))
          .append(",\"rolle\":").append(json(p.rolle()))
          .append(",\"ide\":").append(json(p.ide()))
          .append(",\"vorwissen\":").append(json(p.vorwissen()))
          .append("},\"lehrplaene\":[");
        for (int i = 0; i < lehrplaene.size(); i++) {
            Lehrplan plan = lehrplaene.get(i);
            if (i > 0) {
                sb.append(',');
            }
            sb.append("{\"thema\":").append(json(plan.thema()))
              .append(",\"titel\":").append(json(plan.titel()))
              .append(",\"voraussetzungen\":").append(json(plan.voraussetzungen()))
              .append(",\"lektionen\":[");
            for (int j = 0; j < plan.lektionen().size(); j++) {
                Lektion l = plan.lektionen().get(j);
                if (j > 0) {
                    sb.append(',');
                }
                sb.append("{\"nummer\":").append(json(l.nummer()))
                  .append(",\"titel\":").append(json(l.titel()))
                  .append(",\"uebung\":").append(json(l.uebung()))
                  .append('}');
            }
            sb.append("]}");
        }
        sb.append("],\"stand\":{");
        boolean erstes = true;
        for (Map.Entry<String, ThemenStand> eintrag : stand.themen().entrySet()) {
            if (!erstes) {
                sb.append(',');
            }
            erstes = false;
            sb.append(json(eintrag.getKey())).append(":{\"lektionen\":[");
            List<LektionsStand> lektionen = eintrag.getValue().lektionen();
            for (int j = 0; j < lektionen.size(); j++) {
                LektionsStand s = lektionen.get(j);
                if (j > 0) {
                    sb.append(',');
                }
                sb.append("{\"nummer\":").append(json(s.nummer()))
                  .append(",\"status\":").append(json(s.status()))
                  .append(",\"datum\":").append(json(s.datum()))
                  .append('}');
            }
            sb.append("],\"notizen\":").append(json(eintrag.getValue().notizen())).append('}');
        }
        return sb.append("}}").toString();
    }

    private static final String PLATZHALTER = "/*DATEN*/{}";

    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            abbruch("Aufruf: java tools/Fortschritt.java <name>   (im Repo-Root)");
        }
        Path themenOrdner = Path.of("themen");
        Path fortschrittDatei = Path.of("fortschritt", args[0] + ".md");
        Path vorlage = Path.of("tools", "fortschritt.template.html");
        Path ziel = Path.of("arbeit", "fortschritt.html");
        if (!Files.isDirectory(themenOrdner)) {
            abbruch("Ordner 'themen' nicht gefunden – bitte im Repo-Root starten.");
        }
        if (!Files.exists(fortschrittDatei)) {
            abbruch("Fortschrittsdatei fehlt: " + fortschrittDatei);
        }
        List<Lehrplan> lehrplaene = new ArrayList<>();
        try (Stream<Path> ordner = Files.list(themenOrdner)) {
            for (Path thema : ordner.sorted().toList()) {
                Path lehrplan = thema.resolve("lehrplan.md");
                if (thema.getFileName().toString().startsWith("_") || !Files.exists(lehrplan)) {
                    continue;
                }
                lehrplaene.add(parseLehrplan(Files.readString(lehrplan, StandardCharsets.UTF_8)));
            }
        }
        Stand stand = parseStand(Files.readString(fortschrittDatei, StandardCharsets.UTF_8));
        String html = Files.readString(vorlage, StandardCharsets.UTF_8);
        if (!html.contains(PLATZHALTER)) {
            abbruch("Platzhalter " + PLATZHALTER + " fehlt in " + vorlage);
        }
        Files.createDirectories(ziel.getParent());
        Files.writeString(ziel, html.replace(PLATZHALTER, toJson(lehrplaene, stand)), StandardCharsets.UTF_8);
        System.out.println("Geschrieben: " + ziel.toAbsolutePath());
    }

    private static void abbruch(String meldung) {
        System.err.println(meldung);
        System.exit(2);
    }
```

- [ ] **Step 4: Test laufen lassen – muss bestehen**

Run: `javac -encoding UTF-8 -d build/tools tools/Fortschritt.java tools/FortschrittTest.java && java -cp build/tools FortschrittTest`
Expected: `Alle Tests bestanden`

- [ ] **Step 5: HTML-Vorlage anlegen**

`tools/fortschritt.template.html`:
```html
<!DOCTYPE html>
<html lang="de">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Lernfortschritt</title>
<style>
  :root { --bg:#f6f7f9; --karte:#fff; --text:#1f2933; --grau:#6b7280; --rand:#e5e7eb;
          --fertig:#16a34a; --begonnen:#f59e0b; --offen:#d1d5db; }
  body { margin:0; padding:2rem; font-family:system-ui,sans-serif; background:var(--bg); color:var(--text); }
  header { display:flex; flex-wrap:wrap; gap:1rem 2rem; align-items:baseline; margin-bottom:1.5rem; }
  h1 { margin:0; font-size:1.6rem; }
  .profil { color:var(--grau); font-size:.95rem; }
  .gesamt, .thema { background:var(--karte); border:1px solid var(--rand); border-radius:.75rem; padding:1rem 1.25rem; }
  .gesamt { margin-bottom:1.5rem; }
  .balken { height:.6rem; background:var(--offen); border-radius:999px; overflow:hidden; }
  .balken > div { height:100%; background:var(--fertig); }
  .themen { display:grid; grid-template-columns:repeat(auto-fill,minmax(320px,1fr)); gap:1rem; }
  .thema h2 { margin:0 0 .5rem; font-size:1.15rem; display:flex; justify-content:space-between; }
  .thema .zahl { color:var(--grau); font-weight:normal; font-size:.95rem; }
  .thema.gesperrt { opacity:.7; }
  ul { list-style:none; padding:0; margin:.75rem 0 0; }
  li { display:flex; gap:.5rem; padding:.2rem 0; font-size:.95rem; }
  li .datum { margin-left:auto; color:var(--grau); font-size:.85rem; }
  .status { width:1.2rem; text-align:center; }
  .fertig .status { color:var(--fertig); }
  .begonnen .status { color:var(--begonnen); }
  .offen { color:var(--grau); }
  .notizen { margin-top:.75rem; padding:.5rem .75rem; background:#fffbeb; border-left:3px solid var(--begonnen); font-size:.9rem; }
  .hinweis { color:var(--grau); font-size:.85rem; margin-top:.5rem; }
</style>
</head>
<body>
<header><h1>Lernfortschritt</h1><div class="profil" id="profil"></div></header>
<section class="gesamt" id="gesamt"></section>
<section class="themen" id="themen"></section>
<script>
const DATEN = /*DATEN*/{};
const SYMBOL = { fertig: "\u2714", begonnen: "\u25B6", offen: "\u25CB" };

function standVon(thema) {
  return DATEN.stand[thema] || { lektionen: [], notizen: null };
}

function lektionenMitStatus(plan) {
  const stand = standVon(plan.thema);
  return plan.lektionen.map(l => {
    const s = stand.lektionen.find(x => x.nummer === l.nummer);
    return { ...l, status: s ? s.status : "offen", datum: s ? s.datum : null };
  });
}

function istFertig(thema) {
  const plan = DATEN.lehrplaene.find(p => p.thema === thema);
  if (!plan) return true;
  return lektionenMitStatus(plan).every(l => l.status === "fertig");
}

function fehlendeVoraussetzungen(plan) {
  return plan.voraussetzungen.filter(v => !istFertig(v));
}

function letztesDatum(lektionen) {
  return lektionen.map(l => l.datum).filter(Boolean).sort().pop() || null;
}

function escapeHtml(s) {
  return String(s ?? "").replace(/[&<>"]/g, c => ({ "&": "&amp;", "<": "&lt;", ">": "&gt;", '"': "&quot;" }[c]));
}

function karte(plan) {
  const lektionen = lektionenMitStatus(plan);
  const fertig = lektionen.filter(l => l.status === "fertig").length;
  const fehlend = fehlendeVoraussetzungen(plan);
  const prozent = lektionen.length ? Math.round(100 * fertig / lektionen.length) : 0;
  const stand = standVon(plan.thema);
  const datum = letztesDatum(lektionen);
  const zeilen = lektionen.map(l =>
    `<li class="${l.status}"><span class="status">${SYMBOL[l.status]}</span>` +
    `<span>${l.nummer} ${escapeHtml(l.titel)}</span>` +
    (l.datum ? `<span class="datum">${l.datum}</span>` : "") + `</li>`).join("");
  return `<article class="thema ${fehlend.length ? "gesperrt" : ""}">
    <h2><span>${fehlend.length ? "\uD83D\uDD12 " : ""}${escapeHtml(plan.titel)}</span><span class="zahl">${fertig} / ${lektionen.length}</span></h2>
    <div class="balken"><div style="width:${prozent}%"></div></div>
    ${fehlend.length ? `<div class="hinweis">Voraussetzung noch offen: ${fehlend.map(escapeHtml).join(", ")}</div>` : ""}
    ${datum ? `<div class="hinweis">Letzte Stunde: ${datum}</div>` : ""}
    <ul>${zeilen}</ul>
    ${stand.notizen ? `<div class="notizen">N\u00E4chstes Mal: ${escapeHtml(stand.notizen)}</div>` : ""}
  </article>`;
}

function render() {
  const p = DATEN.profil;
  document.getElementById("profil").textContent = `${p.name} \u00B7 ${p.rolle} \u00B7 Sprache ${p.sprache} \u00B7 IDE ${p.ide}`;
  let gesamtFertig = 0, gesamtAlle = 0;
  for (const plan of DATEN.lehrplaene) {
    const lektionen = lektionenMitStatus(plan);
    gesamtFertig += lektionen.filter(l => l.status === "fertig").length;
    gesamtAlle += lektionen.length;
  }
  const gesamtProzent = gesamtAlle ? Math.round(100 * gesamtFertig / gesamtAlle) : 0;
  document.getElementById("gesamt").innerHTML =
    `<strong>Gesamt: ${gesamtFertig} von ${gesamtAlle} Lektionen (${gesamtProzent} %)</strong>` +
    `<div class="balken" style="margin-top:.5rem"><div style="width:${gesamtProzent}%"></div></div>`;
  document.getElementById("themen").innerHTML = DATEN.lehrplaene.map(karte).join("");
}

render();
</script>
</body>
</html>
```

- [ ] **Step 6: Ende-zu-Ende mit der Beispieldatei**

Zu diesem Zeitpunkt gibt es noch keine Lehrpläne; die Seite muss trotzdem entstehen (leere Themenliste, Profil sichtbar).

Run: `java tools/Fortschritt.java _beispiel && ls -la arbeit/fortschritt.html && grep -c '"name":"Max Mustermann"' arbeit/fortschritt.html`
Expected: `Geschrieben: …\arbeit\fortschritt.html`, Datei vorhanden, `1`.

- [ ] **Step 7: Fehlerfälle prüfen**

Run: `java tools/Fortschritt.java gibt-es-nicht; echo EXIT=$?`
Expected: `Fortschrittsdatei fehlt: fortschritt\gibt-es-nicht.md`, `EXIT=2`.

Run: `cd tools && java Fortschritt.java _beispiel; echo EXIT=$?; cd ..`
Expected: `Ordner 'themen' nicht gefunden – bitte im Repo-Root starten.`, `EXIT=2`.

- [ ] **Step 8: Seite im Browser prüfen**

In Claude Code die Browser-Ansicht öffnen: `mcp__Claude_Browser__navigate` mit `file:///C:/Entwicklung/KI/Claude/Teacher/arbeit/fortschritt.html` (Pfad anpassen). Erwartung: Überschrift „Lernfortschritt", Profilzeile „Max Mustermann · azubi · Sprache de · IDE eclipse", Gesamt „0 von 0 Lektionen", keine Konsolenfehler (`read_console_messages`).

- [ ] **Step 9: Commit**

```bash
git add tools/
git commit -m "feat: Fortschrittsseite – JSON-Ausgabe, HTML-Vorlage und Hauptprogramm"
```

---

### Task 6: Skill `/lernen`

**Files:**
- Create: `.claude/skills/lernen/SKILL.md`

- [ ] **Step 1: Skill schreiben**

````markdown
---
name: lernen
description: Startet oder setzt eine Unterrichtsstunde fort. Aufruf `/lernen` (Übersicht) oder `/lernen <thema>` (z. B. `/lernen git`). Verwenden, wenn jemand lernen, üben, weitermachen oder seinen Stand sehen will.
---

# /lernen [thema]

Du bist der Lehrer. Regeln und Formate stehen in `AGENTS.md` – sie gelten hier vollständig.
Führe die Schritte in dieser Reihenfolge aus. Sprich ab Schritt 2 in der Unterrichtssprache.

## 1. Lernenden bestimmen

1. `git config user.name` lesen. Daraus den Dateinamen bilden: Kleinbuchstaben, Leerzeichen
   → `-`, Umlaute → ae/oe/ue (z. B. `max-mustermann`). Fehlt der Name, nach dem Namen fragen.
2. Existiert `fortschritt/<name>.md`? Wenn nein, **eine Frage nach der anderen** stellen:
   - Wie heißt du? (Vorschlag aus Git anbieten)
   - In welcher Sprache sollen wir arbeiten? (Deutsch / English / Français / andere)
   - Bist du Azubi, Student oder neuer Kollege?
   - Welche IDE benutzt du? (Eclipse / IntelliJ / VS Code / noch keine)
   - Was kannst du schon? Zwei Sätze reichen.
   Dann die Datei aus `fortschritt/_beispiel.md` anlegen: Frontmatter mit den Antworten,
   **keine** Themenabschnitte übernehmen.
3. Existiert sie: Profil lesen. Kurz bestätigen: „Weiterhin mit Eclipse?" – bei Wechsel
   `ide:` ändern und in zwei Sätzen sagen, was in der neuen IDE anders heißt
   (siehe IDE-Hinweise in `AGENTS.md`).

## 2. Übersicht (ohne Thema-Argument) – dann Ende

1. Alle `themen/*/lehrplan.md` lesen (nicht `_schablone`), Fortschrittsdatei lesen.
2. Kurzform im Chat, ein Thema pro Zeile, 16 Zeichen Balken (`█` fertig, `░` offen):
   ```
   Java        ████████░░░░░░░░  5/12
   Git         ████████████████  6/6   ✔
   JUnit       ░░░░░░░░░░░░░░░░  0/8   🔒 setzt java voraus
   ```
   🔒, wenn ein Thema aus `voraussetzungen` nicht komplett fertig ist.
3. `java tools/Fortschritt.java <name>` ausführen und `arbeit/fortschritt.html` in der
   Browser-Ansicht öffnen (`file:///…/arbeit/fortschritt.html`). Ohne Browser-Ansicht:
   den Pfad nennen.
4. Fragen, mit welchem Thema es weitergehen soll. Empfehlung: das zuletzt begonnene, sonst
   das erste ohne offene Voraussetzungen.

## 3. Umgebung prüfen (mit Thema, vor jeder Lektion)

Die Tabelle „Umgebungsprüfung" in `AGENTS.md` abarbeiten – nur die Zeilen, die das Thema
braucht. Jeden Befehl per Bash ausführen, Ergebnis in einem Satz melden:

- Alles da → weiter.
- Etwas fehlt → Installationsbefehl aus der Tabelle für das Betriebssystem des Lernenden
  zeigen, **ausführen lassen** (nicht selbst installieren), neues Terminal öffnen lassen,
  erneut prüfen. Die Installation ist Teil der Stunde; erkläre dabei, was das Werkzeug tut.
- Gradle: beim ersten `./gradlew --version` in einer Übung lädt der Wrapper Gradle. Vorher
  ankündigen, dass das ein paar Minuten dauern kann.

## 4. Lektion bestimmen

1. `themen/<thema>/lehrplan.md` lesen. Unbekanntes Thema → Liste der vorhandenen zeigen.
2. Abschnitt `## <thema>` in der Fortschrittsdatei lesen. Nächste Lektion = die mit
   Status `begonnen`, sonst die erste, die nicht `fertig` ist.
3. Voraussetzungen prüfen (`voraussetzungen:` im Lehrplan). Sind sie nicht fertig: sagen,
   welche, und empfehlen, dort zuerst weiterzumachen – der Lernende entscheidet.
4. Fortschrittsanzeige für das Thema im Chat:
   ```
   Java  ████████░░░░░░░░  5 / 12 Lektionen
     ✔ 01 Erste Klasse        ✔ 02 Variablen & Typen
     ▶ 06 Vererbung (begonnen 2026-09-12)
     ○ 07 Interfaces …
   ```
5. Gibt es eine `notizen:`-Zeile, sie lesen und beim Einstieg berücksichtigen
   („Letztes Mal war Referenz vs. Wert noch wackelig – wir fangen mit einem Beispiel dazu an.").
6. Beim erstmaligen Beginn einer Lektion: Zeile `- NN: begonnen <heute>` eintragen.

## 5. Kernschleife der Lektion

Wiederhole, bis die Ziele der Lektion erreicht sind:

1. **Erklären** – ein Konzept, kurz, mit Beispiel. Leitfaden ist der Lehrplantext; nicht
   vorlesen, sondern erklären.
2. **Prüfen** – eine Verständnisfrage stellen. Antwort abwarten. Bei Fehlern Gegenfrage
   oder kleineres Beispiel, nicht die Lösung.
3. **Üben** (wenn `Übung:` in der Lektion steht):
   - Übung kopieren: `themen/<thema>/<uebung>/` → `arbeit/<thema>/<NN-name>/`, **ohne**
     `loesung/`. Existiert das Ziel schon, nicht überschreiben – fragen, ob weiterarbeiten
     oder neu anfangen.
   - `AUFGABE.md` lesen und in der Unterrichtssprache erklären; Abnahmekriterien nennen.
   - Sagen, wie die Übung in der eingestellten IDE geöffnet wird (IDE-Hinweise in `AGENTS.md`).
   - Warten. Der Lernende schreibt. Erst auf „fertig" oder eine Frage reagieren.
   - Prüfen auf das echte Ergebnis: Java/Gradle/JUnit → `./gradlew test` im Arbeitsordner;
     HTML/CSS/JS → `index.html` in der Browser-Ansicht öffnen, `read_page` und Screenshot;
     Git → `git log --oneline --graph --all` und `git status` im Übungs-Repo.
   - Rückmeldung gestaffelt: 1. Versuch → Hinweis auf die Stelle; 2. Versuch → konkreter
     Hinweis (welche Zeile, welches Konzept); 3. Versuch oder auf Wunsch → Lösung aus
     `loesung/` zeigen und erklären. Nie den Code des Lernenden selbst ändern.
4. **Abschluss** – wenn Prüffrage beantwortet und Übung abgenommen: zwei Sätze
   Zusammenfassung. Fortschrittsdatei: `- NN: fertig <heute>`. Anzeige wie in Schritt 4.4.
   Fragen: weiter mit der nächsten Lektion oder Schluss?

## 6. Ende der Stunde

Wenn der Lernende aufhört oder das Thema fertig ist:

1. Zusammenfassung: was sitzt, was noch wackelt.
2. `notizen:`-Zeile des Themas schreiben oder ersetzen – konkret, für das nächste Mal.
3. `java tools/Fortschritt.java <name>` ausführen, Seite in der Browser-Ansicht öffnen.
4. Erwähnen, dass `arbeit/fortschritt.html` als einzelne Datei an den Ausbilder gehen kann.

## Fehlerfälle

- `java` fehlt und das Thema ist html/css/javascript: Fortschrittsseite überspringen,
  Textanzeige genügt; JDK-Installation nicht erzwingen.
- Fortschrittsdatei ist von Hand kaputt editiert (Parser meldet Fehler): Datei zeigen,
  gemeinsam reparieren, nicht neu anlegen.
- Lernender will eine Lektion überspringen: erlaubt; Status nicht als `fertig` eintragen,
  sondern die nächste als `begonnen`.
````

- [ ] **Step 2: Skill-Erkennung prüfen**

Run: `ls .claude/skills/lernen/SKILL.md && head -4 .claude/skills/lernen/SKILL.md`
Expected: Datei vorhanden, Frontmatter mit `name: lernen`.

- [ ] **Step 3: Commit**

```bash
git add .claude/skills/lernen/SKILL.md
git commit -m "feat: Skill /lernen – Ablauf einer Unterrichtsstunde"
```

---

### Task 7: Skill `/thema-anlegen`

**Files:**
- Create: `.claude/skills/thema-anlegen/SKILL.md`

- [ ] **Step 1: Skill schreiben**

````markdown
---
name: thema-anlegen
description: Legt ein neues Unterrichtsthema an (Ordner, Lehrplan-Entwurf, nach Freigabe Übungen). Aufruf `/thema-anlegen <name>`, z. B. `/thema-anlegen spring`. Für Ausbilder.
---

# /thema-anlegen <name>

Du hilfst einem Ausbilder, ein Thema anzulegen. Formate und Konventionen: `AGENTS.md`.
`<name>` ist der Ordnername: Kleinbuchstaben, keine Leerzeichen, keine Umlaute.

## 1. Klären (eine Frage pro Nachricht)

1. Titel des Themas?
2. Zielgruppe: Azubi, Student, Kollege – mehrere möglich?
3. Voraussetzungen: welche vorhandenen Themen (`ls themen`) sollten vorher fertig sein?
4. Was soll am Ende sitzen? Zwei bis drei Sätze.
5. Quellmaterial: Für Allgemeinthemen keines. Für Firmenthemen: Pfad zu Code oder Doku
   (anderes Repo, relativer Pfad oder `FRAMEWORK_HOME`). Das Material lesen, bevor der
   Lehrplan entsteht – Lektionen werden daraus abgeleitet, nicht geraten. Firmencode wird
   **nicht** in dieses Repo kopiert; Übungen binden ihn als Abhängigkeit oder per Pfad ein.

## 2. Ordner und Lehrplan-Entwurf

1. Existiert `themen/<name>/` schon → abbrechen und sagen, dass das Thema existiert.
2. `themen/_schablone/lehrplan.md` nach `themen/<name>/lehrplan.md` kopieren, Frontmatter
   füllen, `mkdir themen/<name>/uebungen`.
3. Lehrplan-Entwurf mit 6–12 Lektionen schreiben, aufsteigend von Grundlagen zu Anwendung.
   Jede Lektion: Titel, `Ziele`, `Prüffrage`; `Übung` nur dort, wo eine Übung geplant ist,
   mit Pfad `uebungen/NN-name`. Unter jeder Lektion ein bis drei Sätze Leitfaden.
4. Format prüfen: `java tools/Fortschritt.java _beispiel` muss durchlaufen und das Thema
   in `arbeit/fortschritt.html` erscheinen.
5. Entwurf im Chat zeigen (Lektionsliste). **Warten auf Freigabe.** Änderungswünsche
   einarbeiten, erneut zeigen.

## 3. Übungen (erst nach Freigabe)

Für jede Lektion mit `Übung:`:

1. Gerüst kopieren: Java/Gradle/JUnit → `themen/_schablone/uebung-java/`;
   HTML/CSS/JS → `themen/_schablone/uebung-web/`; Git → nur `AUFGABE.md`.
   Ziel: `themen/<name>/uebungen/NN-name/`. In `settings.gradle` `rootProject.name`
   auf `NN-name` setzen.
2. `AUFGABE.md` schreiben: Aufgabe, Abnahmekriterien (prüfbar!), Hinweise gestaffelt
   (Hinweis 1 allgemein, Hinweis 2 konkret). Bei Git-Übungen zusätzlich Abschnitt
   „Vorbereitung (führt Claude aus)" mit den Befehlen, die das Übungs-Repo aufbauen.
3. Startcode und Tests schreiben. Tests müssen mit dem Startcode **rot** sein.
4. `loesung/` anlegen: die Referenzlösung plus `LOESUNG.md` mit einem Satz, warum sie so
   aussieht.
5. Prüfen: Referenzlösung über den Startcode kopieren (in einen Temp-Ordner), Tests
   laufen lassen – müssen **grün** sein. Web-Übungen: `loesung/index.html` in der
   Browser-Ansicht öffnen und Abnahmekriterien nachsehen. Temp-Ordner löschen.
6. Eine Übung, deren Lösung nicht grün ist, wird nicht committet.

## 4. Abschluss

1. `java tools/Fortschritt.java _beispiel` erneut ausführen.
2. Commit-Vorschlag: `git add themen/<name>` und
   `git commit -m "feat(themen): <Titel> – Lehrplan und N Übungen"`. Commit nur nach
   Bestätigung des Ausbilders.
````

- [ ] **Step 2: Commit**

```bash
git add .claude/skills/thema-anlegen/SKILL.md
git commit -m "feat: Skill /thema-anlegen – Themen anlegen für Ausbilder"
```

---

### Task 8: Die sieben Lehrpläne

**Files:**
- Create: `themen/git/lehrplan.md`, `themen/java/lehrplan.md`, `themen/gradle/lehrplan.md`, `themen/junit/lehrplan.md`, `themen/html/lehrplan.md`, `themen/css/lehrplan.md`, `themen/javascript/lehrplan.md`

Nach jedem Lehrplan: `java tools/Fortschritt.java _beispiel` muss durchlaufen (Formatprüfung). Am Ende ein Commit pro Thema.

- [ ] **Step 1: `themen/git/lehrplan.md`**

```markdown
---
thema: git
titel: Git
voraussetzungen: []
zielgruppe: [azubi, student, kollege]
---

# Git

Git ist das Gedächtnis eines Projekts: Jede Änderung wird festgehalten, jede Version ist
wiederherstellbar, und mehrere Menschen können am selben Code arbeiten, ohne sich gegenseitig
zu überschreiben. Ohne Git gibt es keine Teamarbeit in der Softwareentwicklung.

Am Ende kann der Lernende ein Repository anlegen, Änderungen nachvollziehbar committen, mit
Branches arbeiten, Konflikte lösen und mit einem Remote (GitHub, GitLab, Firmenserver)
zusammenarbeiten.

## Lektionen

### 01 Warum Versionierung, erstes Repository
- **Ziele:** Verstehen, was ein Commit ist; `git init`, `git add`, `git commit`, `git log`, `git status` anwenden; Name und E-Mail konfigurieren
- **Übung:** uebungen/01-erstes-repo
- **Prüffrage:** Was ist der Unterschied zwischen dem Arbeitsverzeichnis, der Staging Area und dem Repository?

Leitfaden: Mit der Frage beginnen, wie der Lernende bisher Versionen gesichert hat
(`projekt_final_v2_neu`). Die drei Bereiche als Schreibtisch, Paketannahme und Archiv erklären.

### 02 Branches und Merge
- **Ziele:** Einen Branch anlegen, darauf committen, in `main` mergen; `git branch`, `git switch`, `git merge` anwenden; Fast-Forward von Merge-Commit unterscheiden
- **Übung:** uebungen/02-branch-und-merge
- **Prüffrage:** Warum arbeitet man an einem Feature in einem eigenen Branch und nicht direkt in `main`?

### 03 Änderungen ansehen und zurücknehmen
- **Ziele:** `git diff`, `git restore`, `git reset --soft`, `git revert` unterscheiden und sicher einsetzen
- **Prüffrage:** Wann nimmt man `revert` statt `reset`?

Leitfaden: Faustregel – alles, was schon geteilt ist, wird mit `revert` zurückgenommen.

### 04 Remote: clone, push, pull
- **Ziele:** Ein Repository klonen, Änderungen hochladen und holen; `origin`, `git fetch` vs. `git pull`
- **Prüffrage:** Was passiert bei `git pull` genau – aus welchen zwei Befehlen besteht es?

### 05 Konflikte lösen
- **Ziele:** Einen Merge-Konflikt erkennen, die Markierungen lesen, auflösen und den Merge abschließen
- **Prüffrage:** Woran erkennt Git, dass es einen Konflikt gibt, und woran erkennt man ihn in der Datei?

### 06 .gitignore und gute Commits
- **Ziele:** Build-Reste ausschließen; kleine, thematisch geschlossene Commits mit sprechender Nachricht schreiben
- **Prüffrage:** Warum gehört `build/` nicht ins Repository, `build.gradle` aber schon?

### 07 Rebase und Historie
- **Ziele:** `git rebase` verstehen, `git log --graph` lesen, den Unterschied zu Merge erklären; Regel „nie geteilte Historie umschreiben"
- **Prüffrage:** Was ändert Rebase an den Commits, was Merge nicht ändert?

### 08 Alltag im Team
- **Ziele:** Ablauf Branch → Push → Pull Request → Review → Merge; Branch-Namen, Commit-Konventionen (`feat:`, `fix:`)
- **Prüffrage:** Was prüft ein Reviewer, was die Tests nicht prüfen können?
```

- [ ] **Step 2: `themen/java/lehrplan.md`**

```markdown
---
thema: java
titel: Java
voraussetzungen: []
zielgruppe: [azubi, student]
---

# Java

Java ist die Hauptsprache in unseren Projekten. Der Lehrplan führt von der ersten Klasse
bis zu Collections, Exceptions und den modernen Sprachmitteln (Records, Streams), immer mit
dem Ziel, lesbaren und wartbaren Code zu schreiben.

Am Ende kann der Lernende ein kleines Programm aus mehreren Klassen entwerfen, mit
Objekten, Vererbung und Interfaces strukturieren, Collections sinnvoll wählen und Fehler
mit Exceptions sauber behandeln.

## Lektionen

### 01 Erste Klasse
- **Ziele:** Klasse, Methode, `main`; Kompilieren und Starten; Unterschied Klasse/Objekt; ein Programm in der IDE ausführen
- **Übung:** uebungen/01-erste-klasse
- **Prüffrage:** Was unterscheidet eine Klasse von einem Objekt?

Leitfaden: Bauplan und Haus. Die Übung enthält bereits einen Test – so sieht der Lernende
vom ersten Tag an, wie „fertig" definiert ist.

### 02 Variablen, Typen und Operatoren
- **Ziele:** Primitive Typen vs. `String`; `int`/`double`/`boolean`; Deklaration, Zuweisung; arithmetische und logische Operatoren; `final`
- **Prüffrage:** Warum ergibt `7 / 2` in Java `3`?

### 03 Kontrollfluss
- **Ziele:** `if`/`else`, `switch`, `for`, `while`, `break`/`continue`; Schleifen lesbar halten
- **Prüffrage:** Wann ist `while` die richtige Wahl statt `for`?

### 04 Methoden
- **Ziele:** Parameter, Rückgabewert, `static` vs. Instanzmethode; Methoden klein halten; sprechende Namen; Überladen
- **Übung:** uebungen/02-rechner
- **Prüffrage:** Was ist der Unterschied zwischen Parameter und Argument?

### 05 Klassen und Objekte
- **Ziele:** Felder, Konstruktoren, `this`; Kapselung mit `private` und Getter; `toString`
- **Prüffrage:** Warum sind Felder `private`, wenn es doch Getter gibt?

### 06 Referenz und Wert
- **Ziele:** Referenztypen vs. primitive Typen; `==` vs. `equals`; Objekte als Parameter; `null`
- **Prüffrage:** Zwei Variablen zeigen auf dasselbe Objekt – was passiert, wenn man über eine davon ein Feld ändert?

### 07 Vererbung und Polymorphie
- **Ziele:** `extends`, `super`, Überschreiben, `@Override`; wann Vererbung passt und wann nicht
- **Prüffrage:** Was ist Polymorphie – mit einem Beispiel?

### 08 Interfaces und Abstraktion
- **Ziele:** `interface`, `implements`; Programmieren gegen Schnittstellen; funktionale Interfaces und Lambdas
- **Prüffrage:** Warum nimmt eine Methode lieber ein `List<String>` entgegen als ein `ArrayList<String>`?

### 09 Collections und Generics
- **Ziele:** `List`, `Set`, `Map` und ihre Implementierungen; Generics lesen und schreiben; die richtige Struktur wählen
- **Prüffrage:** Wann `Set` statt `List`?

### 10 Exceptions
- **Ziele:** `try`/`catch`/`finally`, checked vs. unchecked, eigene Exceptions; Fehler nicht verschlucken; try-with-resources
- **Prüffrage:** Warum ist ein leerer `catch`-Block gefährlich?

### 11 Records, Enums und Streams
- **Ziele:** `record` für Datenklassen, `enum` statt Magic Strings, Streams für Filtern/Abbilden/Sammeln
- **Prüffrage:** Was garantiert ein Record, das eine normale Klasse nicht garantiert?

### 12 Clean Code im Kleinen
- **Ziele:** Sprechende Namen, kleine Methoden, keine Magic Numbers, DRY; Code lesen und verbessern
- **Prüffrage:** Woran erkennt man, dass eine Methode zu viel tut?
```

- [ ] **Step 3: `themen/gradle/lehrplan.md`**

```markdown
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

### 02 Aufbau von build.gradle
- **Ziele:** `plugins`, `repositories`, `dependencies`; `implementation` vs. `testImplementation`; Versionen und Maven-Koordinaten lesen
- **Prüffrage:** Was bedeutet `org.junit.jupiter:junit-jupiter:5.10.2` Teil für Teil?

### 03 Tasks und Lebenszyklus
- **Ziele:** Tasks anzeigen (`./gradlew tasks`), Abhängigkeiten zwischen Tasks, `compileJava` → `test` → `build`; einen eigenen Task schreiben
- **Übung:** uebungen/02-eigener-task
- **Prüffrage:** Warum läuft `compileJava`, wenn man nur `test` aufruft?

### 04 Projektstruktur, Toolchain, Encoding
- **Ziele:** `src/main/java`, `src/test/java`, `src/main/resources`; Java-Toolchain festlegen; UTF-8 erzwingen; `settings.gradle`
- **Prüffrage:** Was passiert, wenn auf dem Rechner ein anderes JDK installiert ist als in der Toolchain steht?

### 05 Tests und Berichte
- **Ziele:** `useJUnitPlatform()`, Testbericht unter `build/reports/tests`, JaCoCo-Coverage einbinden und lesen
- **Prüffrage:** Wo findet man nach `./gradlew test` heraus, welcher Test warum fehlgeschlagen ist?

### 06 IDE-Integration und Multi-Projekt
- **Ziele:** Import in Eclipse (Buildship) und IntelliJ; „Refresh Gradle Project" nach Änderungen; Grundidee Multi-Projekt (`include`)
- **Prüffrage:** Warum sieht Eclipse eine neue Abhängigkeit erst nach dem Refresh?
```

- [ ] **Step 4: `themen/junit/lehrplan.md`**

```markdown
---
thema: junit
titel: JUnit
voraussetzungen: [java, gradle]
zielgruppe: [azubi, student, kollege]
---

# JUnit

Tests sind der Beweis, dass Code tut, was er soll – und die Absicherung, dass er es nach
der nächsten Änderung noch tut. JUnit 5 ist unser Test-Framework; Gradle führt die Tests
bei jedem Build aus.

Am Ende kann der Lernende Tests nach dem AAA-Muster schreiben, Ausnahmen und Randfälle
prüfen, parametrisierte Tests einsetzen und Code so entwerfen, dass er testbar bleibt.

## Lektionen

### 01 Warum testen, erster Test
- **Ziele:** `@Test`, `assertEquals`, Testklasse neben der Klasse; Test in IDE und mit `./gradlew test` ausführen; rot und grün lesen
- **Übung:** uebungen/01-erster-test
- **Prüffrage:** Was ist der Unterschied zwischen einem Test, der fehlschlägt, und einem, der einen Fehler wirft?

### 02 Assertions und Testaufbau
- **Ziele:** Arrange – Act – Assert; `assertTrue`, `assertNull`, `assertAll`; sprechende Testnamen; ein Verhalten pro Test
- **Prüffrage:** Warum ist `testAdd()` ein schlechter Name und `addiertZweiPositiveZahlen()` ein guter?

### 03 Ausnahmen und Randfälle
- **Ziele:** `assertThrows`; leere Eingaben, `null`, Grenzwerte; Tests als Spezifikation
- **Prüffrage:** Welche drei Randfälle prüft man bei einer Methode, die eine Liste entgegennimmt?

### 04 Parametrisierte Tests
- **Ziele:** `@ParameterizedTest` mit `@ValueSource` und `@CsvSource`; Wiederholung in Tests vermeiden
- **Übung:** uebungen/02-parametrisierte-tests
- **Prüffrage:** Wann lohnt ein parametrisierter Test, wann nicht?

### 05 Lebenszyklus und Fixtures
- **Ziele:** `@BeforeEach`, `@AfterEach`, `@BeforeAll`; Testdaten aufbauen; Tests unabhängig voneinander halten
- **Prüffrage:** Warum darf ein Test nicht vom Ergebnis eines anderen abhängen?

### 06 Testbarkeit und Abhängigkeiten
- **Ziele:** Abhängigkeiten über Interfaces hereingeben (Dependency Inversion); einfache Test-Doubles von Hand; warum `new` in der Mitte der Logik Tests schwer macht
- **Prüffrage:** Wie testet man eine Klasse, die die aktuelle Uhrzeit braucht?

### 07 TDD im Kleinen
- **Ziele:** Rot → Grün → Refactor an einem kleinen Beispiel durchlaufen
- **Prüffrage:** Warum schreibt man den Test, bevor der Code existiert?

### 08 Coverage lesen
- **Ziele:** JaCoCo-Bericht öffnen, Zeilen- und Zweigabdeckung verstehen, Lücken finden; Coverage ist Hinweis, kein Ziel
- **Prüffrage:** Warum beweist 100 % Coverage nicht, dass der Code richtig ist?
```

- [ ] **Step 5: `themen/html/lehrplan.md`**

```markdown
---
thema: html
titel: HTML
voraussetzungen: []
zielgruppe: [azubi, student]
---

# HTML

HTML beschreibt die Struktur einer Webseite: Überschriften, Absätze, Listen, Links,
Formulare. Es ist die Grundlage für CSS (Aussehen) und JavaScript (Verhalten).

Am Ende kann der Lernende eine gültige, semantisch sinnvolle Seite mit Text, Bildern,
Links, Tabellen und einem Formular schreiben und sie mit den Entwicklerwerkzeugen des
Browsers untersuchen.

## Lektionen

### 01 Aufbau einer Seite
- **Ziele:** `<!DOCTYPE>`, `html`, `head`, `body`, `title`, `meta charset`; Überschriften und Absätze; Seite im Browser öffnen
- **Übung:** uebungen/01-erste-seite
- **Prüffrage:** Was gehört in `head`, was in `body`?

### 02 Text, Listen, Links, Bilder
- **Ziele:** `ul`/`ol`/`li`, `a href`, `img src alt`, `strong`/`em`; relative und absolute Pfade
- **Prüffrage:** Warum braucht jedes Bild ein `alt`-Attribut?

### 03 Formulare
- **Ziele:** `form`, `label`, `input` (text, email, number, checkbox), `select`, `button`; `name` und `for`/`id`
- **Übung:** uebungen/02-formular
- **Prüffrage:** Was verbindet ein `label` mit seinem Eingabefeld, und warum ist das wichtig?

### 04 Semantische Struktur
- **Ziele:** `header`, `nav`, `main`, `section`, `article`, `footer` statt `div`-Suppe
- **Prüffrage:** Was gewinnt man mit `nav` gegenüber `div class="nav"`?

### 05 Tabellen
- **Ziele:** `table`, `thead`, `tbody`, `tr`, `th`, `td`; wann Tabellen richtig sind (Daten) und wann nicht (Layout)
- **Prüffrage:** Warum sollte man kein Seitenlayout mit Tabellen bauen?

### 06 Barrierefreiheit
- **Ziele:** Überschriftenhierarchie, Alt-Texte, Tastaturbedienung, Kontrast; die Seite ohne Maus benutzen
- **Prüffrage:** Wie liest ein Screenreader eine Seite – in welcher Reihenfolge?

### 07 Entwicklerwerkzeuge
- **Ziele:** Elemente inspizieren, DOM live ändern, Konsole lesen, Netzwerk-Tab verstehen
- **Prüffrage:** Warum sieht man im Inspektor manchmal andere Elemente als im Quelltext?
```

- [ ] **Step 6: `themen/css/lehrplan.md`**

```markdown
---
thema: css
titel: CSS
voraussetzungen: [html]
zielgruppe: [azubi, student]
---

# CSS

CSS bestimmt, wie HTML aussieht: Farben, Schrift, Abstände, Anordnung. Der Lehrplan
führt vom ersten Selektor über das Box-Modell bis zu Flexbox, Grid und responsivem Layout.

Am Ende kann der Lernende eine Seite mit eigener Stylesheet-Datei gestalten, Layouts mit
Flexbox und Grid bauen und sie für Bildschirmgrößen vom Handy bis zum Monitor anpassen.

## Lektionen

### 01 Selektoren, Farben, Schrift
- **Ziele:** Stylesheet einbinden; Element-, Klassen- und ID-Selektoren; `color`, `background`, `font-family`, `font-size`; Kaskade und Spezifität im Ansatz
- **Übung:** uebungen/01-farben-und-schrift
- **Prüffrage:** Zwei Regeln treffen dasselbe Element – welche gewinnt, und warum?

### 02 Box-Modell
- **Ziele:** `margin`, `border`, `padding`, `width`; `box-sizing: border-box`; Abstände im Inspektor sehen
- **Prüffrage:** Wie breit ist ein Element mit `width: 200px; padding: 20px; border: 2px` – mit und ohne `border-box`?

### 03 Flexbox
- **Ziele:** `display: flex`, `justify-content`, `align-items`, `gap`, `flex-wrap`; Elemente nebeneinander und zentrieren
- **Übung:** uebungen/02-flexbox-layout
- **Prüffrage:** Was ist die Hauptachse, und was ändert `flex-direction: column` daran?

### 04 Grid
- **Ziele:** `display: grid`, `grid-template-columns`, `gap`, Bereiche benennen; wann Grid, wann Flexbox
- **Prüffrage:** Wann nimmt man Grid statt Flexbox?

### 05 Responsive Design
- **Ziele:** `@media`, `max-width`, relative Einheiten (`rem`, `%`, `vw`), Viewport-Meta; Mobile first
- **Prüffrage:** Warum `rem` statt `px` für Schriftgrößen?

### 06 Variablen und Wiederverwendung
- **Ziele:** `--variablen` und `var()`, gemeinsame Farbpalette, Klassen statt Wiederholung
- **Prüffrage:** Was passiert, wenn man eine CSS-Variable in `:root` und in einem Element definiert?

### 07 Positionierung
- **Ziele:** `position: relative/absolute/fixed/sticky`, `z-index`; wann Positionierung nötig ist und wann Layout reicht
- **Prüffrage:** Wovon hängt ab, worauf sich `position: absolute` bezieht?
```

- [ ] **Step 7: `themen/javascript/lehrplan.md`**

```markdown
---
thema: javascript
titel: JavaScript
voraussetzungen: [html]
zielgruppe: [azubi, student]
---

# JavaScript

JavaScript macht Seiten interaktiv: auf Klicks reagieren, Inhalte ändern, Daten laden.
Der Lehrplan beginnt in der Browser-Konsole und endet bei asynchronem Code und der
Fehlersuche mit den Entwicklerwerkzeugen.

Am Ende kann der Lernende Funktionen und Arrays sicher verwenden, das DOM lesen und
verändern, auf Ereignisse reagieren, Daten per `fetch` laden und Fehler systematisch finden.

## Lektionen

### 01 Einstieg: Konsole, Variablen, Typen
- **Ziele:** Skript einbinden, `console.log`; `let`/`const`; Zahlen, Strings, Booleans, `undefined`/`null`; Template-Strings
- **Prüffrage:** Warum `const` als Standard und `let` nur bei Bedarf?

### 02 Funktionen und Arrays
- **Ziele:** Funktionen deklarieren, Pfeilfunktionen; Arrays mit `map`, `filter`, `reduce`, `find`; Funktionen als Werte
- **Übung:** uebungen/01-funktionen-und-arrays
- **Prüffrage:** Was ist der Unterschied zwischen `map` und `forEach`?

### 03 DOM: Elemente finden und ändern
- **Ziele:** `querySelector`, `textContent`, `classList`, Elemente erzeugen und einhängen
- **Prüffrage:** Was ist das DOM – und ist es dasselbe wie der HTML-Quelltext?

### 04 Events
- **Ziele:** `addEventListener`, Event-Objekt, `preventDefault`; Zustand in Variablen halten und anzeigen
- **Übung:** uebungen/02-dom-klick
- **Prüffrage:** Warum steht das Skript am Ende von `body` oder nutzt `defer`?

### 05 Objekte und JSON
- **Ziele:** Objektliteral, Eigenschaften, Methoden; `JSON.stringify`/`parse`; Destructuring
- **Prüffrage:** Was ist der Unterschied zwischen einem JavaScript-Objekt und JSON?

### 06 Asynchron: Promises und fetch
- **Ziele:** Warum asynchron; `Promise`, `async`/`await`, `fetch`; Fehler mit `try`/`catch` behandeln
- **Prüffrage:** Was gibt eine `async`-Funktion immer zurück?

### 07 Module und Struktur
- **Ziele:** `import`/`export`, Code in Dateien aufteilen, keine globalen Variablen
- **Prüffrage:** Warum sind globale Variablen in größeren Skripten ein Problem?

### 08 Fehler finden
- **Ziele:** Konsole lesen, Breakpoints setzen, Variablen beobachten, Stacktrace verstehen
- **Prüffrage:** Was sagt `Uncaught TypeError: Cannot read properties of undefined` über den Fehler aus?
```

- [ ] **Step 8: Formatprüfung und Seite ansehen**

Run: `java tools/Fortschritt.java _beispiel && grep -o '"thema":"[a-z]*"' arbeit/fortschritt.html`
Expected: sieben Treffer: `css, git, gradle, html, java, javascript, junit` (alphabetisch, weil `Files.list` sortiert wird).

In der Browser-Ansicht `arbeit/fortschritt.html` öffnen. Erwartung: sieben Karten; Java zeigt 2/12 mit ✔ 01, ✔ 02, ▶ 03 und der Notiz; Git 1/8; JUnit und Gradle mit 🔒 (java nicht fertig); CSS und JavaScript mit 🔒 (html nicht fertig).

- [ ] **Step 9: Commits (einer pro Thema)**

```bash
for t in git java gradle junit html css javascript; do git add themen/$t/lehrplan.md && git commit -m "feat(themen): Lehrplan $t"; done
```

---

## Übungen – gemeinsame Regeln für Task 9–15

- Java-Übungen entstehen aus der Schablone: `cp -r themen/_schablone/uebung-java themen/<thema>/uebungen/<NN-name>` und `rootProject.name = '<NN-name>'` in `settings.gradle`; die `.gitkeep`-Dateien löschen, sobald Quellen drin sind.
- Paket für alle Java-Übungen: `de.makno.lernen` (Konvention: Pakete beginnen mit `de.makno`).
- Prüfung einer Referenzlösung: Übung in den Scratch-Ordner kopieren, `loesung/`-Dateien über die Startdateien kopieren, `./gradlew test --no-daemon -q` → Exit 0. Beim Startcode muss derselbe Befehl **fehlschlagen** (Tests rot oder Kompilierfehler), sonst prüft die Übung nichts.
- `AUFGABE.md` hat immer die Abschnitte: Aufgabe, Abnahmekriterien, Hinweise (gestaffelt), bei Git zusätzlich „Vorbereitung (führt Claude aus)".
- Ein Commit pro Übung: `git commit -m "feat(themen): <thema> Übung <NN-name>"`.

### Task 9: Git-Übungen

**Files:**
- Create: `themen/git/uebungen/01-erstes-repo/AUFGABE.md`, `…/loesung/LOESUNG.md`
- Create: `themen/git/uebungen/02-branch-und-merge/AUFGABE.md`, `…/loesung/LOESUNG.md`

- [ ] **Step 1: `01-erstes-repo/AUFGABE.md`**

```markdown
# Übung 01: Dein erstes Repository

## Vorbereitung (führt Claude aus)

1. Prüfen: `git config --global user.name` und `git config --global user.email` sind gesetzt.
   Wenn nicht, den Lernenden beide setzen lassen (eigener Name, dienstliche E-Mail).
2. `mkdir -p arbeit/git/01-erstes-repo/spielwiese` – der Ordner ist noch **kein** Repository.

## Aufgabe

Arbeite im Ordner `arbeit/git/01-erstes-repo/spielwiese`.

1. Mache den Ordner zu einem Git-Repository.
2. Lege eine Datei `notizen.txt` mit einer Zeile Text an und committe sie mit der Nachricht
   `Notizen angelegt`.
3. Hänge eine zweite Zeile an `notizen.txt` an und committe die Änderung mit einer eigenen,
   sprechenden Nachricht.
4. Lege eine Datei `README.md` mit einer Überschrift an und committe sie.

## Abnahmekriterien

- `git log --oneline` zeigt genau drei Commits.
- `git status` meldet `nothing to commit, working tree clean`.
- Jede Commit-Nachricht sagt, *was* geändert wurde (nicht „update" oder „test").

## Hinweise

1. Der Ablauf ist immer derselbe: ändern → `git add <datei>` → `git commit -m "…"`.
   `git status` sagt dir jederzeit, in welchem Bereich eine Datei gerade ist.
2. Wenn `git commit` einen Editor öffnet und du nicht weiterweißt: Der Editor wollte eine
   Nachricht. Mit `-m "Nachricht"` umgehst du ihn.
```

- [ ] **Step 2: `01-erstes-repo/loesung/LOESUNG.md`**

```markdown
# Lösung 01

```bash
cd arbeit/git/01-erstes-repo/spielwiese
git init
echo "Erste Notiz" > notizen.txt
git add notizen.txt
git commit -m "Notizen angelegt"
echo "Zweite Notiz" >> notizen.txt
git add notizen.txt
git commit -m "Zweite Notiz ergänzt"
echo "# Spielwiese" > README.md
git add README.md
git commit -m "README mit Überschrift angelegt"
git log --oneline
```

Warum so: Jeder Commit enthält genau eine abgeschlossene Änderung mit einer Nachricht, die
das *Was* benennt. So bleibt die Historie lesbar, wenn man sie in einem Jahr wieder braucht.
```

- [ ] **Step 3: `02-branch-und-merge/AUFGABE.md`**

```markdown
# Übung 02: Branch anlegen und mergen

## Vorbereitung (führt Claude aus)

```bash
mkdir -p arbeit/git/02-branch-und-merge/rezept && cd arbeit/git/02-branch-und-merge/rezept
git init -q
printf "Pfannkuchen\n\nZutaten:\n- Mehl\n- Milch\n" > rezept.txt
git add rezept.txt && git commit -q -m "Rezept angelegt"
printf -- "- Eier\n" >> rezept.txt
git add rezept.txt && git commit -q -m "Eier ergänzt"
```

Danach `git log --oneline` zeigen: zwei Commits auf `main` (bei älterem Git `master` –
dann `git branch -m main`).

## Aufgabe

Arbeite in `arbeit/git/02-branch-und-merge/rezept`.

1. Lege einen Branch `zutaten-ergaenzen` an und wechsle hinein.
2. Ergänze in `rezept.txt` die Zeile `- Prise Salz` und committe.
3. Wechsle zurück nach `main` und merge den Branch hinein.
4. Lösche den Branch `zutaten-ergaenzen`.

## Abnahmekriterien

- `git log --oneline --graph` zeigt drei Commits, alle auf `main` erreichbar.
- `git branch` zeigt nur `main`.
- `rezept.txt` enthält `- Prise Salz`.
- `git status` ist sauber.

## Hinweise

1. `git switch -c <name>` legt einen Branch an und wechselt in einem Schritt.
2. Merge immer *aus* dem Ziel heraus: erst nach `main` wechseln, dann `git merge <branch>`.
   Weil `main` sich nicht weiterbewegt hat, macht Git einen Fast-Forward – es gibt keinen
   eigenen Merge-Commit. Das ist richtig so.
```

- [ ] **Step 4: `02-branch-und-merge/loesung/LOESUNG.md`**

```markdown
# Lösung 02

```bash
git switch -c zutaten-ergaenzen
printf -- "- Prise Salz\n" >> rezept.txt
git add rezept.txt
git commit -m "Salz ergänzt"
git switch main
git merge zutaten-ergaenzen
git branch -d zutaten-ergaenzen
git log --oneline --graph
```

Warum so: Die Änderung entsteht isoliert im Branch; `main` bleibt bis zum Merge
unverändert. `-d` löscht nur, wenn der Branch gemergt ist – ein Schutz gegen Datenverlust.
```

- [ ] **Step 5: Beide Übungen selbst durchspielen**

Vorbereitung und Lösung von 01 und 02 im Scratch-Ordner ausführen; Abnahmekriterien mit `git log --oneline --graph`, `git branch`, `git status` prüfen. Erwartung: alle Kriterien erfüllt. Scratch-Ordner löschen.

- [ ] **Step 6: Commits**

```bash
git add themen/git/uebungen/01-erstes-repo && git commit -m "feat(themen): git Übung 01-erstes-repo"
git add themen/git/uebungen/02-branch-und-merge && git commit -m "feat(themen): git Übung 02-branch-und-merge"
```

---

### Task 10: Java-Übungen

**Files:**
- Create: `themen/java/uebungen/01-erste-klasse/` (Schablone + `AUFGABE.md`, `src/main/java/de/makno/lernen/Begruessung.java`, `src/test/java/de/makno/lernen/BegruessungTest.java`, `loesung/Begruessung.java`, `loesung/LOESUNG.md`)
- Create: `themen/java/uebungen/02-rechner/` (Schablone + `AUFGABE.md`, `src/main/java/de/makno/lernen/Rechner.java`, `src/test/java/de/makno/lernen/RechnerTest.java`, `loesung/Rechner.java`, `loesung/LOESUNG.md`)

- [ ] **Step 1: Gerüste kopieren**

```bash
for u in 01-erste-klasse 02-rechner; do
  cp -r themen/_schablone/uebung-java themen/java/uebungen/$u
  sed -i "s/rootProject.name = 'uebung'/rootProject.name = '$u'/" themen/java/uebungen/$u/settings.gradle
  rm themen/java/uebungen/$u/src/main/java/.gitkeep themen/java/uebungen/$u/src/test/java/.gitkeep
  mkdir -p themen/java/uebungen/$u/src/main/java/de/makno/lernen themen/java/uebungen/$u/src/test/java/de/makno/lernen themen/java/uebungen/$u/loesung
done
```

- [ ] **Step 2: `01-erste-klasse/AUFGABE.md`**

```markdown
# Übung 01: Erste Klasse

## Aufgabe

In `src/main/java/de/makno/lernen/Begruessung.java` fehlt der Inhalt der Methode
`begruesse`. Sie soll für einen Namen den Text `Hallo, <Name>!` liefern. Ist der Name
leer oder `null`, soll `Hallo, Unbekannt!` herauskommen.

Zusätzlich soll `main` die Begrüßung für „Welt" auf der Konsole ausgeben.

## Abnahmekriterien

- `./gradlew test` ist grün (beide Tests in `BegruessungTest`).
- Das Programm lässt sich in der IDE starten und gibt `Hallo, Welt!` aus.

## Hinweise

1. Schau in den Test: Er zeigt genau, welche Eingabe welche Ausgabe erwartet.
2. Strings verbindet man mit `+`. Ob ein String leer ist, sagt `name.isEmpty()` – aber
   Vorsicht: bei `null` wirft das eine Exception, prüfe `null` zuerst.
```

- [ ] **Step 3: Startcode und Test für 01**

`src/main/java/de/makno/lernen/Begruessung.java`:
```java
package de.makno.lernen;

public class Begruessung {

    public String begruesse(String name) {
        // TODO: Hier fehlt die Umsetzung.
        return null;
    }

    public static void main(String[] args) {
        // TODO: Begrüßung für "Welt" ausgeben.
    }
}
```

`src/test/java/de/makno/lernen/BegruessungTest.java`:
```java
package de.makno.lernen;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class BegruessungTest {

    @Test
    void begruesstMitNamen() {
        assertEquals("Hallo, Anna!", new Begruessung().begruesse("Anna"));
    }

    @Test
    void begruesstUnbekanntBeiLeeremOderFehlendemNamen() {
        Begruessung begruessung = new Begruessung();
        assertEquals("Hallo, Unbekannt!", begruessung.begruesse(""));
        assertEquals("Hallo, Unbekannt!", begruessung.begruesse(null));
    }
}
```

- [ ] **Step 4: Lösung für 01**

`loesung/Begruessung.java`:
```java
package de.makno.lernen;

public class Begruessung {

    private static final String UNBEKANNT = "Unbekannt";

    public String begruesse(String name) {
        String anrede = (name == null || name.isEmpty()) ? UNBEKANNT : name;
        return "Hallo, " + anrede + "!";
    }

    public static void main(String[] args) {
        System.out.println(new Begruessung().begruesse("Welt"));
    }
}
```

`loesung/LOESUNG.md`:
```markdown
Warum so: `null` wird vor `isEmpty()` geprüft, sonst gäbe es eine NullPointerException.
Der Ersatzname steht als Konstante, nicht als Magic String mitten im Code.
Die Lösungsdatei gehört nach `src/main/java/de/makno/lernen/`.
```

- [ ] **Step 5: `02-rechner/AUFGABE.md`**

```markdown
# Übung 02: Rechner mit Methoden

## Aufgabe

`src/main/java/de/makno/lernen/Rechner.java` enthält vier Methoden, die alle noch
`UnsupportedOperationException` werfen. Setze sie um:

- `addiere(a, b)`, `subtrahiere(a, b)`, `multipliziere(a, b)` – das Übliche.
- `dividiere(a, b)` – Division; bei `b == 0` soll eine `IllegalArgumentException` mit der
  Nachricht `Division durch 0` fliegen, statt `Infinity` zurückzugeben.

## Abnahmekriterien

- `./gradlew test` ist grün (fünf Tests in `RechnerTest`).
- Keine Methode länger als vier Zeilen.

## Hinweise

1. `double` teilt durch 0 ohne Fehler und liefert `Infinity` – deshalb musst du vorher
   selbst prüfen.
2. Eine Exception wirft man mit `throw new IllegalArgumentException("Division durch 0");`.
```

- [ ] **Step 6: Startcode und Test für 02**

`src/main/java/de/makno/lernen/Rechner.java`:
```java
package de.makno.lernen;

public class Rechner {

    public double addiere(double a, double b) {
        throw new UnsupportedOperationException("noch nicht umgesetzt");
    }

    public double subtrahiere(double a, double b) {
        throw new UnsupportedOperationException("noch nicht umgesetzt");
    }

    public double multipliziere(double a, double b) {
        throw new UnsupportedOperationException("noch nicht umgesetzt");
    }

    public double dividiere(double a, double b) {
        throw new UnsupportedOperationException("noch nicht umgesetzt");
    }
}
```

`src/test/java/de/makno/lernen/RechnerTest.java`:
```java
package de.makno.lernen;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class RechnerTest {

    private final Rechner rechner = new Rechner();

    @Test
    void addiertZweiZahlen() {
        assertEquals(5.0, rechner.addiere(2, 3));
    }

    @Test
    void subtrahiertZweiZahlen() {
        assertEquals(-1.0, rechner.subtrahiere(2, 3));
    }

    @Test
    void multipliziertZweiZahlen() {
        assertEquals(6.0, rechner.multipliziere(2, 3));
    }

    @Test
    void dividiertZweiZahlen() {
        assertEquals(2.5, rechner.dividiere(5, 2));
    }

    @Test
    void divisionDurchNullWirftException() {
        IllegalArgumentException fehler =
                assertThrows(IllegalArgumentException.class, () -> rechner.dividiere(1, 0));
        assertEquals("Division durch 0", fehler.getMessage());
    }
}
```

- [ ] **Step 7: Lösung für 02**

`loesung/Rechner.java`:
```java
package de.makno.lernen;

public class Rechner {

    public double addiere(double a, double b) {
        return a + b;
    }

    public double subtrahiere(double a, double b) {
        return a - b;
    }

    public double multipliziere(double a, double b) {
        return a * b;
    }

    public double dividiere(double a, double b) {
        if (b == 0) {
            throw new IllegalArgumentException("Division durch 0");
        }
        return a / b;
    }
}
```

`loesung/LOESUNG.md`:
```markdown
Warum so: Die Prüfung auf 0 steht vor der Rechnung (Guard Clause) – so bleibt der
Normalfall ohne Einrückung lesbar. Die Lösungsdatei gehört nach `src/main/java/de/makno/lernen/`.
```

- [ ] **Step 8: Startcode rot, Lösung grün – für beide Übungen**

```bash
S="$SCRATCHPAD"   # Scratch-Verzeichnis dieser Sitzung
for u in 01-erste-klasse 02-rechner; do
  rm -rf "$S/$u" && cp -r themen/java/uebungen/$u "$S/$u"
  (cd "$S/$u" && ./gradlew test --no-daemon -q >/dev/null 2>&1; echo "$u start EXIT=$?")
  cp "$S/$u"/loesung/*.java "$S/$u/src/main/java/de/makno/lernen/"
  (cd "$S/$u" && ./gradlew test --no-daemon -q >/dev/null 2>&1; echo "$u loesung EXIT=$?")
done
```
Expected: `start EXIT=1` und `loesung EXIT=0` für beide.

- [ ] **Step 9: Commits**

```bash
git add themen/java/uebungen/01-erste-klasse && git update-index --chmod=+x themen/java/uebungen/01-erste-klasse/gradlew && git commit -m "feat(themen): java Übung 01-erste-klasse"
git add themen/java/uebungen/02-rechner && git update-index --chmod=+x themen/java/uebungen/02-rechner/gradlew && git commit -m "feat(themen): java Übung 02-rechner"
```

---

### Task 11: Gradle-Übungen

**Files:**
- Create: `themen/gradle/uebungen/01-build-datei/` (Schablone mit **absichtlich unvollständiger** `build.gradle`, `AUFGABE.md`, `src/main/java/de/makno/lernen/Hallo.java`, `src/test/java/de/makno/lernen/HalloTest.java`, `loesung/build.gradle`, `loesung/LOESUNG.md`)
- Create: `themen/gradle/uebungen/02-eigener-task/` (Schablone, `AUFGABE.md`, `loesung/build.gradle`, `loesung/LOESUNG.md`)

- [ ] **Step 1: Gerüste kopieren**

```bash
for u in 01-build-datei 02-eigener-task; do
  cp -r themen/_schablone/uebung-java themen/gradle/uebungen/$u
  sed -i "s/rootProject.name = 'uebung'/rootProject.name = '$u'/" themen/gradle/uebungen/$u/settings.gradle
  mkdir -p themen/gradle/uebungen/$u/loesung
done
mkdir -p themen/gradle/uebungen/01-build-datei/src/main/java/de/makno/lernen themen/gradle/uebungen/01-build-datei/src/test/java/de/makno/lernen
rm themen/gradle/uebungen/01-build-datei/src/main/java/.gitkeep themen/gradle/uebungen/01-build-datei/src/test/java/.gitkeep
```

- [ ] **Step 2: 01 – kaputte `build.gradle`, Startcode, Test**

`themen/gradle/uebungen/01-build-datei/build.gradle` (ersetzt die Schablone – ohne Test-Abhängigkeiten und ohne `useJUnitPlatform`):
```groovy
plugins {
    id 'java'
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

tasks.withType(JavaCompile).configureEach {
    options.encoding = 'UTF-8'
}

repositories {
    mavenCentral()
}
```

`src/main/java/de/makno/lernen/Hallo.java`:
```java
package de.makno.lernen;

public class Hallo {

    public String text() {
        return "Hallo Gradle";
    }
}
```

`src/test/java/de/makno/lernen/HalloTest.java`:
```java
package de.makno.lernen;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class HalloTest {

    @Test
    void liefertText() {
        assertEquals("Hallo Gradle", new Hallo().text());
    }
}
```

`AUFGABE.md`:
```markdown
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
```

`loesung/build.gradle`: exakt der Inhalt von `themen/_schablone/uebung-java/build.gradle`.

`loesung/LOESUNG.md`:
```markdown
Warum so: `testImplementation` macht JUnit nur für Tests sichtbar, nicht für den
Produktionscode. `testRuntimeOnly 'org.junit.platform:junit-platform-launcher'` braucht
Gradle 9.6.1, um die Tests zu starten. `useJUnitPlatform()` schaltet den JUnit-5-Runner ein –
ohne ihn findet Gradle keinen einzigen Test und meldet trotzdem Erfolg.
Die Datei ersetzt `build.gradle` im Projektordner.
```

- [ ] **Step 3: 02 – Aufgabe und Lösung**

`themen/gradle/uebungen/02-eigener-task/AUFGABE.md`:
```markdown
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
```

`loesung/build.gradle`: Inhalt der Schablone plus am Ende:
```groovy

tasks.register('hallo') {
    description = 'Gibt eine Begrüßung aus'
    doLast {
        println 'Hallo Gradle!'
    }
}

tasks.register('zaehle') {
    description = 'Zählt bis drei'
    dependsOn 'hallo'
    doLast {
        (1..3).each { println it }
    }
}
```

`loesung/LOESUNG.md`:
```markdown
Warum so: `tasks.register` legt den Task faul an – Gradle konfiguriert ihn nur, wenn er
gebraucht wird. `doLast` trennt Konfiguration von Ausführung; `dependsOn` macht die
Reihenfolge explizit statt sie durch Aufrufreihenfolge zu erzwingen.
Die Datei ersetzt `build.gradle` im Projektordner.
```

- [ ] **Step 4: Prüfen**

```bash
S="$SCRATCHPAD"
rm -rf "$S/01-build-datei" && cp -r themen/gradle/uebungen/01-build-datei "$S/01-build-datei"
(cd "$S/01-build-datei" && ./gradlew test --no-daemon -q >/dev/null 2>&1; echo "start EXIT=$?")
cp "$S/01-build-datei/loesung/build.gradle" "$S/01-build-datei/build.gradle"
(cd "$S/01-build-datei" && ./gradlew test --no-daemon -q; echo "loesung EXIT=$?"; grep -o 'tests="1"' build/test-results/test/TEST-de.makno.lernen.HalloTest.xml)
rm -rf "$S/02-eigener-task" && cp -r themen/gradle/uebungen/02-eigener-task "$S/02-eigener-task"
cp "$S/02-eigener-task/loesung/build.gradle" "$S/02-eigener-task/build.gradle"
(cd "$S/02-eigener-task" && ./gradlew -q zaehle --no-daemon)
```
Expected: `start EXIT=1`, `loesung EXIT=0`, `tests="1"`, dann die vier Zeilen `Hallo Gradle!`, `1`, `2`, `3`.

- [ ] **Step 5: Commits**

```bash
git add themen/gradle/uebungen/01-build-datei && git update-index --chmod=+x themen/gradle/uebungen/01-build-datei/gradlew && git commit -m "feat(themen): gradle Übung 01-build-datei"
git add themen/gradle/uebungen/02-eigener-task && git update-index --chmod=+x themen/gradle/uebungen/02-eigener-task/gradlew && git commit -m "feat(themen): gradle Übung 02-eigener-task"
```

---

### Task 12: JUnit-Übungen

**Files:**
- Create: `themen/junit/uebungen/01-erster-test/` (Schablone, `AUFGABE.md`, `src/main/java/de/makno/lernen/Stapel.java`, `src/test/java/de/makno/lernen/StapelTest.java` leer, `loesung/StapelTest.java`, `loesung/LOESUNG.md`)
- Create: `themen/junit/uebungen/02-parametrisierte-tests/` (Schablone, `AUFGABE.md`, `src/main/java/de/makno/lernen/Schaltjahr.java`, `src/test/java/de/makno/lernen/SchaltjahrTest.java` leer, `loesung/SchaltjahrTest.java`, `loesung/LOESUNG.md`)

Hier ist die Rolle vertauscht: Der Code ist fertig, der Lernende schreibt die Tests. „Startcode rot" heißt hier: Der leere Test lässt `./gradlew test` zwar grün, aber die Abnahme verlangt eine Mindestzahl ausgeführter Tests aus dem XML-Bericht.

- [ ] **Step 1: Gerüste kopieren**

```bash
for u in 01-erster-test 02-parametrisierte-tests; do
  cp -r themen/_schablone/uebung-java themen/junit/uebungen/$u
  sed -i "s/rootProject.name = 'uebung'/rootProject.name = '$u'/" themen/junit/uebungen/$u/settings.gradle
  rm themen/junit/uebungen/$u/src/main/java/.gitkeep themen/junit/uebungen/$u/src/test/java/.gitkeep
  mkdir -p themen/junit/uebungen/$u/src/main/java/de/makno/lernen themen/junit/uebungen/$u/src/test/java/de/makno/lernen themen/junit/uebungen/$u/loesung
done
```

- [ ] **Step 2: 01 – Klasse, leerer Test, Aufgabe**

`src/main/java/de/makno/lernen/Stapel.java`:
```java
package de.makno.lernen;

import java.util.ArrayList;
import java.util.List;

/** Ein einfacher Stapel (LIFO) für Strings. */
public class Stapel {

    private final List<String> elemente = new ArrayList<>();

    public void lege(String element) {
        elemente.add(element);
    }

    public String nimm() {
        if (elemente.isEmpty()) {
            throw new IllegalStateException("Stapel ist leer");
        }
        return elemente.remove(elemente.size() - 1);
    }

    public boolean istLeer() {
        return elemente.isEmpty();
    }

    public int groesse() {
        return elemente.size();
    }
}
```

`src/test/java/de/makno/lernen/StapelTest.java`:
```java
package de.makno.lernen;

class StapelTest {
    // Hier entstehen deine Tests.
}
```

`AUFGABE.md`:
```markdown
# Übung 01: Dein erster Test

## Aufgabe

`Stapel` ist fertig. Schreibe in `StapelTest` mindestens vier Tests:

1. Ein neuer Stapel ist leer.
2. Nach `lege("a")` ist der Stapel nicht leer und hat die Größe 1.
3. `nimm()` liefert das zuletzt gelegte Element (lege „a", dann „b" – `nimm()` gibt „b").
4. `nimm()` auf einem leeren Stapel wirft `IllegalStateException`.

## Abnahmekriterien

- `./gradlew test` ist grün.
- `build/test-results/test/TEST-de.makno.lernen.StapelTest.xml` enthält `tests="4"` (oder
  mehr) und `failures="0"`.
- Jeder Test hat einen Namen, der das geprüfte Verhalten beschreibt.

## Hinweise

1. Ein Test ist eine Methode mit `@Test` darüber (`import org.junit.jupiter.api.Test`).
   Prüfen mit `assertTrue`, `assertEquals` aus `org.junit.jupiter.api.Assertions`.
2. Für Exceptions: `assertThrows(IllegalStateException.class, () -> stapel.nimm())`.
```

`loesung/StapelTest.java`:
```java
package de.makno.lernen;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class StapelTest {

    @Test
    void neuerStapelIstLeer() {
        assertTrue(new Stapel().istLeer());
    }

    @Test
    void nachLegenNichtLeerUndGroesseEins() {
        Stapel stapel = new Stapel();
        stapel.lege("a");
        assertFalse(stapel.istLeer());
        assertEquals(1, stapel.groesse());
    }

    @Test
    void nimmLiefertZuletztGelegtesElement() {
        Stapel stapel = new Stapel();
        stapel.lege("a");
        stapel.lege("b");
        assertEquals("b", stapel.nimm());
    }

    @Test
    void nimmAufLeeremStapelWirftException() {
        assertThrows(IllegalStateException.class, () -> new Stapel().nimm());
    }
}
```

`loesung/LOESUNG.md`:
```markdown
Warum so: Jeder Test prüft genau ein Verhalten und trägt es im Namen. Arrange (Stapel
bauen), Act (legen/nehmen), Assert (prüfen) sind auch ohne Kommentare erkennbar.
Die Datei ersetzt `src/test/java/de/makno/lernen/StapelTest.java`.
```

- [ ] **Step 3: 02 – Klasse, leerer Test, Aufgabe**

`src/main/java/de/makno/lernen/Schaltjahr.java`:
```java
package de.makno.lernen;

public final class Schaltjahr {

    private Schaltjahr() {}

    public static boolean istSchaltjahr(int jahr) {
        if (jahr % 400 == 0) {
            return true;
        }
        if (jahr % 100 == 0) {
            return false;
        }
        return jahr % 4 == 0;
    }
}
```

`src/test/java/de/makno/lernen/SchaltjahrTest.java`:
```java
package de.makno.lernen;

class SchaltjahrTest {
    // Hier entstehen deine parametrisierten Tests.
}
```

`AUFGABE.md`:
```markdown
# Übung 02: Parametrisierte Tests

## Aufgabe

`Schaltjahr.istSchaltjahr` ist fertig. Schreibe **einen** parametrisierten Test mit
`@CsvSource`, der mindestens diese Fälle abdeckt:

| Jahr | Schaltjahr? | Warum |
|---|---|---|
| 2024 | ja | durch 4 teilbar |
| 2023 | nein | nicht durch 4 teilbar |
| 1900 | nein | durch 100, nicht durch 400 |
| 2000 | ja | durch 400 |

## Abnahmekriterien

- `./gradlew test` ist grün.
- `build/test-results/test/TEST-de.makno.lernen.SchaltjahrTest.xml` enthält `tests="4"`
  (oder mehr) und `failures="0"` – jede Zeile der `@CsvSource` zählt als eigener Test.
- Es gibt genau eine Testmethode.

## Hinweise

1. `@ParameterizedTest` und `@CsvSource` liegen in `org.junit.jupiter.params` bzw.
   `org.junit.jupiter.params.provider`. Sie sind über `junit-jupiter` bereits im Projekt.
2. Die Methode bekommt die Spalten als Parameter: `void test(int jahr, boolean erwartet)`.
   Eine Zeile in `@CsvSource` sieht so aus: `"2024, true"`.
```

`loesung/SchaltjahrTest.java`:
```java
package de.makno.lernen;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class SchaltjahrTest {

    @ParameterizedTest(name = "{0} ist Schaltjahr: {1}")
    @CsvSource({
        "2024, true",
        "2023, false",
        "1900, false",
        "2000, true"
    })
    void erkenntSchaltjahre(int jahr, boolean erwartet) {
        assertEquals(erwartet, Schaltjahr.istSchaltjahr(jahr));
    }
}
```

`loesung/LOESUNG.md`:
```markdown
Warum so: Vier Fälle, eine Methode – die Regel steht einmal da, die Daten in der Tabelle.
`name = "{0} ist Schaltjahr: {1}"` macht jede Zeile im Bericht lesbar.
Die Datei ersetzt `src/test/java/de/makno/lernen/SchaltjahrTest.java`.
```

- [ ] **Step 4: Prüfen**

```bash
S="$SCRATCHPAD"
for u in 01-erster-test:StapelTest 02-parametrisierte-tests:SchaltjahrTest; do
  d=${u%%:*}; k=${u##*:}
  rm -rf "$S/$d" && cp -r themen/junit/uebungen/$d "$S/$d"
  cp "$S/$d/loesung/$k.java" "$S/$d/src/test/java/de/makno/lernen/$k.java"
  (cd "$S/$d" && ./gradlew test --no-daemon -q; echo "$d EXIT=$?"; grep -o 'tests="[0-9]*" skipped="[0-9]*" failures="[0-9]*"' build/test-results/test/TEST-de.makno.lernen.$k.xml)
done
```
Expected: beide `EXIT=0`, beide `tests="4" skipped="0" failures="0"`.

- [ ] **Step 5: Commits**

```bash
git add themen/junit/uebungen/01-erster-test && git update-index --chmod=+x themen/junit/uebungen/01-erster-test/gradlew && git commit -m "feat(themen): junit Übung 01-erster-test"
git add themen/junit/uebungen/02-parametrisierte-tests && git update-index --chmod=+x themen/junit/uebungen/02-parametrisierte-tests/gradlew && git commit -m "feat(themen): junit Übung 02-parametrisierte-tests"
```

---

### Task 13: HTML-Übungen

**Files:**
- Create: `themen/html/uebungen/01-erste-seite/AUFGABE.md`, `index.html` (leer bis auf Kommentar), `loesung/index.html`, `loesung/LOESUNG.md`
- Create: `themen/html/uebungen/02-formular/AUFGABE.md`, `index.html` (Schablone), `loesung/index.html`, `loesung/LOESUNG.md`

Web-Übungen werden mit der Browser-Ansicht geprüft: `navigate` auf `file:///…/arbeit/html/<uebung>/index.html`, dann `read_page` bzw. `find`.

- [ ] **Step 1: 01 – Start und Aufgabe**

`themen/html/uebungen/01-erste-seite/index.html`:
```html
<!-- Hier entsteht deine erste Seite. Lösche diesen Kommentar und fang mit <!DOCTYPE html> an. -->
```

`AUFGABE.md`:
```markdown
# Übung 01: Deine erste Seite

## Aufgabe

Schreibe `index.html` von Grund auf:

1. Gültiges Grundgerüst: `<!DOCTYPE html>`, `html` mit `lang="de"`, `head` mit
   `meta charset="UTF-8"` und `title` „Meine erste Seite", `body`.
2. Eine Überschrift `h1` mit deinem Namen oder einem Thema.
3. Zwei Absätze `p` mit je einem Satz.
4. Eine ungeordnete Liste mit genau drei Einträgen (drei Dinge, die du lernen willst).
5. Einen Link zu `https://developer.mozilla.org/de/` mit dem Text „MDN".

## Abnahmekriterien

- Die Seite öffnet sich im Browser ohne Fehler, der Tab zeigt „Meine erste Seite".
- Genau ein `h1`, zwei `p`, eine `ul` mit drei `li`, ein `a` mit dem Text „MDN" und dem
  richtigen `href`.

## Hinweise

1. Jedes öffnende Tag braucht ein schließendes – außer `meta`. Rücke verschachtelte
   Elemente ein, dann siehst du Fehler sofort.
2. `<a href="…">Text</a>` – die Adresse ins Attribut, der sichtbare Text dazwischen.
```

`loesung/index.html`:
```html
<!DOCTYPE html>
<html lang="de">
<head>
  <meta charset="UTF-8">
  <title>Meine erste Seite</title>
</head>
<body>
  <h1>Lernen mit HTML</h1>
  <p>Das ist meine erste selbst geschriebene Seite.</p>
  <p>Sie besteht nur aus Struktur, noch ohne Gestaltung.</p>
  <ul>
    <li>HTML</li>
    <li>CSS</li>
    <li>JavaScript</li>
  </ul>
  <a href="https://developer.mozilla.org/de/">MDN</a>
</body>
</html>
```

`loesung/LOESUNG.md`:
```markdown
Warum so: `lang` und `charset` stehen ganz oben, damit Browser und Screenreader die Seite
richtig lesen. Struktur vor Gestaltung – CSS kommt in einem eigenen Thema.
```

- [ ] **Step 2: 02 – Start und Aufgabe**

`themen/html/uebungen/02-formular/index.html`: Kopie von `themen/_schablone/uebung-web/index.html`, `title` und `h1` auf „Anmeldung" geändert; `style.css` und `app.js` aus der Schablone **nicht** kopieren, die beiden `link`/`script`-Zeilen entfernen.

`AUFGABE.md`:
```markdown
# Übung 02: Ein Formular

## Aufgabe

Baue unter der Überschrift ein Anmeldeformular:

1. `form` mit `method="post"`.
2. Textfeld für den Namen (`type="text"`, `required`) mit `label`.
3. E-Mail-Feld (`type="email"`, `required`) mit `label`.
4. Auswahl `select` „Rolle" mit den Optionen Azubi, Student, Kollege, mit `label`.
5. Checkbox „Newsletter" mit `label`.
6. Button „Absenden".

Jedes `label` ist über `for`/`id` mit seinem Feld verbunden. Jedes Feld hat ein `name`.

## Abnahmekriterien

- Klick auf ein Label setzt den Fokus in das zugehörige Feld (das prüft die Verbindung).
- Absenden ohne Namen wird vom Browser verhindert (`required`).
- Vier Eingabeelemente plus ein Button sind vorhanden.

## Hinweise

1. `<label for="name">Name</label> <input id="name" name="name" type="text" required>` –
   `for` zeigt auf die `id`.
2. Optionen einer Auswahl: `<select id="rolle" name="rolle"><option>Azubi</option>…</select>`.
```

`loesung/index.html`:
```html
<!DOCTYPE html>
<html lang="de">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Anmeldung</title>
</head>
<body>
  <h1>Anmeldung</h1>
  <form method="post">
    <p>
      <label for="name">Name</label>
      <input id="name" name="name" type="text" required>
    </p>
    <p>
      <label for="email">E-Mail</label>
      <input id="email" name="email" type="email" required>
    </p>
    <p>
      <label for="rolle">Rolle</label>
      <select id="rolle" name="rolle">
        <option>Azubi</option>
        <option>Student</option>
        <option>Kollege</option>
      </select>
    </p>
    <p>
      <input id="newsletter" name="newsletter" type="checkbox">
      <label for="newsletter">Newsletter</label>
    </p>
    <button type="submit">Absenden</button>
  </form>
</body>
</html>
```

`loesung/LOESUNG.md`:
```markdown
Warum so: `label for` + `id` macht das Formular per Tastatur und Screenreader bedienbar
und vergrößert die Klickfläche. `required` und `type="email"` lassen den Browser prüfen,
bevor etwas abgeschickt wird.
```

- [ ] **Step 3: Prüfen (Browser-Ansicht)**

Beide `loesung/index.html` mit `navigate` öffnen. `read_page` muss zeigen: bei 01 Titel „Meine erste Seite", ein `heading`, drei `listitem`, ein `link` „MDN"; bei 02 vier Eingabeelemente (textbox, textbox, combobox, checkbox) und einen `button` „Absenden". `find` mit „Name" liefert das Label; Klick darauf fokussiert die Textbox.

- [ ] **Step 4: Commits**

```bash
git add themen/html/uebungen/01-erste-seite && git commit -m "feat(themen): html Übung 01-erste-seite"
git add themen/html/uebungen/02-formular && git commit -m "feat(themen): html Übung 02-formular"
```

---

### Task 14: CSS-Übungen

**Files:**
- Create: `themen/css/uebungen/01-farben-und-schrift/AUFGABE.md`, `index.html`, `style.css` (leer), `loesung/style.css`, `loesung/LOESUNG.md`
- Create: `themen/css/uebungen/02-flexbox-layout/AUFGABE.md`, `index.html`, `style.css` (leer), `loesung/style.css`, `loesung/LOESUNG.md`

- [ ] **Step 1: 01 – HTML, leeres CSS, Aufgabe**

`themen/css/uebungen/01-farben-und-schrift/index.html`:
```html
<!DOCTYPE html>
<html lang="de">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Farben und Schrift</title>
  <link rel="stylesheet" href="style.css">
</head>
<body>
  <h1>Willkommen</h1>
  <p>Ein ganz normaler Absatz.</p>
  <p class="hinweis">Dieser Absatz ist ein Hinweis und soll hervorgehoben sein.</p>
  <p>Noch ein normaler Absatz.</p>
</body>
</html>
```

`style.css`:
```css
/* Hier entsteht dein Stylesheet. */
```

`AUFGABE.md`:
```markdown
# Übung 01: Farben und Schrift

## Aufgabe

Gestalte die Seite nur über `style.css` – `index.html` bleibt unverändert:

1. `body`: Schrift `system-ui, sans-serif`, Hintergrund `#f6f7f9`, Innenabstand `2rem`.
2. `h1`: Farbe `#1d4ed8`.
3. Absätze mit der Klasse `hinweis`: Hintergrund `#fef3c7`, Innenabstand `1rem`.
   Normale Absätze bleiben unverändert.

## Abnahmekriterien

- Die Überschrift ist blau, genau ein Absatz gelb hinterlegt, der Hintergrund hellgrau.
- Der Inspektor zeigt für `h1` `color: rgb(29, 78, 216)` und für `.hinweis`
  `background-color: rgb(254, 243, 199)`.

## Hinweise

1. Eine Regel: `selektor { eigenschaft: wert; }`. Klassen wählt man mit `.klassenname`.
2. Wenn nichts passiert: Ist das Stylesheet verlinkt (ja, in `index.html`)? Seite neu
   laden? Tippfehler im Selektor?
```

`loesung/style.css`:
```css
body {
  font-family: system-ui, sans-serif;
  background: #f6f7f9;
  padding: 2rem;
}

h1 {
  color: #1d4ed8;
}

.hinweis {
  background: #fef3c7;
  padding: 1rem;
}
```

`loesung/LOESUNG.md`:
```markdown
Warum so: Der Klassenselektor trifft nur den markierten Absatz – die anderen `p` bleiben
unberührt. Farben als Hex, Abstände in `rem`, damit sie mit der Schriftgröße skalieren.
Die Datei ersetzt `style.css`.
```

- [ ] **Step 2: 02 – HTML, leeres CSS, Aufgabe**

`themen/css/uebungen/02-flexbox-layout/index.html`:
```html
<!DOCTYPE html>
<html lang="de">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Flexbox</title>
  <link rel="stylesheet" href="style.css">
</head>
<body>
  <h1>Drei Karten</h1>
  <div class="karten">
    <article class="karte"><h2>Git</h2><p>Versionen verwalten.</p></article>
    <article class="karte"><h2>Java</h2><p>Programme schreiben.</p></article>
    <article class="karte"><h2>JUnit</h2><p>Code absichern.</p></article>
  </div>
</body>
</html>
```

`style.css`:
```css
/* Hier entsteht dein Layout. */
```

`AUFGABE.md`:
```markdown
# Übung 02: Drei Karten nebeneinander

## Aufgabe

1. `.karten` wird ein Flex-Container mit `gap: 1rem`.
2. Jede `.karte` bekommt `flex: 1`, einen Rahmen `1px solid #e5e7eb`, abgerundete Ecken
   `0.5rem` und Innenabstand `1rem`.
3. Unter 600 px Breite (`@media (max-width: 600px)`) sollen die Karten untereinander
   stehen (`flex-direction: column`).

## Abnahmekriterien

- Auf einem breiten Fenster stehen die drei Karten in einer Reihe, gleich breit.
- Bei schmalem Fenster (Browser-Ansicht auf „mobile" stellen) stehen sie untereinander.
- Der Inspektor zeigt für `.karten` `display: flex`.

## Hinweise

1. `display: flex` gehört auf den **Container**, `flex: 1` auf die **Kinder**.
2. Eine Media Query umschließt normale Regeln:
   `@media (max-width: 600px) { .karten { flex-direction: column; } }`.
```

`loesung/style.css`:
```css
body {
  font-family: system-ui, sans-serif;
  margin: 2rem;
}

.karten {
  display: flex;
  gap: 1rem;
}

.karte {
  flex: 1;
  border: 1px solid #e5e7eb;
  border-radius: 0.5rem;
  padding: 1rem;
}

@media (max-width: 600px) {
  .karten {
    flex-direction: column;
  }
}
```

`loesung/LOESUNG.md`:
```markdown
Warum so: `flex: 1` verteilt den Platz gleichmäßig, `gap` ersetzt Margin-Tricks. Die
Media Query ändert nur die Richtung – alles andere bleibt gültig.
Die Datei ersetzt `style.css`.
```

- [ ] **Step 3: Prüfen (Browser-Ansicht)**

Für beide Übungen `loesung/style.css` nach `style.css` in eine Scratch-Kopie legen und `index.html` öffnen. Mit `javascript_tool` prüfen:
- 01: `getComputedStyle(document.querySelector('h1')).color` → `rgb(29, 78, 216)`; `getComputedStyle(document.querySelector('.hinweis')).backgroundColor` → `rgb(254, 243, 199)`.
- 02: `getComputedStyle(document.querySelector('.karten')).display` → `flex`; `[...document.querySelectorAll('.karte')].map(k => k.getBoundingClientRect().top)` → drei gleiche Werte. Dann `resize_window` preset `mobile`, neu laden: drei verschiedene, aufsteigende Werte. Danach `resize_window` preset `desktop`.

- [ ] **Step 4: Commits**

```bash
git add themen/css/uebungen/01-farben-und-schrift && git commit -m "feat(themen): css Übung 01-farben-und-schrift"
git add themen/css/uebungen/02-flexbox-layout && git commit -m "feat(themen): css Übung 02-flexbox-layout"
```

---

### Task 15: JavaScript-Übungen

**Files:**
- Create: `themen/javascript/uebungen/01-funktionen-und-arrays/AUFGABE.md`, `aufgabe.js`, `tests.html`, `loesung/aufgabe.js`, `loesung/LOESUNG.md`
- Create: `themen/javascript/uebungen/02-dom-klick/AUFGABE.md`, `index.html`, `app.js` (leer), `loesung/app.js`, `loesung/LOESUNG.md`

- [ ] **Step 1: 01 – Startcode, Test-Seite, Aufgabe**

`aufgabe.js`:
```javascript
// Setze die drei Funktionen um. tests.html im Browser zeigt, was noch fehlt.

function summe(zahlen) {
  return 0;
}

function nurGerade(zahlen) {
  return [];
}

function groesste(zahlen) {
  return undefined;
}
```

`tests.html` (eine Mini-Testseite ohne Bibliothek):
```html
<!DOCTYPE html>
<html lang="de">
<head>
  <meta charset="UTF-8">
  <title>Tests: Funktionen und Arrays</title>
  <style>
    body { font-family: system-ui, sans-serif; margin: 2rem; }
    li { padding: .2rem 0; }
    .ok { color: #16a34a; }
    .fehler { color: #dc2626; }
  </style>
</head>
<body>
  <h1>Tests</h1>
  <p id="ergebnis"></p>
  <ul id="liste"></ul>
  <script src="aufgabe.js"></script>
  <script>
    const faelle = [
      ["summe([1, 2, 3]) ist 6", () => summe([1, 2, 3]) === 6],
      ["summe([]) ist 0", () => summe([]) === 0],
      ["nurGerade([1, 2, 3, 4]) ist [2, 4]", () => JSON.stringify(nurGerade([1, 2, 3, 4])) === "[2,4]"],
      ["nurGerade([1, 3]) ist []", () => JSON.stringify(nurGerade([1, 3])) === "[]"],
      ["groesste([3, 9, 2]) ist 9", () => groesste([3, 9, 2]) === 9],
      ["groesste([]) ist undefined", () => groesste([]) === undefined],
    ];
    let bestanden = 0;
    const liste = document.getElementById("liste");
    for (const [name, pruefung] of faelle) {
      let ok = false;
      try { ok = pruefung(); } catch (e) { ok = false; }
      if (ok) bestanden++;
      const li = document.createElement("li");
      li.className = ok ? "ok" : "fehler";
      li.textContent = (ok ? "✔ " : "✘ ") + name;
      liste.appendChild(li);
    }
    document.getElementById("ergebnis").textContent = `bestanden: ${bestanden}/${faelle.length}`;
  </script>
</body>
</html>
```

`AUFGABE.md`:
```markdown
# Übung 01: Funktionen und Arrays

## Aufgabe

Setze in `aufgabe.js` drei Funktionen um:

- `summe(zahlen)` – Summe aller Zahlen im Array; leeres Array → `0`.
- `nurGerade(zahlen)` – neues Array nur mit den geraden Zahlen, Reihenfolge bleibt.
- `groesste(zahlen)` – die größte Zahl; leeres Array → `undefined`.

Öffne `tests.html` im Browser: Sie zeigt, welche Fälle schon bestehen.

## Abnahmekriterien

- `tests.html` zeigt `bestanden: 6/6`.
- Keine Schleife mit Index (`for (let i = 0; …)`) – benutze `reduce`, `filter`, `Math.max`
  oder `for…of`.

## Hinweise

1. `zahlen.reduce((summe, z) => summe + z, 0)` – der zweite Parameter ist der Startwert
   und rettet dich beim leeren Array.
2. `zahlen.filter(z => z % 2 === 0)`; für die größte Zahl `Math.max(...zahlen)` – bei
   leerem Array liefert das `-Infinity`, also vorher `zahlen.length` prüfen.
```

`loesung/aufgabe.js`:
```javascript
function summe(zahlen) {
  return zahlen.reduce((bisher, zahl) => bisher + zahl, 0);
}

function nurGerade(zahlen) {
  return zahlen.filter(zahl => zahl % 2 === 0);
}

function groesste(zahlen) {
  if (zahlen.length === 0) {
    return undefined;
  }
  return Math.max(...zahlen);
}
```

`loesung/LOESUNG.md`:
```markdown
Warum so: `reduce` und `filter` sagen, *was* passiert, nicht *wie* – kein Zähler, kein
Hilfsarray. Der leere Fall bei `groesste` ist die einzige Verzweigung, und sie steht vorn.
Die Datei ersetzt `aufgabe.js`.
```

- [ ] **Step 2: 02 – HTML, leeres Skript, Aufgabe**

`index.html`:
```html
<!DOCTYPE html>
<html lang="de">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Zähler</title>
  <style>
    body { font-family: system-ui, sans-serif; margin: 2rem; }
    #anzeige { font-size: 2rem; font-weight: bold; margin: 0 1rem; }
    button { font-size: 1rem; padding: .4rem .8rem; }
  </style>
</head>
<body>
  <h1>Zähler</h1>
  <p>
    <button id="minus">−</button>
    <span id="anzeige">0</span>
    <button id="plus">+</button>
    <button id="reset">Zurücksetzen</button>
  </p>
  <script src="app.js"></script>
</body>
</html>
```

`app.js`:
```javascript
// Hier entsteht dein Zähler.
```

`AUFGABE.md`:
```markdown
# Übung 02: Ein Zähler mit Klicks

## Aufgabe

Setze in `app.js` einen Zähler um:

- Klick auf `+` erhöht die Zahl in `#anzeige` um 1, Klick auf `−` verringert sie um 1.
- `Zurücksetzen` setzt sie auf 0.
- Der Wert darf nicht unter 0 fallen.

`index.html` bleibt unverändert.

## Abnahmekriterien

- Dreimal `+`, einmal `−` → Anzeige `2`. Danach `Zurücksetzen` → `0`. Dann `−` → bleibt `0`.
- Der aktuelle Wert steht in **einer** Variablen; die Anzeige wird aus ihr aktualisiert,
  nicht aus dem Text im `span` zurückgelesen.

## Hinweise

1. Elemente holen: `document.querySelector("#plus")`. Reagieren:
   `element.addEventListener("click", () => { … })`.
2. Eine Funktion `zeige()` schreibt den Wert in `#anzeige` (`textContent`). Jeder Klick
   ändert die Variable und ruft dann `zeige()` auf – so gibt es nur einen Ort, der die
   Anzeige verändert.
```

`loesung/app.js`:
```javascript
const anzeige = document.querySelector("#anzeige");
let wert = 0;

function zeige() {
  anzeige.textContent = String(wert);
}

document.querySelector("#plus").addEventListener("click", () => {
  wert += 1;
  zeige();
});

document.querySelector("#minus").addEventListener("click", () => {
  wert = Math.max(0, wert - 1);
  zeige();
});

document.querySelector("#reset").addEventListener("click", () => {
  wert = 0;
  zeige();
});

zeige();
```

`loesung/LOESUNG.md`:
```markdown
Warum so: Der Zustand lebt in `wert`, die Anzeige ist nur eine Sicht darauf. `Math.max(0, …)`
hält die Untergrenze ohne `if`. Die Datei ersetzt `app.js`.
```

- [ ] **Step 3: Prüfen (Browser-Ansicht)**

01: Scratch-Kopie, `loesung/aufgabe.js` über `aufgabe.js`, `tests.html` öffnen, `find` „bestanden" → Text `bestanden: 6/6`. Mit dem Startcode muss `bestanden: 1/6` stehen (nur `summe([]) ist 0` besteht) – das ist der „rote" Zustand.
02: Scratch-Kopie mit Lösung, `index.html` öffnen; per `find`/`computer` dreimal `+`, einmal `−` klicken → `#anzeige` zeigt `2`; `Zurücksetzen` → `0`; `−` → `0`. `read_console_messages` ohne Fehler.

- [ ] **Step 4: Commits**

```bash
git add themen/javascript/uebungen/01-funktionen-und-arrays && git commit -m "feat(themen): javascript Übung 01-funktionen-und-arrays"
git add themen/javascript/uebungen/02-dom-klick && git commit -m "feat(themen): javascript Übung 02-dom-klick"
```

---

### Task 16: Probelauf und Abschluss

**Files:**
- Modify: `fortschritt/_beispiel.md` (nur falls der Probelauf ein Formatproblem aufdeckt)
- Modify: `README.md`, `AGENTS.md` (nur bei Korrekturen aus dem Probelauf)

- [ ] **Step 1: Gesamtprüfung der Werkzeuge**

```bash
javac -encoding UTF-8 -d build/tools tools/Fortschritt.java tools/FortschrittTest.java && java -cp build/tools FortschrittTest
java tools/Fortschritt.java _beispiel
git status --short
```
Expected: `Alle Tests bestanden`, `Geschrieben: …`, `git status` leer (arbeit/ und build/ ignoriert).

- [ ] **Step 2: Probelauf als Lernender**

In einer **neuen** Claude-Code-Sitzung im Repo `/lernen git` ausführen und die Rolle des Lernenden „Test Person" (Sprache `en`, Rolle `azubi`, IDE `keine`) spielen. Prüfen:

1. Es wird nacheinander (nicht auf einmal) nach Name, Sprache, Rolle, IDE, Vorwissen gefragt; `fortschritt/test-person.md` entsteht mit korrektem Frontmatter und ohne Themenabschnitte.
2. Ab der Sprachwahl wird Englisch gesprochen; Befehle bleiben Englisch.
3. Umgebungsprüfung: `git --version` wird ausgeführt und gemeldet.
4. Fortschrittsanzeige für Git erscheint (0/8), Lektion 01 wird `begonnen` eingetragen.
5. Erklärung → Prüffrage → Übung: `arbeit/git/01-erstes-repo/` wird angelegt, **ohne** `loesung/`; Vorbereitung wird ausgeführt.
6. Absichtlich einen Fehler machen (`git commit` ohne `add`): Rückmeldung ist ein Hinweis, keine Lösung.
7. Übung lösen; Claude prüft mit `git log --oneline` und `git status`.
8. „Schluss" sagen: Zusammenfassung, `- 01: fertig <heute>` und `notizen:` in der Datei, `arbeit/fortschritt.html` wird erzeugt und geöffnet.

Abweichungen in `SKILL.md` bzw. `AGENTS.md` korrigieren, dann Schritt 2 wiederholen.

- [ ] **Step 3: Probelauf als Ausbilder**

`/thema-anlegen eclipse` starten, die Klärungsfragen beantworten, den Lehrplan-Entwurf ansehen – und dann **ablehnen** (kein Commit). Prüfen: Ordner `themen/eclipse/` wurde angelegt, `java tools/Fortschritt.java _beispiel` zeigt das Thema, kein Commit ist erfolgt. Danach `rm -rf themen/eclipse` und `git status` leer.

- [ ] **Step 4: Aufräumen und Abschluss-Commit**

```bash
rm -f fortschritt/test-person.md
rm -rf arbeit
git status --short
git add -A
git commit -m "docs: Korrekturen aus dem Probelauf"   # nur, wenn Schritt 2/3 Änderungen ergaben
git log --oneline | head -30
```
Expected: ~27 Commits von „docs: Design des Lehr-Agenten" bis hier.

---

## Selbstprüfung des Plans gegen die Spec

| Spec-Abschnitt | Task |
|---|---|
| Aufbau des Repos, .gitignore, arbeit/, loesung/ | 1, 2 |
| Ablauf `/lernen`: Einstieg, IDE-Abfrage und -Wechsel, Übersicht, Kernschleife, Ende | 6 |
| Umgebungsprüfung vor jeder Lektion (Ergänzung nach Spec-Freigabe) | 1 (AGENTS.md), 6 |
| Fortschrittsanzeige Text | 6 |
| Fortschrittsseite HTML (`Fortschritt.java`, Vorlage, JS-Rendering, 🔒) | 3, 4, 5 |
| Dateiformate Lehrplan / Fortschritt | 1 (AGENTS.md), 3, 4 (Parser + Tests) |
| Übungsordner-Konvention | 2, 9–15 |
| `/thema-anlegen` inkl. Framework-Hinweis | 7 |
| Sieben Lehrpläne | 8 |
| Je Thema zwei Übungen mit Referenzlösung, rot/grün geprüft | 9–15 |
| Prüfung: Tool-Tests, Lösungen, Probelauf | 3–5, 9–15, 16 |
| Nicht in Runde 1 (Sprachfassungen, IDE-Themen, Framework, Ausbilder-Sammelansicht) | bewusst kein Task |

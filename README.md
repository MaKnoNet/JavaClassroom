# Teacher – Lernen mit Claude Code

Interaktiver Unterricht zu Git, Java, Gradle, JUnit, Spring Boot, Vaadin, HTML, CSS, JavaScript modernem Frontend mit TypeScript, Datenformaten (JSON, YAML, XML, CSV) sowie Containern, Compose, CI/CD und OAuth2.

## Für Lernende

Du brauchst zu Beginn **nichts** außer Claude Code und einem Clone dieses Repos – alles
Weitere (JDK, Git, IDE) prüft der Lehrer vor jeder Lektion und hilft bei der Installation.

1. Claude Code installieren (Desktop-App oder Terminal, siehe claude.com/claude-code).
2. Dieses Repository klonen oder als ZIP herunterladen und entpacken.
3. Im Repo-Ordner Claude Code starten und einfach etwas schreiben – „hallo" reicht. Der
   Lehrer stellt sich vor und erklärt, wie es weitergeht. Direkt loslegen: `/lernen`.
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

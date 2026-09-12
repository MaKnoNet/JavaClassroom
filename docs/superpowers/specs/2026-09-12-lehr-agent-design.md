# Lehr-Agent für Softwareentwicklung – Design

Datum: 2026-09-12
Status: freigegeben

## Ziel

Ein Claude-Code-basierter Lehrer, der Azubis, Studenten und neue Kollegen interaktiv
in Themen der Softwareentwicklung unterrichtet. Jedes Thema hat einen eigenen Ordner mit
Lehrplan und Übungen. Der Lernfortschritt wird pro Person lokal geführt. Unterrichtssprache
ist pro Person wählbar (Deutsch, Englisch, Französisch, weitere), die Lehrpläne werden nur
auf Deutsch gepflegt und beim Unterrichten live übersetzt.

Erste Themen: Git, Java, Gradle, JUnit, HTML, CSS, JavaScript. Später: das firmeneigene
Framework (Quellmaterial liegt außerhalb dieses Repos).

## Entscheidungen

| Frage | Entscheidung | Begründung |
|---|---|---|
| Was ist der „Agent"? | Skill `/lernen` + Projektinstruktionen in `AGENTS.md`; Claude unterrichtet in der Hauptsitzung | Subagenten liefern Berichte, führen keinen Dialog. Unterricht braucht Frage-Antwort-Rückmeldung mit Zugriff auf Build, Browser und Dateien |
| Fortschritt | Jeder Lernende hat einen eigenen Clone; `fortschritt/<name>.md` ist nicht versioniert | Keine Vermischung, keine Merge-Konflikte; Stand kann als HTML weitergegeben werden |
| Lehrplan | Fester Rahmen (Lektionen, Ziele, Reihenfolge), Didaktik passt sich der Person an | Vergleichbarkeit für Azubis, Anpassung an Tempo und Niveau |
| Üben | Konzeptfragen im Gespräch, Fertigkeiten hands-on in echten Projekten | Gradle, JUnit, Git lernt man nicht durch Zuschauen |
| Sprachen | Lehrpläne und Aufgabentexte nur Deutsch, Live-Übersetzung; Code und Fachbegriffe Englisch | Ein Wahrheitsort, kein Auseinanderlaufen von Fassungen |
| Fortschrittsanzeige | Textbalken im Chat plus generierte HTML-Seite | HTML ist für Ausbilder und Lernende übersichtlicher |
| IDE | Wird beim ersten Start abgefragt, in der Fortschrittsdatei gespeichert, jederzeit änderbar | Anleitungen passen zur IDE; Wechsel (z. B. Eclipse → IntelliJ) sind vorgesehen |
| KB-Setup (OKF, graphify) | Nicht angelegt | Kein Bibliothekscode; die „Architektur" ist der Lehrplan selbst |

## Aufbau des Repos

```
Teacher/
├── AGENTS.md                  Lehrerrolle, Regeln, Ablauf (Quelle der Wahrheit)
├── CLAUDE.md                  nur „@AGENTS.md"
├── README.md                  für Lernende: Claude Code öffnen, „/lernen java" tippen
├── .gitignore                 fortschritt/* (außer _beispiel.md), arbeit/, build/, .gradle/,
│                              .claude/settings.local.json
├── .claude/skills/
│   ├── lernen/SKILL.md        Einstieg für Lernende
│   └── thema-anlegen/SKILL.md Einstieg für Ausbilder
├── tools/
│   ├── Fortschritt.java       Single-File-Java: Markdown → JSON → HTML
│   └── fortschritt.template.html
├── themen/
│   ├── _schablone/            Vorlage für neue Themen (lehrplan.md, uebungen/)
│   ├── git/  java/  gradle/  junit/  html/  css/  javascript/
│   │   ├── lehrplan.md
│   │   └── uebungen/NN-name/
│   │       ├── AUFGABE.md
│   │       ├── (Projekt: build.gradle, gradlew, src/… bzw. HTML-Dateien)
│   │       └── loesung/       Referenzlösung mit kurzer Begründung
│   └── (später: firmeneigenes Framework)
├── fortschritt/
│   ├── _beispiel.md           Schablone, versioniert
│   └── <name>.md              persönlich, nicht versioniert
├── arbeit/                    nicht versioniert – Arbeitskopien der Übungen
│   ├── <thema>/NN-name/
│   └── fortschritt.html       generierte Fortschrittsseite
└── docs/superpowers/specs/    dieses Dokument
```

Übungen werden nach `arbeit/` kopiert und dort bearbeitet; `themen/` bleibt unverändert,
damit `git pull` neue Lektionen konfliktfrei bringt.

Referenzlösungen liegen im Repo. Ein Verstecken im selben Clone ist technisch nicht
möglich; es ist eine Vertrauensfrage. Im Unterricht werden sie erst nach mehreren
Versuchen oder auf Wunsch gezeigt.

## Ablauf einer Stunde: `/lernen [thema]`

### Einstieg

1. Person bestimmen: Vorschlag aus `git config user.name`. Fehlt `fortschritt/<name>.md`,
   wird sie aus `_beispiel.md` angelegt; abgefragt werden Sprache, Rolle
   (Azubi/Student/Kollege), IDE (Eclipse/IntelliJ/VS Code/keine) und Vorwissen in zwei Sätzen.
2. Ab hier alles in der Unterrichtssprache.
3. Zu Beginn jeder Stunde kurze Rückfrage, ob die IDE noch stimmt; der Lernende kann den
   Eintrag jederzeit ändern. Bei Wechsel: kurzer Umstiegshinweis.
4. Ohne Thema-Argument: Übersicht aller Themen (Textbalken) und Fortschrittsseite.
5. Mit Thema: Lehrplan laden, Fortschritt lesen, nächste Lektion bestimmen.
   Fehlende Voraussetzungen werden benannt, aber nicht erzwungen.

### Lektion (Kernschleife)

- **Erklären** – ein Konzept pro Schritt, kurz, mit Beispiel.
- **Prüfen** – eine Verständnisfrage. Bei falscher Antwort Gegenfrage oder kleineres
  Beispiel, nicht die Lösung.
- **Üben** – Übung nach `arbeit/<thema>/NN-name/` kopieren, `AUFGABE.md` erklären, sagen,
  wie man sie in der eingestellten IDE öffnet (Eclipse: Import → Existing Gradle Project,
  nach Änderungen an `build.gradle` „Refresh Gradle Project"; IntelliJ: Open). Der Lernende
  schreibt Code; Claude wartet. Dann echte Prüfung: `gradlew test`, Browser-Vorschau bei
  HTML/CSS/JS, `git log`/`git status` bei Git.
- **Rückmeldung** – auf das echte Ergebnis. Hinweise gestaffelt: erst allgemein, dann
  konkreter, Lösung beim dritten Anlauf oder auf Wunsch. Code des Lernenden wird nie
  stillschweigend geändert; Vorschläge immer mit Begründung.
- **Lektionsabschluss** – zwei Sätze Zusammenfassung, Fortschrittsdatei fortschreiben,
  Fortschrittsanzeige.

### Ende der Stunde

- Zusammenfassung: gelernt / noch wackelig.
- Fortschrittsdatei: Status der Lektion, Datum, Notizen für nächstes Mal.
- Fortschrittsseite neu erzeugen; auf Wunsch Hinweis, dass `arbeit/fortschritt.html`
  als einzelne Datei an den Ausbilder weitergegeben werden kann.

### Umgebungsprüfung (Ergänzung 2026-09-12, nach Freigabe)

Lernende haben zu Beginn nichts installiert. Vor **jeder** Lektion prüft Claude per Shell,
was das Thema braucht (Git für alle; JDK 21 und Gradle-Wrapper für Java/Gradle/JUnit;
Browser-Ansicht für HTML/CSS/JS; die eingestellte IDE), meldet das Ergebnis und leitet bei
Fehlendem die Installation an – Befehl pro Betriebssystem zeigen, vom Lernenden ausführen
lassen, neues Terminal, erneut prüfen. Die Tabelle mit Prüf- und Installationsbefehlen steht
in `AGENTS.md`. Die Installation ist Teil der Stunde, nicht Voraussetzung dafür.

### Haltungsregeln (in `AGENTS.md`, gelten auch ohne `/lernen`)

Fragen statt vorsagen; ein Schritt pro Nachricht; Fehler sind Lernanlass, kein Vorwurf;
Code, Bezeichner und Fachbegriffe Englisch, Erklärung in der Unterrichtssprache; nicht
über die Lektion hinaus abschweifen, außer auf Nachfrage; Tempo bestimmt der Lernende.

## Fortschrittsanzeige

### Text im Chat

```
Java  ████████░░░░░░░░  5 / 12 Lektionen
  ✔ 01 Erste Klasse        ✔ 02 Variablen & Typen
  ▶ 06 Vererbung (begonnen 2026-09-12)
  ○ 07 Interfaces …
```

Kurzform für alle Themen bei `/lernen` ohne Argument. Status: ✔ fertig, ▶ begonnen,
○ offen, 🔒 Voraussetzung fehlt.

### HTML-Seite

- `tools/Fortschritt.java`, gestartet als `java tools/Fortschritt.java <name>`
  (Single-File-Launch, kein Build, keine Abhängigkeiten). Liest `fortschritt/<name>.md`
  und alle `themen/*/lehrplan.md`, erzeugt JSON und schreibt `arbeit/fortschritt.html`
  aus `tools/fortschritt.template.html` mit eingebettetem JSON.
- Rendering (Balken, Prozent, Status, Sortierung) passiert in JavaScript im Browser.
  Das Java-Programm parst nur Markdown.
- Inhalt: Gesamtübersicht; pro Thema eine Karte mit Balken, Lektionsliste mit Status,
  Datum der letzten Stunde, Notizen für nächstes Mal.
- Die Seite ist eigenständig (eine Datei, offline lesbar) und wird von `/lernen` in der
  Browser-Ansicht geöffnet.
- Java statt Shell: die einzige Laufzeit, die im Repo garantiert ist und auf Windows,
  Linux und macOS gleich läuft.

## Dateiformate

### `themen/<thema>/lehrplan.md`

```markdown
---
thema: java
titel: Java
voraussetzungen: []            # z. B. [java] beim Thema junit
zielgruppe: [azubi, student]
---

# Java

Zwei Absätze: worum es geht, was am Ende sitzen soll.

## Lektionen

### 01 Erste Klasse
- **Ziele:** Klasse, main-Methode, Kompilieren und Starten verstehen
- **Übung:** uebungen/01-erste-klasse
- **Prüffrage:** Was unterscheidet eine Klasse von einem Objekt?
```

Konvention: Lektionsnummer zweistellig, Überschrift Ebene 3, die drei Aufzählungspunkte
in dieser Reihenfolge. Weiterer Text ist frei und dient als Leitfaden. `Übung:` darf
fehlen (reine Konzeptlektion).

### `fortschritt/<name>.md`

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
- 03: begonnen 2026-09-12
- notizen: Tut sich mit Referenz vs. Wert schwer – nächstes Mal Beispiel mit Listen.
```

Frontmatter für das Profil, ein Abschnitt (`## <thema>`) pro Thema, eine Zeile pro
Lektion mit Status (`fertig` | `begonnen`) und Datum (ISO), eine `notizen:`-Zeile.
Nicht erwähnte Lektionen gelten als offen.

### Übungsordner `uebungen/NN-name/`

- `AUFGABE.md`: Aufgabe, Abnahmekriterien, gestaffelte Hinweise.
- Das Projekt: Java-Übungen als eigenständiges Gradle-Projekt (Wrapper, `settings.gradle`,
  Java-21-Toolchain, JUnit 5), importierbar in Eclipse (Buildship) und IntelliJ.
  HTML/CSS/JS-Übungen als Dateien, die im Browser geöffnet werden. Git-Übungen: die
  Vorbereitung (Wegwerf-Repo mit vorgegebener Historie) beschreibt `AUFGABE.md` als
  Schrittfolge, die Claude ausführt – kein Shell-Skript, damit es plattformneutral bleibt.
- `loesung/`: Referenzlösung mit einem Satz, warum sie so aussieht.

## Ausbilder-Skill: `/thema-anlegen <name>`

1. Fragt Titel, Zielgruppe, Voraussetzungen und „was soll am Ende sitzen" ab.
2. Fragt nach Quellmaterial. Allgemeinthemen: keines. Firmenframework: Pfad zu Code/Doku,
   ggf. anderes Repo; die Lektionen werden daraus abgeleitet.
3. Legt `themen/<name>/` aus `_schablone/` an, schreibt einen Lehrplan-Entwurf mit 6–12
   Lektionen (Ziele, Prüffrage, Übungsidee).
4. Ausbilder prüft im Chat. Erst nach Freigabe werden Übungen erzeugt: Projektgerüst,
   `AUFGABE.md`, Referenzlösung, Tests.
5. Sicherung: jede Referenzlösung besteht ihre Tests, bevor sie ins Repo kommt; der
   Lehrplan wird mit `Fortschritt.java` geparst. Abschluss: Commit-Vorschlag.

Firmencode gehört nicht in dieses Repo. Framework-Lehrpläne verweisen per relativem Pfad
oder Umgebungsvariable auf das Framework-Repo; Übungen binden es als Abhängigkeit ein.
Die Schablone enthält dazu einen Hinweis; gebaut wird es erst mit dem ersten
Framework-Thema.

## Umfang der ersten Runde

- Gerüst komplett: `AGENTS.md`, `CLAUDE.md`, `README.md`, `.gitignore`, beide Skills,
  `_schablone/`, `fortschritt/_beispiel.md`, `tools/Fortschritt.java` + Vorlage.
- Alle sieben Lehrpläne vollständig (Lektionen, Ziele, Prüffragen).
- Je Thema zwei bis drei fertige Übungen mit Referenzlösung.
- Reihenfolge nach Voraussetzungen: Git → Java → Gradle → JUnit; parallel HTML → CSS →
  JavaScript.

Nicht in der ersten Runde: Übungstexte pro Sprache, IDE-Themen (Eclipse/IntelliJ als
eigener Lehrplan), Framework-Thema, Ausbilder-Sammelansicht über mehrere Lernende.

## Prüfung

- `tools/Fortschritt.java` wird gegen `fortschritt/_beispiel.md` und alle Lehrpläne
  ausgeführt; die erzeugte Seite wird in der Browser-Ansicht geprüft.
- Jede Übung: Referenzlösung besteht ihre Tests bzw. rendert korrekt.
- Probelauf: eine Lektion wird mit Claude als „Lernendem" komplett durchgespielt
  (Einstieg, Erklären, Prüfen, Üben, Rückmeldung, Fortschritt).

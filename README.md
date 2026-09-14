# JavaClassroom – Lernen mit Claude Code

Ein Lernsystem für Azubis, Studenten und neue Kollegen in der Softwareentwicklung: Claude
Code ist der **Lehrer**, du bist der **Lernende**. Der Lehrer erklärt, fragt nach, gibt
Übungen an echten Projekten und prüft das echte Ergebnis – Tests laufen, Seiten öffnen sich
im Browser, SQL läuft gegen eine echte Datenbank. Dein Fortschritt bleibt auf deinem Rechner.

**16 Themen, 154 Lektionen, 86 Übungen** – jede Übung mit Startzustand (rot) und
Referenzlösung (grün) durchgespielt.

## Themen

| Thema | Lektionen | Übungen | Setzt voraus |
|---|---|---|---|
| **Git** | 14 | 7 | – |
| **HTML** | 7 | 2 | – |
| **CSS** | 7 | 2 | HTML |
| **JavaScript** | 8 | 2 | HTML |
| **Modernes Frontend und TypeScript** (Vite, TypeScript, Lit) | 4 | 4 | HTML, CSS, JavaScript |
| **Java** | 19 | 8 | – |
| **Gradle** | 13 | 10 | Java |
| **JUnit** | 13 | 7 | Java, Gradle |
| **UML-Diagramme** (Klassen-, Sequenz-, Zustandsdiagramme mit PlantUML) | 8 | 7 | Java |
| **Entwurfsmuster** (Strategie, Beobachter, Builder, Dekorierer, Kommando, Zustand …) | 11 | 9 | Java, UML |
| **Datenbanken und SQL** (PostgreSQL, H2 oder SQLite; Flyway, Fensterfunktionen, Indizes) | 10 | 10 | – |
| **Spring Boot** (IoC, REST, JPA, Transaktionen, N+1, Resilience4j) | 7 | 6 | Java, Gradle, JUnit, Datenbanken |
| **Vaadin** | 18 | 2 | Java, Gradle, JUnit, Spring Boot |
| **Datenformate** (JSON, YAML/TOML/XML, CSV, Streaming, XXE) | 5 | 4 | Java, Gradle, JUnit |
| **Sicherheit** (OWASP, SQL-Injection, XSS, Passwort-Hashing) | 5 | 3 | Java, Gradle, JUnit |
| **Cloud-Native und Betrieb** (Docker, Compose, CI/CD, OAuth2/OIDC, Fehlersuche) | 5 | 3 | Java, Gradle, Git, Spring Boot |

Einstieg für Anfänger: Git oder HTML. Wer Java kennt: Gradle und JUnit. SQL braucht keine
Programmierkenntnisse – die Übungen sind reine `.sql`-Dateien, geprüft von SQL selbst.

## Für Lernende

Du brauchst zu Beginn **nichts** außer Claude Code und einem Clone dieses Repos – alles
Weitere (JDK, Git, IDE, Node, Podman, eine Datenbank) prüft der Lehrer vor jeder Lektion
und hilft bei der Installation.

1. Claude Code installieren (Desktop-App oder Terminal, siehe claude.com/claude-code).
2. Dieses Repository klonen:
   ```bash
   git clone https://github.com/MaKnoNet/JavaClassroom.git
   ```
   oder als ZIP herunterladen und entpacken.
3. Im Repo-Ordner Claude Code starten und einfach etwas schreiben – „hallo" reicht. Der
   Lehrer stellt sich vor und erklärt, wie es weitergeht. Direkt loslegen: `/lernen`,
   ein bestimmtes Thema: `/lernen git`.
4. Beim ersten Mal wirst du nach Name, Sprache, Rolle, IDE und Vorwissen gefragt.
   Das landet in `fortschritt/<dein-name>.md` – nur auf deinem Rechner, nie im Repository.

Übungen werden nach `arbeit/` kopiert. Dort arbeitest du; `themen/` bleibt unverändert,
damit `git pull` neue Inhalte konfliktfrei bringt.

Deinen Stand siehst du in `arbeit/fortschritt.html` – eine einzelne Datei, die du deinem
Ausbilder schicken kannst. Klicks auf der Seite starten die nächste Lektion im Chat.

## Für Ausbilder

- Neues Thema: `/thema-anlegen <name>` in Claude Code.
- Lehrpläne liegen unter `themen/<thema>/lehrplan.md`, Übungen darunter in `uebungen/`
  (`AUFGABE.md`, Projekt, `loesung/`). Schablonen für Java-, Web- und SQL-Übungen unter
  `themen/_schablone/`.
- Alle Konventionen, Umgebungsprüfung und Installationshilfen: `AGENTS.md`.
- Firmencode gehört nicht in dieses Repo.
- Fehler im Material: Der Lehrer meldet sie nach Rückfrage als Issue (`/fehler-melden`),
  oder du öffnest selbst eines über die Vorlage „Fehler im Lehrmaterial".

## Aufbau

| Pfad | Inhalt |
|---|---|
| `themen/<thema>/` | Lehrplan und Übungen eines Themas |
| `fortschritt/` | persönlicher Fortschritt (nicht versioniert) |
| `arbeit/` | Arbeitskopien der Übungen und die Fortschrittsseite (nicht versioniert) |
| `tools/Fortschritt.java` | erzeugt die Fortschrittsseite |
| `.claude/skills/` | die Skills `/lernen` und `/thema-anlegen` |
| `AGENTS.md` | Regeln für den Lehrer – die Quelle der Wahrheit |

Fortschrittsseite von Hand erzeugen:

```bash
java tools/Fortschritt.java <name>
```

Alles läuft auf Windows, macOS und Linux; maschinenspezifische Einstellungen (Proxy,
Zertifikate) gehören nie ins Repository – `AGENTS.md` beschreibt die Alternativen.

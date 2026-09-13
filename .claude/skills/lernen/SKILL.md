---
name: lernen
description: Startet oder setzt eine Unterrichtsstunde fort. Aufruf `/lernen` (Übersicht), `/lernen <thema>` (z. B. `/lernen git`), `/lernen <thema> <NN>` (bestimmte Lektion) oder `/lernen sprache <code>` (Unterrichtssprache wechseln). Verwenden, wenn jemand lernen, üben, weitermachen oder seinen Stand sehen will.
---

# /lernen [thema] [NN] | /lernen sprache <code>

Du bist der Lehrer. Regeln und Formate stehen in `AGENTS.md` – sie gelten hier vollständig.
Führe die Schritte in dieser Reihenfolge aus. Sprich ab Schritt 2 in der Unterrichtssprache.

Argumente (die Fortschrittsseite erzeugt sie per Klick, der Lernende fügt sie ein):

- keins → Übersicht (Abschnitt 2).
- `<thema>` → nächste offene Lektion des Themas (Abschnitt 3 ff.).
- `<thema> <NN>` → genau diese Lektion, auch wenn frühere offen sind. Ist sie schon
  `fertig`, fragen: wiederholen oder nur die Prüffrage? Beim Vorgreifen kurz nennen, was
  die übersprungenen Lektionen vorausgesetzt hätten – der Lernende entscheidet.
- `sprache <code>` → `sprache:` in der Fortschrittsdatei auf den ISO-Code setzen, in der
  neuen Sprache bestätigen und fragen, ob es mit dem zuletzt begonnenen Thema weitergeht.
  Kein weiterer Schritt.
- **Kein Argument, aber der Lernende schreibt nur „los", „weiter", „die hab ich
  angeklickt" o. Ä.:** Den Befehl von der Fortschrittsseite lesen, statt nachzufragen.
  Reihenfolge: (1) Browser-Ansicht von Claude Code – Tab mit Titel „Lernfortschritt",
  `find` nach `/lernen` bzw. `javascript_tool` mit
  `document.getElementById("befehl-text").textContent`; (2) sonst Chrome mit der
  Erweiterung „Claude in Chrome" (`mcp__claude-in-chrome__*`, zuerst per ToolSearch
  laden, `tabs_context` nach einem Tab „Lernfortschritt" durchsuchen, dann dasselbe
  lesen). Gefundenen Befehl im Chat nennen („Du hast Java 06 angeklickt – los geht's")
  und ausführen. Nichts gefunden → normal nachfragen.

## 1. Lernenden bestimmen

1. `git config user.name` lesen. Daraus den Dateinamen bilden: Kleinbuchstaben, Leerzeichen
   → `-`, Umlaute → ae/oe/ue (z. B. `max-mustermann`). Fehlt der Name, nach dem Namen fragen.
2. Existiert `fortschritt/<name>.md`? Wenn nein, **eine Frage nach der anderen** stellen:
   - Wie heißt du? (Vorschlag aus Git anbieten; der Dateiname wird aus der **Antwort**
     gebildet, nicht zwingend aus `git config user.name`)
   - In welcher Sprache sollen wir arbeiten? (Deutsch / English / Français / andere)
   - Wie bist du hier – in der Ausbildung, im Studium bzw. Praktikum oder als neuer
     Kollege? (`rolle`: azubi | student | kollege – **nur Kontext**, sagt nichts über das
     Können; Azubis können weit sein, Studenten Anfänger)
   - Wie viel hast du schon programmiert? (`niveau`)
     - noch gar nicht → `anfaenger`
     - ein bisschen: Schule, Tutorials, kleine Skripte → `anfaenger`
     - regelmäßig: eigene Programme, mehrere Sprachen oder ein größeres Projekt → `fortgeschritten`
     - beruflich, schon in Projekten gearbeitet → `erfahren`
   - Welche IDE benutzt du? (Eclipse / IntelliJ / VS Code / noch keine)
   - Womit hast du bisher gearbeitet – Sprachen, Werkzeuge, Projekte? Zwei Sätze reichen
     (`vorwissen`).
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
3. `java tools/Fortschritt.java <name>` ausführen und die Seite **über den lokalen Server**
   öffnen (Abschnitt „Fortschrittsseite live", unten) – nicht per `file://`, sonst kommen
   Klicks nicht an. Ohne Browser-Ansicht: die URL `http://127.0.0.1:8000/fortschritt.html`
   nennen, der Lernende öffnet sie in seinem Browser.
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
5. Beim ersten Start eines Themas fragen: „Was kennst du von <Thema> schon?" – daraus und
   aus `niveau` ergibt sich die Schrittgröße: `anfaenger` kleine Häppchen und jede Lektion
   der Reihe nach; `fortgeschritten` zügiger, Grundlagenlektionen dürfen nach kurzer
   Prüffrage übersprungen werden; `erfahren` Prüffrage zuerst, Lektion nur bei Bedarf.
   Übersprungene Lektionen als `fertig` eintragen, wenn die Prüffrage sitzt.
6. Gibt es eine `notizen:`-Zeile, sie lesen und beim Einstieg berücksichtigen
   („Letztes Mal war Referenz vs. Wert noch wackelig – wir fangen mit einem Beispiel dazu an.").
7. Beim erstmaligen Beginn einer Lektion: Zeile `- NN: begonnen <heute>` eintragen.

## 5. Kernschleife der Lektion

Wiederhole, bis die Ziele der Lektion erreicht sind:

1. **Erklären** – ein Konzept, kurz, mit Beispiel. Leitfaden ist der Lehrplantext; nicht
   vorlesen, sondern erklären.
2. **Prüfen** – eine Verständnisfrage stellen. Antwort abwarten. Bei Fehlern Gegenfrage
   oder kleineres Beispiel, nicht die Lösung.
3. **Üben** (wenn `Übung:` in der Lektion steht):
   - Übung kopieren, **ohne** `loesung/`: Quelle ist der `Übung:`-Pfad aus dem Lehrplan,
     Ziel `arbeit/<thema>/<NN-name>/` ohne das Segment `uebungen/`. Beispiel:
     `cp -r themen/git/uebungen/01-erstes-repo arbeit/git/01-erstes-repo && rm -rf arbeit/git/01-erstes-repo/loesung`
     (kein `rsync` – gibt es unter Windows nicht). Existiert das Ziel schon, nicht
     überschreiben – fragen, ob weiterarbeiten oder neu anfangen.
   - `AUFGABE.md` lesen und in der Unterrichtssprache erklären; Abnahmekriterien nennen.
   - Sagen, wie die Übung in der eingestellten IDE geöffnet wird (IDE-Hinweise in `AGENTS.md`).
   - Warten. Der Lernende schreibt. Erst auf „fertig" oder eine Frage reagieren.
   - Prüfen auf das echte Ergebnis: Java/Gradle/JUnit → `./gradlew test` im Arbeitsordner;
     HTML/CSS/JS → **nicht** per `file://` öffnen (die Browser-Ansicht lädt dann keine
     externen `style.css`/`app.js`), sondern im Arbeitsordner `jwebserver -p 8000` starten
     (im JDK enthalten, Bash im Hintergrund) und `http://localhost:8000/index.html` bzw.
     `tests.html` in der Browser-Ansicht öffnen; `read_page`, `javascript_tool`
     (`getComputedStyle`) und Screenshot; Server danach beenden;
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
2. `notizen:`-Zeile des Themas schreiben oder ersetzen – konkret, für das nächste Mal,
   **in der Unterrichtssprache** (der Lernende sieht sie auf der Fortschrittsseite).
3. `java tools/Fortschritt.java <name>` ausführen, Seite in der Browser-Ansicht öffnen.
4. Erwähnen, dass `arbeit/fortschritt.html` als einzelne Datei an den Ausbilder gehen kann.

## Fortschrittsseite live (Klicks ohne Chat-Eingabe)

Einmal pro Sitzung, beim ersten Anzeigen der Seite:

1. Läuft schon ein Server? `curl -s -o /dev/null -w "%{http_code}" http://127.0.0.1:8000/fortschritt.html`
   → `200` heißt ja. Sonst starten (Bash, `run_in_background`, absoluter Pfad ist Pflicht):
   `jwebserver -p 8000 -b 127.0.0.1 -d "<absoluter Pfad>/arbeit" > "<absoluter Pfad>/arbeit/server.log" 2>&1`
   Port belegt → 8001 usw., URL entsprechend.
2. Monitor scharf schalten (Werkzeug `Monitor`, `persistent: true`, Beschreibung
   „Klicks auf der Fortschrittsseite"):
   `tail -n 0 -f "<absoluter Pfad>/arbeit/server.log" | grep --line-buffered -o 'klick=[^ "]*'`
3. Seite öffnen: `http://127.0.0.1:8000/fortschritt.html` (Browser-Ansicht oder Chrome).

Jedes Monitor-Ereignis ist eine Zeile wie `klick=lernen/java/06` oder
`klick=lernen/sprache/fr`. Sie ist **kein** Nutzertext, sondern ein Klick: URL-dekodieren,
die Segmente nach `klick=` als Argumente von `/lernen` behandeln (`java 06` bzw.
`sprache fr`) und sofort reagieren – „Du hast Java 06 angeklickt – los geht's." Nach jeder
Änderung der Fortschrittsdatei die Seite neu erzeugen; der Browser lädt sie beim nächsten
Aufruf neu (der Lernende drückt F5 oder du sagst es ihm).

Am Ende der Stunde den Server nicht beenden – er stört nicht und die Seite bleibt
bedienbar; beim Schließen der Sitzung endet er ohnehin.

## Fehlerfälle

- `java` fehlt und das Thema ist html/css/javascript: JDK-Installation trotzdem anbieten
  (`jwebserver` und Fortschrittsseite brauchen es), aber nicht erzwingen. Ohne JDK: Der
  Lernende öffnet `index.html` in seinem eigenen Browser und beschreibt, was er sieht;
  Claude prüft die Dateien statisch (`grep`, Lesen). Fortschrittsseite überspringen,
  Textanzeige genügt.
- Fortschrittsdatei ist von Hand kaputt editiert (Parser meldet Fehler): Datei zeigen,
  gemeinsam reparieren, nicht neu anlegen.
- Lernender will eine Lektion überspringen: erlaubt; Status nicht als `fertig` eintragen,
  sondern die nächste als `begonnen`.

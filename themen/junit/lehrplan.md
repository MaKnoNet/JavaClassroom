---
thema: junit
titel: JUnit
voraussetzungen: [java, gradle]
zielgruppe: [azubi, student, kollege]
reihenfolge: 80
---

# JUnit

Tests sind der Beweis, dass Code tut, was er soll – und die Absicherung, dass er es nach
der nächsten Änderung noch tut. JUnit 5 ist unser Test-Framework, Mockito ersetzt
Abhängigkeiten in Unit-Tests; Gradle führt die Tests bei jedem Build aus.

Am Ende kann der Lernende die Testverfahren der Softwareentwicklung einordnen, Tests nach
dem AAA-Muster schreiben, Ausnahmen und Randfälle prüfen, parametrisierte Tests einsetzen,
Abhängigkeiten mit Mocks isolieren und Code so entwerfen, dass er testbar bleibt.

## Lektionen

### 01 Warum testen, erster Test
- **Ziele:** `@Test`, `assertEquals`, Testklasse neben der Klasse; Test in IDE und mit `./gradlew test` ausführen; rot und grün lesen
- **Übung:** uebungen/01-erster-test
- **Prüffrage:** Was ist der Unterschied zwischen einem Test, der fehlschlägt, und einem, der einen Fehler wirft?
- **Übersetzung:** en: Why test, first test | fr: Pourquoi tester, premier test
- **Stufe:** 1

### 02 Testverfahren im Überblick
- **Ziele:** Testverfahren nach vier Dimensionen einordnen – Teststufe (Unit, Integration, System, Akzeptanz), Testmethode (Black-, White-, Grey-Box), Ausführungsart (statisch, dynamisch) und Testziel (funktional, nicht-funktional) – und für eine konkrete Anforderung sagen können, welche Tests auf welcher Ebene sie absichern; dazu TDD und BDD als Vorgehensweisen und Code Review als Ergänzung
- **Prüffrage:** Ein Unit-Test mit Mockito – auf welcher Stufe steht er, ist er Black- oder White-Box, statisch oder dynamisch, funktional oder nicht-funktional? Und warum stehen Unit-Tests unten in der Pyramide und Systemtests oben?
- **Übersetzung:** en: Testing methods at a glance | fr: Les méthodes de test en un coup d'œil
- **Stufe:** 1

Leitfaden: Mit dem ersten Test aus Lektion 01 beginnen und fragen, was er *nicht* prüft
(Zusammenspiel mit anderen Klassen, Datenbank, Oberfläche, Geschwindigkeit). Daraus die
vier Dimensionen entwickeln – jedes Verfahren hat in jeder Dimension einen Platz.

**1. Teststufe – die Testpyramide.** Je weiter unten, desto schneller, isolierter und
zahlreicher.

| Stufe | Prüft | Werkzeug bei uns | Tempo / Anzahl |
|---|---|---|---|
| Unit (Modultest) | kleinste Einheit, Abhängigkeiten durch Mocks/Stubs ersetzt | JUnit, Mockito | Millisekunden, sehr viele |
| Integration | Zusammenspiel mehrerer Komponenten – Klasse + echte Datenbank, Service + Service | JUnit, Spring Test, Testcontainers | Sekunden, einige |
| System (End-to-End) | das Gesamtsystem in testnaher Umgebung, durch die Oberfläche oder API | Playwright, Selenium | Minuten, wenige |
| Akzeptanz (UAT) | erfüllt die Software die fachlichen Anforderungen? – durch Kunde oder Fachseite | BDD-Szenarien, manuelle Abnahme | pro Release |

**2. Testmethode – Sicht auf den Code.** **Black-Box:** Code unbekannt, rein
spezifikationsbasiert (Eingabe → erwartete Ausgabe; manuelle Klick-Tests, Systemtests).
**White-Box (Glass-Box):** voller Blick in den Code, Testfälle decken gezielt Pfade,
Schleifen, Bedingungen ab (typisch Unit-Tests, Coverage in Lektion 10). **Grey-Box:**
Teilwissen über innere Strukturen (Datenmodell, Architektur), getestet von außen über
Schnittstellen (API-Tests).

**3. Art der Ausführung.** **Statisch:** ohne Ausführung – Code Review, Linter,
Compiler, SpotBugs, SonarQube; findet Stil, Sicherheitslücken, tote Pfade.
**Dynamisch:** Code läuft – alle Unit-, Integrations- und Systemtests.

**4. Testziel.** **Funktional** (*was* tut die Software): Regressionstests
(Wiederholung nach Änderungen – jeder automatisierte Test wird dazu), Smoke-Tests
(läuft der Build überhaupt?), Akzeptanztests. **Nicht-funktional** (*wie gut*):
Last-/Performancetests (JMeter, Gatling), Stresstests (bewusst überlasten – wann kippt
es, erholt es sich?), Security-/Penetrationstests (OWASP, Dependency-Scan),
Barrierefreiheit und Usability.

**Ergänzende Verfahren**, die quer dazu liegen: **Mutationstest** (PIT verfälscht den
Code absichtlich – schlagen die Tests an?), **Property-based Testing** (jqwik:
Eigenschaften statt Beispiele, Eingaben werden generiert), **exploratives Testen**
(Mensch mit Neugier, für das, was niemand vorhergesehen hat). Vorgehensweisen: **TDD**
(Test zuerst, Lektion 09) und **BDD** (Given/When/Then, lesbar für die Fachseite).

Abschluss: Ein Login-Formular gemeinsam durch alle vier Dimensionen sortieren – welcher
Test steht wo, und welche Lücke bleibt, wenn eine Ebene fehlt.

### 03 Assertions und Testaufbau
- **Ziele:** Arrange – Act – Assert; `assertTrue`, `assertNull`, `assertAll`; sprechende Testnamen; ein Verhalten pro Test
- **Prüffrage:** Warum ist `testAdd()` ein schlechter Name und `addiertZweiPositiveZahlen()` ein guter?
- **Übersetzung:** en: Assertions and test structure | fr: Assertions et structure d'un test
- **Stufe:** 1

### 04 Ausnahmen und Randfälle
- **Ziele:** `assertThrows`; leere Eingaben, `null`, Grenzwerte; Tests als Spezifikation
- **Prüffrage:** Welche drei Randfälle prüft man bei einer Methode, die eine Liste entgegennimmt?
- **Übersetzung:** en: Exceptions and edge cases | fr: Exceptions et cas limites
- **Stufe:** 1

### 05 Parametrisierte Tests
- **Ziele:** `@ParameterizedTest` mit `@ValueSource` und `@CsvSource`; Wiederholung in Tests vermeiden
- **Übung:** uebungen/02-parametrisierte-tests
- **Prüffrage:** Wann lohnt ein parametrisierter Test, wann nicht?
- **Übersetzung:** en: Parameterized tests | fr: Tests paramétrés
- **Stufe:** 2

### 06 Lebenszyklus und Fixtures
- **Ziele:** `@BeforeEach`, `@AfterEach`, `@BeforeAll`; Testdaten aufbauen; Tests unabhängig voneinander halten
- **Prüffrage:** Warum darf ein Test nicht vom Ergebnis eines anderen abhängen?
- **Übersetzung:** en: Lifecycle and fixtures | fr: Cycle de vie et fixtures
- **Stufe:** 2

### 07 Testbarkeit und Abhängigkeiten
- **Ziele:** Abhängigkeiten über Interfaces hereingeben (Dependency Inversion); einfache Test-Doubles von Hand; warum `new` in der Mitte der Logik Tests schwer macht
- **Prüffrage:** Wie testet man eine Klasse, die die aktuelle Uhrzeit braucht?
- **Übersetzung:** en: Testability and dependencies | fr: Testabilité et dépendances
- **Stufe:** 2

### 08 Mocking mit Mockito
- **Ziele:** `mock()`, `when(...).thenReturn(...)`, `verify(...)`, `never()`, `ArgumentMatchers`; Unterschied Mock, Stub, Spy, Fake; was man mockt (fremde Schnittstellen, langsame oder nicht deterministische Dinge) und was nicht (die Klasse unter Test, Wertobjekte)
- **Übung:** uebungen/03-mocking
- **Prüffrage:** Warum mockt man die Datenbankschnittstelle, aber nicht die Klasse, die man gerade testet?
- **Übersetzung:** en: Mocking with Mockito | fr: Mocking avec Mockito
- **Stufe:** 2

Leitfaden: Erst mit dem handgeschriebenen Test-Double aus Lektion 07 beginnen und
zeigen, wie viel Boilerplate es ist; dann dieselbe Klasse mit Mockito. Faustregel: Ein
Mock **antwortet** (Stub, `when`) oder **bezeugt** (`verify`) – wer beides in jedem
Test braucht, testet vermutlich zu viel auf einmal. Warnzeichen für Über-Mocking:
Tests, die bei jedem Refactoring brechen, obwohl das Verhalten gleich blieb.

### 09 TDD im Kleinen
- **Ziele:** Rot → Grün → Refactor an einem kleinen Beispiel durchlaufen
- **Prüffrage:** Warum schreibt man den Test, bevor der Code existiert?
- **Übersetzung:** en: TDD in the small | fr: TDD au quotidien
- **Stufe:** 2

### 10 Coverage und Mutationstest
- **Ziele:** JaCoCo-Bericht öffnen, Zeilen- und Zweigabdeckung verstehen, Lücken finden; Coverage ist Hinweis, kein Ziel. Mutationstest mit PIT: Code wird absichtlich verfälscht – überlebende Mutanten zeigen, was die Tests nicht prüfen
- **Übung:** uebungen/06-mutationstest
- **Prüffrage:** Warum beweist 100 % Coverage nicht, dass der Code richtig ist – und was sagt ein Mutation Score von 60 % bei 100 % Coverage?
- **Übersetzung:** en: Coverage and mutation testing | fr: Couverture et tests de mutation
- **Stufe:** 3

### 11 Tests im Repository und in der Pipeline
- **Ziele:** Wissen, wo welcher Test lebt und wer ihn startet: Unit-Tests in `src/test/java` bei jedem Commit; Integrationstests (`*IT`, Testcontainers) vor dem Deployment in die Testumgebung; statische Analyse (Checkstyle, SpotBugs, ArchUnit) als Build-Abbruch; API- und Vertragstests (RestAssured, Pact) gegen Schnittstellen; E2E (Playwright, Selenium) und Lasttests (Gatling, JMeter) als Code im Repo, ausgeführt gegen eine bereitgestellte Umgebung; außerhalb des Repos: manuelle Abnahme (UAT) auf Staging, zentrales Security-Scanning (SonarQube, Snyk)
- **Übung:** uebungen/05-api-test
- **Prüffrage:** Warum laufen Unit-Tests bei jedem Commit, E2E-Tests aber erst gegen eine Testumgebung – und was passiert, wenn man das umdreht?
- **Übersetzung:** en: Tests in the repository and the pipeline | fr: Les tests dans le dépôt et le pipeline
- **Stufe:** 2

Leitfaden: Die Pipeline als Treppe zeichnen – Commit → Build mit Unit-Tests und
statischer Analyse → Deployment auf Test → Integrations-, API- und E2E-Tests → Staging →
UAT und Security-Scan → Produktion. Jede Stufe filtert, was die vorherige nicht sehen
konnte; jede ist teurer und langsamer als die vorherige. Was **nicht** im Repo liegt
(UAT, zentrale Scanner), trotzdem benennen: Der Entwickler sieht deren Ergebnisse als
Ticket oder Bericht, nicht als roten Build.

### 12 Architekturregeln als Test
- **Ziele:** Mit ArchUnit Abhängigkeitsregeln zwischen Paketen und Schichten als JUnit-Test festhalten; Regeln laufen bei jedem Build statt im Kopf des Reviewers
- **Übung:** uebungen/04-archunit
- **Prüffrage:** Warum ist „service darf nicht von ui abhängen" als Test wertvoller als als Satz im Wiki?
- **Übersetzung:** en: Architecture rules as tests | fr: Règles d'architecture sous forme de tests
- **Stufe:** 3

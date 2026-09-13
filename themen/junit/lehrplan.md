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
- **Übersetzung:** en: Why test, first test | fr: Pourquoi tester, premier test

### 02 Assertions und Testaufbau
- **Ziele:** Arrange – Act – Assert; `assertTrue`, `assertNull`, `assertAll`; sprechende Testnamen; ein Verhalten pro Test
- **Prüffrage:** Warum ist `testAdd()` ein schlechter Name und `addiertZweiPositiveZahlen()` ein guter?
- **Übersetzung:** en: Assertions and test structure | fr: Assertions et structure d'un test

### 03 Ausnahmen und Randfälle
- **Ziele:** `assertThrows`; leere Eingaben, `null`, Grenzwerte; Tests als Spezifikation
- **Prüffrage:** Welche drei Randfälle prüft man bei einer Methode, die eine Liste entgegennimmt?
- **Übersetzung:** en: Exceptions and edge cases | fr: Exceptions et cas limites

### 04 Parametrisierte Tests
- **Ziele:** `@ParameterizedTest` mit `@ValueSource` und `@CsvSource`; Wiederholung in Tests vermeiden
- **Übung:** uebungen/02-parametrisierte-tests
- **Prüffrage:** Wann lohnt ein parametrisierter Test, wann nicht?
- **Übersetzung:** en: Parameterized tests | fr: Tests paramétrés

### 05 Lebenszyklus und Fixtures
- **Ziele:** `@BeforeEach`, `@AfterEach`, `@BeforeAll`; Testdaten aufbauen; Tests unabhängig voneinander halten
- **Prüffrage:** Warum darf ein Test nicht vom Ergebnis eines anderen abhängen?
- **Übersetzung:** en: Lifecycle and fixtures | fr: Cycle de vie et fixtures

### 06 Testbarkeit und Abhängigkeiten
- **Ziele:** Abhängigkeiten über Interfaces hereingeben (Dependency Inversion); einfache Test-Doubles von Hand; warum `new` in der Mitte der Logik Tests schwer macht
- **Prüffrage:** Wie testet man eine Klasse, die die aktuelle Uhrzeit braucht?
- **Übersetzung:** en: Testability and dependencies | fr: Testabilité et dépendances

### 07 TDD im Kleinen
- **Ziele:** Rot → Grün → Refactor an einem kleinen Beispiel durchlaufen
- **Prüffrage:** Warum schreibt man den Test, bevor der Code existiert?
- **Übersetzung:** en: TDD in the small | fr: TDD au quotidien

### 08 Coverage lesen
- **Ziele:** JaCoCo-Bericht öffnen, Zeilen- und Zweigabdeckung verstehen, Lücken finden; Coverage ist Hinweis, kein Ziel
- **Prüffrage:** Warum beweist 100 % Coverage nicht, dass der Code richtig ist?
- **Übersetzung:** en: Reading coverage | fr: Lire la couverture

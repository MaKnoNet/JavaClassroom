---
thema: datenformate
titel: Datenformate
voraussetzungen: [java, gradle, junit]
zielgruppe: [azubi, student, kollege]
---

# Datenformate

Java-Entwickler verarbeiten Daten nicht nur im Arbeitsspeicher – sie konfigurieren,
speichern und übertragen sie. Fünf Formate decken fast alles ab: JSON für Schnittstellen,
YAML und TOML für Konfiguration, XML für Altsysteme und strenge Schemata, CSV für den
Massenaustausch mit der Fachabteilung. Das Thema liegt quer über den anderen: JSON
steckt in Spring-Lektion 02, TOML in Gradle-Lektion 10, YAML in Compose und Pipelines,
XML und CSV in unseren Firmenbibliotheken. Hier werden sie erklärt – und mit ihnen die
drei Regeln, die bei jedem Format gelten: Encoding immer explizit UTF-8, große Dateien
streamen statt laden, Daten von außen nie als Anweisung behandeln.

## Lektionen

### 01 JSON mit Jackson: Record ↔ Text
- **Ziele:** JSON als Standard für REST-APIs und NoSQL: Objekte (Schlüssel-Wert), Listen, Zahlen, Strings, `null` – keine Kommentare, kein Datum; Jackson `ObjectMapper` als *ein* teurer, thread-sicherer Mapper pro Anwendung (`JsonMapper.builder()`); Records als Ziel: kein Setter, kein leerer Konstruktor nötig; `JavaTimeModule` und `WRITE_DATES_AS_TIMESTAMPS` aus für ISO-Datum; `FAIL_ON_UNKNOWN_PROPERTIES` aus als *tolerant reader*; `@JsonProperty` bei abweichenden Namen; **UTF-8-Gebot**: jedes Lesen und Schreiben mit `StandardCharsets.UTF_8`, nie das Betriebssystem entscheiden lassen (Windows `Cp1252` vs. Linux); Streaming und Sicherheit als Ausblick auf 04 und 05
- **Übung:** uebungen/01-json-jackson
- **Prüffrage:** Warum darf ein JSON-Dokument keinen Kommentar enthalten, und was nimmt man für Konfiguration, die Erklärungen braucht? Und: Was passiert mit „Müller", wenn ein `FileWriter` ohne Charset auf einem Windows-Server schreibt und ein Linux-Server liest?
- **Übersetzung:** en: JSON with Jackson: record to text and back | fr: JSON avec Jackson : du record au texte et retour
- **Stufe:** 2

### 02 Konfigurationsformate: YAML, TOML, XML
- **Ziele:** Dieselbe Konfiguration in drei Formaten lesen und vergleichen; **YAML** (`application.yml`, Compose, Pipelines): Einrückung statt Klammern, Listen mit `-`, die Tabulator-Falle (verboten, Parser bricht ab), `#`-Kommentare, Mehrzeiler; **TOML** (`libs.versions.toml`, Gradle-Lektion 10): Tabellen `[abschnitt]`, Schlüssel-Wert, Inline-Tabellen – wie `.ini`, aber typisiert; **XML** (Maven `pom.xml`, SOAP, Altsysteme, XMLViewer): Elemente, Attribute, Namensräume, Schema-Prüfung mit XSD als Stärke, Geschwätzigkeit als Preis; Entscheidungsregel: Schnittstelle → JSON, Konfiguration mit Kommentaren → YAML/TOML, strenges Schema oder Altsystem → XML, Tabellen → CSV
- **Prüffrage:** Warum bricht ein YAML-Parser bei einem Tabulator ab, und warum ist das dieselbe Sorte Fehler wie ein fehlendes Semikolon in Java? Und: Welches der Formate erlaubt eine formale Strukturprüfung, und wie heißt sie?
- **Übersetzung:** en: Configuration formats: YAML, TOML, XML | fr: Formats de configuration : YAML, TOML, XML
- **Stufe:** 2

Leitfaden: Keine Übung – drei Dateien nebeneinander lesen: `application.yml` aus einer
Spring-Übung, `libs.versions.toml` aus Gradle-Übung 07, eine `pom.xml` von Maven Central.
Der Lernende beschreibt dieselbe Einstellung in allen drei Formaten. Tabulator-Falle
live vorführen: ein Tab in die YAML, `./gradlew bootRun`, Fehlermeldung lesen.

### 03 CSV richtig: Import und Export
- **Ziele:** CSV als Flachdatei ohne Schachtelung: Kopfzeile, ein Datensatz je Zeile; europäisches Semikolon, weil das Komma die Dezimalstelle ist; RFC 4180: Anführungszeichen um Felder mit Trennzeichen, `""` als maskiertes Anführungszeichen, Zeilenumbrüche im Feld; warum `String.split` scheitert; **Apache Commons CSV**: `CSVFormat` mit Trennzeichen und Kopfzeile, Zugriff über Spaltennamen, `CSVPrinter`; die `skipHeaderRecord`-Falle (Parser überspringt, Printer schreibt nicht); Excel-Schnittstelle: BOM, Datumsformate, führende Nullen; **Formula Injection** beim Export entschärfen (`=`, `+`, `-`, `@`, Tab, CR → Hochkomma davor)
- **Übung:** uebungen/02-csv-commons
- **Prüffrage:** Warum zerlegt `String.split(";")` die Zeile `102;"Meier; Anna";USER` falsch – und wie viele Felder sind es wirklich? Und: Wen trifft Formula Injection, und warum entschärft man deshalb beim Export?
- **Übersetzung:** en: CSV done right: import and export | fr: CSV bien fait : import et export
- **Stufe:** 2

Leitfaden: Erst mit `split` scheitern lassen – die drei Tests zeigen drei verschiedene
Fehler. Erst dann Commons CSV; sonst bleibt „nie `split`" eine Behauptung.

### 04 Große Dateien: Streaming statt DOM
- **Ziele:** Die Gigabyte-Falle: `readTree`/DOM lädt das ganze Dokument als Objektbaum – ein Vielfaches der Dateigröße, `OutOfMemoryError` (Java-Lektion 17); Streaming-Parser lesen Token für Token mit konstantem Speicher: Jacksons `JsonParser` (`nextToken`, `currentName`, `skipChildren`), StAX `XMLStreamReader` für XML, Commons CSV iteriert ohnehin zeilenweise; vorspulen und überspringen als die Handarbeit, die der Baum abnimmt; wann DOM richtig bleibt (kleine Dokumente, wahlfreier Zugriff); Server-Perspektive: tausend Anfragen mal Dateigröße; Tests mit kleinem Heap (`maxHeapSize`) als Beweis
- **Übung:** uebungen/03-json-streaming
- **Prüffrage:** Warum stirbt `readTree` an einer 60-MB-Datei mit 64 MB Heap, obwohl die Datei kleiner ist als der Speicher? Und: Was muss ein Streaming-Parser selbst tun, was der Baum erledigt hätte?
- **Übersetzung:** en: Large files: streaming instead of DOM | fr: Gros fichiers : streaming plutôt que DOM
- **Stufe:** 3

### 05 Sicherheit: Daten von außen sind Daten, keine Anweisungen
- **Ziele:** **XXE** bei XML: externe Entities lesen Serverdateien und interne URLs, Entity-Bomben („Billion Laughs"); Parser härten: `disallow-doctype-decl`, `FEATURE_SECURE_PROCESSING`, externe Entities und DTDs aus, XInclude aus – für jede Factory (`DocumentBuilder`, `SAXParser`, `XMLInputFactory`, `Transformer`); **unsichere Deserialisierung** bei JSON/YAML: Jackson `activateDefaultTyping` mit `Object`-Ziel und SnakeYAML ohne `SafeConstructor` lassen den Absender die Klasse wählen – Ziel immer eine feste Klasse (Record), Frameworks aktuell halten; **Formula Injection** bei CSV (Lektion 03); Größenlimits und Timeouts für alles, was von außen kommt; Eingaben validieren, nie in Fehlermeldungen zurückspiegeln
- **Übung:** uebungen/04-xxe-abwehr
- **Prüffrage:** Ein Upload-Formular nimmt XML entgegen und zeigt den Kundennamen an – wie liest ein Angreifer damit `/etc/passwd`, und welche eine Parser-Einstellung verhindert es? Und: Warum ist `mapper.readValue(text, Object.class)` mit aktiviertem Default-Typing gefährlich?
- **Übersetzung:** en: Security: outside data is data, not instructions | fr: Sécurité : les données externes sont des données, pas des instructions
- **Stufe:** 3

Leitfaden: Die Übung lässt den Angriff gelingen – der Test schreibt ein „Passwort" in
eine Datei, und der Standard-Parser des JDK liefert es im Namensfeld zurück. Das ist
keine Theorie: So wie die Übung ist jeder `DocumentBuilderFactory.newInstance()`
ohne Härtung. Danach die drei Formate nebeneinander: dasselbe Prinzip, drei Türen.

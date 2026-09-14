---
thema: patterns
titel: Entwurfsmuster
voraussetzungen: [java, uml]
zielgruppe: [anfaenger, fortgeschritten, erfahren]
reihenfolge: 85
---

# Entwurfsmuster

Entwurfsmuster sind bewährte Antworten auf Probleme, die in jeder Codebasis wiederkehren:
Verhalten austauschen, ohne den Aufrufer zu ändern; Objekte über Ereignisse entkoppeln;
Objekte mit vielen Einstellungen lesbar erzeugen; Zustandsautomaten ohne if-Kaskaden.
Sie sind zuerst ein **Vokabular** – „das ist eine Strategie" sagt in drei Worten, was
sonst eine Seite Erklärung braucht – und erst dann Code. Deshalb steht jedes Muster hier
als Problem, als UML-Klassendiagramm und als Java.

Am Ende erkennt der Lernende wiederkehrende Entwurfsprobleme, kennt die wichtigsten Muster
mit Namen, Struktur und Einsatzfall und kann sie in Java umsetzen – ohne sie dort
einzubauen, wo sie nichts lösen. Er liest Muster in fremdem Code und im UML-Diagramm und
kann begründen, warum ein Muster gewählt wurde und was es kostet.

Jede Übung ist ein Gradle-Projekt mit JUnit-Tests: Der Startcode funktioniert, ist aber
starr oder verwickelt; die Tests verlangen die Eigenschaft, die das Muster bringt
(Erweiterbarkeit ohne Änderung, Entkopplung, Rücknahme). Lektion 01 ist Stufe 1 und
holt Grundlagen nach, die für alles Weitere sitzen müssen. Voraussetzungen: Java bis
Lektion 11 (Interfaces, Polymorphie, Dependency Injection) und das Lesen von
UML-Klassendiagrammen aus dem Thema UML.

## Lektionen

### 01 Das Fundament: Kopplung und Interfaces
- **Ziele:** Warum starre Abhängigkeiten Code schwer testbar und erweiterbar machen – das Schlüsselwort `new` mitten in einer Klasse bindet sie an genau eine Implementierung; der Unterschied zwischen Vererbung („ist ein") und Komposition („hat ein") und warum Komposition in der Praxis fast immer gewinnt (*favor composition over inheritance*): Unterklassen je Variante explodieren, zusammengesetzte Objekte kombinieren sich; „gegen ein Interface programmieren, nicht gegen eine Implementierung"; Dependency Injection von Hand – die Abhängigkeit kommt per Konstruktor hinein (Java-Lektion 11); lose Kopplung als das Ziel, für das alle folgenden Muster die Formen liefern; Klassendiagramm: durchgezogener Pfeil mit Dreieck (Vererbung) vs. Raute (Komposition) vs. gestrichelt (Realisierung)
- **Übung:** uebungen/01-kopplung-aufheben
- **Prüffrage:** Eine Klasse `Auto` erzeugt intern per `new BenzinMotor()` ihren eigenen Antrieb – warum ist das eine Sackgasse, wenn das Auto morgen einen `Elektromotor` bekommen soll, und wie löst ein Interface das? Und: Was unterscheidet `ElektroAuto extends Auto` von `new Auto(new Elektromotor())` – und warum gewinnt die zweite Form, sobald ein Hybrid dazukommt?
- **Übersetzung:** en: The foundation: coupling and interfaces | fr: Les fondations : couplage et interfaces
- **Stufe:** 1

Leitfaden: Diese Lektion holt den Lernenden bei Java-Lektion 11 ab und zeigt das
Problem, das alle Muster überhaupt erst lösen: harte Kopplung durch `new`. Der Startcode
der Übung hat beides – `new BenzinMotor()` im Auto und eine Unterklasse `ElektroAuto`, die
den Motor per Überschreiben tauscht. Der Test verlangt ein Hybridauto: mit Vererbung nur
per Kopie zu haben, mit Komposition ein Konstruktoraufruf. Wer Java 11 sicher kann, ist
hier per Prüffrage in zwei Minuten durch – das ist gewollt.

### 02 Warum Muster: Probleme, Vokabular, Kategorien
- **Ziele:** Was ein Entwurfsmuster ist – ein benanntes Problem-Lösungs-Paar, keine Bibliothek und kein Rezept; die Herkunft (Gang of Four, 1994) und die drei Kategorien Erzeugung, Struktur, Verhalten; Muster vs. Prinzipien: SOLID, DRY und Dependency Inversion (Java-Lektion 11) sind die Gründe, Muster die Formen; das Klassendiagramm als Sprache der Muster – Interface, Vererbung, Komposition, Abhängigkeit auf einen Blick; die Kostenfrage: jedes Muster fügt Klassen und Indirektion hinzu, gerechtfertigt nur durch ein echtes Problem; Muster erkennen, die schon da sind: `Iterator` in jeder `for`-Schleife, `Comparator` als Strategie, Listener als Beobachter
- **Prüffrage:** Warum ist „wir bauen das gleich als Strategie, falls wir es mal brauchen" ein Fehler – und woran erkennst du, dass ein Muster wirklich gebraucht wird? Und: Welches Muster nutzt du jedes Mal, wenn du `for (String s : liste)` schreibst?
- **Übersetzung:** en: Why patterns: problems, vocabulary, categories | fr: Pourquoi les patrons : problèmes, vocabulaire, catégories
- **Stufe:** 2

Leitfaden: Keine Übung – mit einem Stück Code aus einer früheren Übung beginnen (der
Getränkeautomat aus Java 07 oder der Rechner aus Java 02) und fragen: „Was müsste sich
ändern, wenn morgen eine dritte Variante dazukommt?" Die Antwort „an drei Stellen" ist das
Problem, das die nächsten Lektionen lösen. Die Kostenfrage von Anfang an mitführen –
Muster-Overkill ist der häufigere Fehler als fehlende Muster.

### 03 Strategie: Verhalten austauschen
- **Ziele:** Das Problem: ein `switch` über einen Typ, der bei jeder neuen Variante wächst (Versandart, Rabatt, Steuersatz); die Lösung: ein Interface für das Verhalten, eine Klasse je Variante, der Kontext kennt nur das Interface; **Open/Closed** – offen für Erweiterung (neue Klasse), geschlossen für Änderung (Kontext bleibt); Strategie als Lambda, wenn das Interface eine Methode hat (`Comparator`, `Function`); Auswahl der Strategie: Konstruktor-Injection, Map von Schlüssel auf Strategie, Enum mit Verhalten; Test: jede Strategie einzeln, der Kontext mit einer Attrappe; Klassendiagramm mit `<<interface>>` und gestricheltem Realisierungspfeil
- **Übung:** uebungen/02-strategie
- **Prüffrage:** Woran erkennst du im Code, dass eine Strategie fehlt? Und: Was ist der Unterschied zwischen Strategie und einer `if`-Kette mit derselben Wirkung, wenn morgen die vierte Variante kommt?
- **Übersetzung:** en: Strategy: swapping behaviour | fr: Stratégie : échanger un comportement
- **Stufe:** 2

Leitfaden: Der Test der Übung fügt eine Versandart hinzu, die der Startcode nicht kennt –
und darf den Kontext dafür nicht anfassen. Das ist Open/Closed als Test, nicht als Satz.
`Comparator` zeigen: Der Lernende hat schon Strategien geschrieben, ohne den Namen zu
kennen.

### 04 Beobachter: Ereignisse statt Aufrufe
- **Ziele:** Das Problem: ein Objekt muss andere über Änderungen informieren, ohne sie zu kennen (Lagerbestand → Anzeige, Bestellung, Protokoll); die Lösung: Subjekt mit Liste von Beobachtern, `registriere`/`entferne`/`benachrichtige`, Beobachter-Interface; Push (Daten im Ereignis) vs. Pull (Beobachter fragt nach); Reihenfolge und Fehler in einem Beobachter – warum das Subjekt weiterlaufen muss; Speicherleck durch vergessene Abmeldung (Java-Lektion 18); wo es überall steckt: DOM-Events (JavaScript 04), Vaadin-Listener, Spring `ApplicationEvent`, `PropertyChangeListener`; Beobachter als Lambda; Test mit einem aufzeichnenden Beobachter statt Mockito
- **Übung:** uebungen/03-beobachter
- **Prüffrage:** Warum darf das Lager die Klasse `Anzeige` nicht importieren, und was gewinnt man dadurch? Und: Was passiert mit einem Beobachter, der sich nie abmeldet – und warum merkt man das erst im Betrieb?
- **Übersetzung:** en: Observer: events instead of calls | fr: Observateur : des événements plutôt que des appels
- **Stufe:** 2

Leitfaden: Das Klassendiagramm zeigt den Pfeil, der *fehlt*: vom Lager zur Anzeige. Genau
diese fehlende Abhängigkeit ist das Muster. Den Bezug zu `addEventListener` aus
JavaScript herstellen – dasselbe Muster, andere Sprache.

### 05 Erzeugung: Fabrikmethode und Builder
- **Ziele:** Das Problem: `new` an vielen Stellen bindet an konkrete Klassen und verteilt Erzeugungswissen; **statische Fabrikmethode** (`List.of`, `LocalDate.of`, `Optional.empty`) – sprechender Name, Rückgabe eines Interfaces, Wiederverwendung von Instanzen; **Fabrikmethode** im engeren Sinn: Unterklasse entscheidet, welches Produkt entsteht; **Builder** für Objekte mit vielen optionalen Werten – lesbar, unveränderliches Ergebnis, Validierung in `build()`; `HttpRequest.newBuilder()` und `StringBuilder` als Vorbilder; wann ein Record reicht (wenige Pflichtwerte) und wann der Builder lohnt (ab etwa vier optionalen); Telescoping-Constructor als Anti-Muster
- **Übung:** uebungen/04-builder
- **Prüffrage:** Warum ist `new Bestellung(kunde, null, null, true, 0, null)` ein Warnsignal, und was macht ein Builder daran besser? Und: Was unterscheidet eine statische Fabrikmethode von einem Konstruktor – nenne zwei Dinge, die nur sie kann?
- **Übersetzung:** en: Creation: factory method and builder | fr: Création : fabrique et builder
- **Stufe:** 2

Leitfaden: Mit dem Telescoping-Constructor aus dem Startcode beginnen und laut vorlesen
lassen – niemand weiß, was das vierte `null` bedeutet. Der Builder ist die Antwort auf
Lesbarkeit, nicht auf Flexibilität; das unterscheidet ihn von der Fabrik.

### 06 Singleton – und warum meist nicht
- **Ziele:** Das Muster: genau eine Instanz, globaler Zugriff, privater Konstruktor; die Umsetzungen (eager, lazy, Enum) und die Thread-Sicherheit (Java-Lektion 18); warum es das meistgehasste Muster ist: versteckte Abhängigkeit (nichts im Konstruktor verrät sie), globaler veränderlicher Zustand, Tests können ihn nicht austauschen, Reihenfolgeabhängigkeit beim Start; die Alternative: eine Instanz, aber **injiziert** – der Spring-Container ist genau das (Spring-Lektion 01, Scope Singleton ohne die Nachteile); wann es legitim bleibt: zustandslose Werkzeuge, Konfiguration beim Start, Enum-Konstanten
- **Übung:** uebungen/05-singleton-abloesen
- **Prüffrage:** Warum lässt sich eine Klasse, die `Konfiguration.getInstance()` aufruft, nicht mit einer Testkonfiguration testen – und wie sieht dieselbe Klasse aus, wenn man sie testbar macht? Und: Was macht Spring anders, obwohl auch dort jede Bean nur einmal existiert?
- **Übersetzung:** en: Singleton – and why usually not | fr: Singleton – et pourquoi souvent non
- **Stufe:** 2

Leitfaden: Die Übung dreht das Muster um: Der Startcode hat ein Singleton, der Test kommt
nicht daran vorbei, die Lösung injiziert. Das ist Dependency Injection aus Java 11 mit
neuem Gegner. Nicht verteufeln – erklären, welches Problem es lösen wollte und warum die
Antwort heute anders aussieht.

### 07 Struktur: Dekorierer und Adapter
- **Ziele:** **Dekorierer**: Verhalten hinzufügen, ohne die Klasse zu ändern oder zu erben – dasselbe Interface, ein umhülltes Objekt, Aufruf weiterreichen und ergänzen; Kombinierbarkeit (Protokollierung + Caching + Wiederholung) gegen Vererbungsexplosion; `java.io` als Vorbild (`BufferedReader(new FileReader(…))`); **Adapter**: eine vorhandene Klasse an ein erwartetes Interface anpassen, ohne sie zu ändern – der Weg, fremde Bibliotheken hinter eigene Schnittstellen zu legen; Unterschied: Dekorierer behält das Interface und ergänzt, Adapter wechselt es; Klassendiagramm: beide „haben" das umhüllte Objekt (Komposition), nur der Dekorierer implementiert zusätzlich dessen Interface
- **Übung:** uebungen/06-dekorierer
- **Prüffrage:** Warum ist `new PufferndeProtokollierendeDatei` als Unterklasse eine Sackgasse, und wie lösen Dekorierer das? Und: Woran erkennst du im Diagramm, ob ein Adapter oder ein Dekorierer vor dir steht?
- **Übersetzung:** en: Structure: decorator and adapter | fr: Structure : décorateur et adaptateur
- **Stufe:** 2

Leitfaden: `new BufferedReader(new InputStreamReader(System.in))` aus Java-Lektion 03 ist
der Dekorierer, den jeder Lernende schon geschrieben hat. Von dort aus ist die Übung ein
kurzer Weg. Adapter nur an einem Beispiel – der Unterschied zum Dekorierer ist die
Prüffrage.

### 08 Verhalten: Schablonenmethode und Kommando
- **Ziele:** **Schablonenmethode**: ein Algorithmus mit festem Ablauf und austauschbaren Schritten – abstrakte Oberklasse mit `final` Ablaufmethode und abstrakten Schritten; Hollywood-Prinzip („wir rufen dich"); die Grenze: Vererbung bindet fest, Strategie per Komposition ist oft die bessere Wahl (Java-Lektion 10); **Kommando**: eine Aktion als Objekt – `ausfuehren()` und `rueckgaengig()`, Warteschlange, Protokoll, Undo/Redo; Kommandos als Lambdas, wenn kein Undo gebraucht wird; wo es steckt: `Runnable`, Menüaktionen, Datenbank-Transaktionen als Liste von Kommandos
- **Übung:** uebungen/07-kommando
- **Prüffrage:** Warum ist die Ablaufmethode in der Schablonenmethode `final`, und was ginge kaputt, wenn sie es nicht wäre? Und: Was muss ein Kommando speichern, damit `rueckgaengig()` funktioniert – und warum reicht ein Lambda dafür nicht?
- **Übersetzung:** en: Behaviour: template method and command | fr: Comportement : patron de méthode et commande
- **Stufe:** 3

Leitfaden: Die Übung ist ein Texteditor mit Undo – der Startcode ändert den Text direkt,
der Test verlangt, dass drei Schritte rückgängig gehen. Wer versucht, den alten Text
irgendwo zwischenzuspeichern, landet beim Kommando von selbst.

### 09 Zustand: Automaten ohne if-Kaskaden
- **Ziele:** Das Problem: ein Objekt verhält sich je nach Zustand anders, und jede Methode beginnt mit `if (zustand == …)` (Bestellung: neu, bezahlt, versandt, storniert); die Lösung: ein Interface je Zustand, das Objekt delegiert an den aktuellen Zustand, Übergänge liegen in den Zustandsklassen; unerlaubte Übergänge werden Exceptions statt vergessener `else`-Zweige; Zustandsdiagramm (UML) als Vorlage für die Klassen – jeder Kreis eine Klasse, jeder Pfeil eine Methode; Enum mit Verhalten als leichte Variante; Bezug: der Getränkeautomat aus Java 07, Spring Statemachine als Ausblick
- **Übung:** uebungen/08-zustand
- **Prüffrage:** Warum ist eine Bestellung, die in jeder Methode `if (status == VERSANDT) throw …` prüft, schwer zu erweitern – und wo landet diese Prüfung beim Zustandsmuster? Und: Wie liest du aus einem Zustandsdiagramm ab, welche Methoden das Zustands-Interface braucht?
- **Übersetzung:** en: State: automata without if cascades | fr: État : des automates sans cascades de if
- **Stufe:** 3

Leitfaden: Erst das Zustandsdiagramm auf Papier, dann die Klassen. Der Test der Übung
verlangt einen neuen Zustand („retourniert"), und der Startcode zwingt dafür zu Änderungen
in vier Methoden – die Lösung braucht eine neue Klasse und einen Übergang.

### 10 Fassade und Proxy: Schnittstellen vereinfachen und abschirmen
- **Ziele:** **Fassade**: eine einfache Schnittstelle vor ein kompliziertes Subsystem (fünf Klassen für „Bestellung aufgeben" werden eine Methode); Fassade als Grenze zwischen Schichten, Service-Klassen in Spring sind Fassaden; **Proxy**: derselbe Typ, aber mit Kontrolle davor – Zugriffsschutz, Lazy Loading, Caching, Protokollierung; wie Spring `@Transactional` und `@CircuitBreaker` umsetzt (Proxy um die Bean – und warum der Aufruf aus derselben Klasse daran vorbeigeht, Spring-Lektionen 04 und 07); JPA-Lazy-Proxies (Spring-Lektion 06); Abgrenzung zu Dekorierer und Adapter in einem Satz je Muster
- **Prüffrage:** Warum greift `@Transactional` nicht, wenn eine Methode eine andere annotierte Methode derselben Klasse aufruft – und welches Muster erklärt das? Und: Worin unterscheiden sich Proxy und Dekorierer, wenn beide dasselbe Interface implementieren und ein Objekt umhüllen?
- **Übersetzung:** en: Facade and proxy: simplifying and shielding interfaces | fr: Façade et proxy : simplifier et protéger les interfaces
- **Stufe:** 3

Leitfaden: Keine Übung – die Proxy-Lektion ist die Erklärung für ein Phänomen, das jeder
Spring-Entwickler einmal erlebt (Selbstaufruf ohne Transaktion). Wer Spring 04 kennt,
bekommt hier die Antwort; wer es nicht kennt, merkt sie sich für später.

### 11 Muster erkennen, Muster vermeiden
- **Ziele:** Muster im JDK und in Spring lesen: `Iterator`, `Comparator` (Strategie), `InputStream`-Ketten (Dekorierer), `HttpRequest.newBuilder()` (Builder), `JdbcTemplate` (Schablonenmethode), `ApplicationEventPublisher` (Beobachter), `@Transactional` (Proxy); Refactoring zu Mustern als Weg – Muster entstehen aus Code, der wehtut, nicht am Reißbrett (Java-Lektion 19); Anti-Muster: Gottklasse, Muster-Overkill (Fabrik für eine Klasse, Strategie mit einer Variante), Singleton-Sucht, anämisches Modell; die Frage vor jedem Muster: „Welche Änderung erwarte ich, und macht das Muster sie billiger?"; Muster benennen im Code Review und im Klassendiagramm
- **Übung:** uebungen/09-muster-erkennen
- **Prüffrage:** Welches Muster steckt in `Collections.sort(liste, comparator)`, welches in `new BufferedReader(new FileReader(datei))`, welches in `HttpRequest.newBuilder().uri(u).GET().build()`? Und: Woran erkennst du Muster-Overkill in einem Pull Request?
- **Übersetzung:** en: Recognising patterns, avoiding patterns | fr: Reconnaître les patrons, éviter les patrons
- **Stufe:** 3

Leitfaden: Die Übung ist ein Leseauftrag mit Tests: Für sechs Code-Stellen den Musternamen
zuordnen – der Test prüft die Zuordnung. Danach die Gegenrichtung: ein Stück Code mit zu
vielen Mustern gemeinsam zurückbauen. Das ist die Lektion, die aus Musterwissen Urteil
macht.

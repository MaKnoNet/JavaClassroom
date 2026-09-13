---
thema: java
titel: Java
voraussetzungen: []
zielgruppe: [azubi, student, kollege]
---

# Java

Java ist die Hauptsprache in unseren Projekten. Der Lehrplan führt von der ersten Klasse
über Objekte, Collections und Exceptions bis zu den modernen Sprachmitteln (Lambdas,
Streams, Records) und zur Nebenläufigkeit – immer mit dem Ziel, lesbaren und wartbaren
Code zu schreiben.

Stufe 1 (Anfänger) endet mit Lektion 08: Danach kann der Lernende ein kleines Programm
aus mehreren Klassen schreiben und starten. Stufe 2 (Fortgeschritten) endet mit
Lektion 18: Danach beherrscht er das, was im Team für einen Pull Request nötig ist.
Stufe 3 (Erfahren – JVM, Architektur, Betrieb) sind eigene Themen.

## Lektionen

### 01 Erste Klasse
- **Ziele:** Klasse, Methode, `main`; Kompilieren und Starten; Unterschied Klasse/Objekt; ein Programm in der IDE ausführen
- **Übung:** uebungen/01-erste-klasse
- **Prüffrage:** Was unterscheidet eine Klasse von einem Objekt?
- **Übersetzung:** en: First class | fr: Première classe
- **Stufe:** 1

Leitfaden: Bauplan und Haus. Die Übung enthält bereits einen Test – so sieht der Lernende
vom ersten Tag an, wie „fertig" definiert ist.

### 02 Variablen, Typen und Operatoren
- **Ziele:** Primitive Typen vs. `String`; alle acht primitiven Typen (`byte`, `short`, `int`, `long`, `float`, `double`, `boolean`, `char`) – im Alltag `int`, `long`, `double`, `boolean`, `char`; `float` bewusst meiden; `var` für lokale Variablen und wann es lesbar bleibt; Deklaration, Zuweisung; arithmetische, Vergleichs- und logische Operatoren (`&&`, `||`, `!`); `final`; Typumwandlung; zu jedem primitiven Typ gibt es eine Wrapper-Klasse (`int` → `Integer`, `double` → `Double`), Java wandelt automatisch um (Autoboxing) – warum es beides gibt, folgt in Lektion 08 und 13
- **Prüffrage:** Warum ergibt `7 / 2` in Java `3`, und wie bekommt man `3.5`? Und: `int` oder `Integer` – was ist der Unterschied in einem Satz?
- **Übersetzung:** en: Variables, types and operators | fr: Variables, types et opérateurs
- **Stufe:** 1

Leitfaden: Die Typtabelle mit Bitbreite und Wertebereich an die Tafel; `long` für
Zeitstempel und IDs, `double` für Geld *nicht* (später `BigDecimal`). Zum Abschluss die
Schlüsselwörter, die dem Lernenden begegnen werden, aber keine Rolle spielen: `const` und
`goto` (reserviert, ohne Funktion), `native` (Methoden in C), `strictfp` (veraltet), `_`
(ab Java 22 für unbenannte Variablen). Nennen, einordnen, weitergehen.

### 03 Eingabe und Ausgabe
- **Ziele:** `System.out.println` und `printf`/`String.format`; Benutzereingaben mit `Scanner`; Zahlen aus Text parsen; was passiert bei falscher Eingabe (Vorgriff auf Exceptions)
- **Prüffrage:** Was liefert `scanner.nextLine()` nach einem `nextInt()` – und warum?
- **Übersetzung:** en: Input and output | fr: Entrée et sortie
- **Stufe:** 1

### 04 Kontrollfluss
- **Ziele:** `if`/`else`, `switch` inklusive Switch Expressions (`case 1 -> …`), `for`, `while`, `do-while`, `break`/`continue`; wann welche Schleife
- **Prüffrage:** Wann ist `do-while` die richtige Wahl – und welchen Pfad nimmt ein gegebenes Programm bei bestimmten Werten?
- **Übersetzung:** en: Control flow | fr: Flux de contrôle
- **Stufe:** 1

Leitfaden: „Code-Interpreter"-Rätsel als Prüfung – ein kurzes Programm zeigen, der Lernende
sagt die Ausgabe voraus, dann ausführen.

### 05 Arrays
- **Ziele:** Arrays deklarieren, befüllen, per Index lesen; `length`; Schleife über ein Array; `ArrayIndexOutOfBoundsException`; Grenzen von Arrays (feste Größe) als Motivation für Collections
- **Prüffrage:** Warum beginnt der Index bei 0, und was ist der letzte gültige Index eines Arrays mit `length` 5?
- **Übersetzung:** en: Arrays | fr: Tableaux
- **Stufe:** 1

### 06 Methoden
- **Ziele:** Parameter, Rückgabewert, `return`; `static` vs. Instanzmethode; warum `public static void main(String[] args)`; Sichtbarkeit von Variablen (Scope); Methoden klein halten; Überladen
- **Übung:** uebungen/02-rechner
- **Prüffrage:** Was ist der Unterschied zwischen Parameter und Argument – und warum ist eine Variable aus `main` in einer anderen Methode unbekannt?
- **Übersetzung:** en: Methods | fr: Méthodes
- **Stufe:** 1

### 07 Klassen und Objekte
- **Ziele:** Felder, Konstruktoren, `new`, `this`; Zustand und Verhalten; **Kapselung**: Felder `private`, Zugriff nur über Methoden, die prüfen dürfen (Setter mit Validierung); `toString`; **Pakete und Sichtbarkeit**: `package`, `import`, die vier Stufen `private` → *package-private* (kein Schlüsselwort) → `protected` → `public`, Regel „so eng wie möglich"; das Modulsystem (`module-info.java`, `requires`, `exports`) als nächste Stufe über Paketen – wir nutzen es nicht, aber Bibliotheken tun es
- **Übung:** uebungen/06-bankkonto
- **Prüffrage:** Warum sind Felder `private`, wenn es doch Getter gibt – und was kann ein Setter, was ein öffentliches Feld nicht kann? Und: Eine Methode ohne Schlüsselwort davor – wer darf sie aufrufen?
- **Übersetzung:** en: Classes and objects | fr: Classes et objets
- **Stufe:** 1

Leitfaden: Eckpunkte 1 und 2 der OOP. Klasse = Bauplan des Architekten, Objekt = das
gemauerte Haus, in dem jemand wohnt (`Hund` vs. `meinHund = new Hund()`). Kapselung =
geschlossene Motorhaube: Man fährt über das Gaspedal (Methode), man gießt kein Benzin
mit dem Becher in den Motor (direkter Feldzugriff). Das Bankkonto in der Übung zeigt,
warum: `einzahlen(-50)` darf nicht durchgehen – ein öffentliches Feld könnte das nicht
verhindern.

Sichtbarkeit an der Tafel, von eng nach weit:

| Stufe | Schlüsselwort | Sichtbar für | Typischer Einsatz |
|---|---|---|---|
| privat | `private` | nur die eigene Klasse | Felder, Hilfsmethoden – der Normalfall |
| package-private | *keins* | alle Klassen im selben Paket | Hilfsklassen eines Pakets, Testzugriff (Tests liegen im selben Paket) |
| geschützt | `protected` | Paket **plus** Unterklassen, auch in anderen Paketen | selten sinnvoll – siehe Lektion 10 |
| öffentlich | `public` | alle | die Schnittstelle der Klasse |

Merksatz: Ohne Schlüsselwort ist es *nicht* öffentlich – ein häufiger Irrtum. Die Übungen
nutzen package-private bewusst: Testklassen ohne `public` liegen im selben Paket wie die
Klasse, die sie testen.

### 08 Referenz und Wert
- **Ziele:** Referenztypen vs. primitive Typen; `==` vs. `equals`; Objekte als Parameter; `null` und die `NullPointerException`; **`int` vs. `Integer`** im Detail: `Integer` ist ein Objekt und darf `null` sein, `int` nie; Autoboxing/Unboxing; die zwei Fallen – `Integer a == Integer b` vergleicht Referenzen (funktioniert zufällig bis 127, danach nicht mehr) und Unboxing von `null` wirft `NullPointerException`
- **Prüffrage:** Zwei Variablen zeigen auf dasselbe Objekt – was passiert, wenn man über eine davon ein Feld ändert? Und: Warum ist `Integer.valueOf(1000) == Integer.valueOf(1000)` `false`, `Integer.valueOf(100) == Integer.valueOf(100)` aber `true`?
- **Übersetzung:** en: Reference and value | fr: Référence et valeur
- **Stufe:** 1

### 09 Strings, Dateien und Zeit
- **Ziele:** `String`-API (`split`, `substring`, `strip`, `StringBuilder`); Dateien lesen und schreiben mit `Path`/`Files` in UTF-8; `java.time` (`LocalDate`, `LocalDateTime`, `Duration`, Formatierung); Objekte speichern: Java-Serialisierung (`Serializable`, `transient`, `serialVersionUID`) kennen, aber für Dateien und Schnittstellen JSON oder Text bevorzugen
- **Prüffrage:** Warum sind Strings unveränderlich, und was bedeutet das für eine Schleife, die einen Text zusammenbaut?
- **Übersetzung:** en: Strings, files and time | fr: Chaînes, fichiers et temps
- **Stufe:** 2

### 10 Vererbung und Polymorphie
- **Ziele:** **Vererbung**: `extends`, `super`, Überschreiben, `@Override`; `protected` – was Unterklassen sehen dürfen, und warum ein `protected`-Feld meist ein Warnzeichen ist (die Unterklasse hängt an Innereien der Oberklasse); `final` bei Klassen und Methoden; `instanceof` mit Pattern Matching (`if (tier instanceof Hund hund)`) – und warum eine Kette solcher Prüfungen meist ein Zeichen für fehlende Polymorphie ist; **Polymorphie**: ein Aufruf, unterschiedliche Reaktion je nach Laufzeittyp; wann Vererbung passt und wann Komposition besser ist
- **Prüffrage:** Was ist Polymorphie – mit einem Beispiel? Und: Wann ist `protected` richtig, wann sollte es `private` mit einer `protected`-Methode sein?
- **Übersetzung:** en: Inheritance and polymorphism | fr: Héritage et polymorphisme
- **Stufe:** 2

Leitfaden: Eckpunkte 3 und 4. Vererbung = das Smartphone erbt das Telefonieren vom
Telefon und fügt Apps hinzu (`ElektroAuto extends Fahrzeug`). Polymorphie = „Sprich!" zu
Hund und Katze – derselbe Befehl, Bellen oder Miauen (`Tier.macheGeraeusch()` überschrieben
in `Hund` und `Katze`, aufgerufen über eine `List<Tier>`). Dann die Grenze: Nicht jede
„ist-ein"-Beziehung ist Vererbung wert – ein `Auto` *hat* einen Motor, es *ist* keiner.

### 11 Interfaces und abstrakte Klassen
- **Ziele:** **Abstraktion**: nur festlegen, *was* etwas kann, nicht *wie*; `interface`, `implements`, Default- und statische Methoden; `abstract class`; wann Interface, wann abstrakte Klasse; Programmieren gegen Schnittstellen (Dependency Inversion); die fünf Eckpunkte der OOP zusammenfassen
- **Prüffrage:** Warum nimmt eine Methode lieber ein `List<String>` entgegen als ein `ArrayList<String>`? Und: Nenne die fünf Eckpunkte der OOP mit je einem Beispiel aus den Lektionen 07 bis 11.
- **Übersetzung:** en: Interfaces and abstract classes | fr: Interfaces et classes abstraites
- **Stufe:** 2

Leitfaden: Eckpunkt 5. Abstraktion = der Startknopf der Kaffeemaschine: Man muss Pumpe
und Heizung nicht kennen, um Kaffee zu bekommen (`interface Nachrichtenservice { void
senden(String text); }` – `EmailService` versteckt SMTP dahinter, der Aufrufer sieht nur
`senden`). Zum Abschluss die fünf Eckpunkte an die Tafel: Klassen/Objekte, Kapselung,
Vererbung, Polymorphie, Abstraktion – je eine Metapher, je ein Codebeispiel aus den
Übungen. Das ist Prüfungs- und Vorstellungsgesprächs-Vokabular; der Lernende soll es frei
erklären können.

### 12 Lambdas und Optional
- **Ziele:** Funktionale Interfaces (`Predicate`, `Function`, `Supplier`, `Consumer`); Lambda-Ausdrücke und Methodenreferenzen; Code als Argument übergeben; `Optional` statt `null` als Rückgabe
- **Prüffrage:** Was ist ein funktionales Interface, und warum gibt eine Suche lieber `Optional<Kunde>` zurück als `null`?
- **Übersetzung:** en: Lambdas and Optional | fr: Lambdas et Optional
- **Stufe:** 2

### 13 Collections und Generics
- **Ziele:** `List`, `Set`, `Map` und ihre Implementierungen (`ArrayList` vs. `LinkedList`, `HashSet`, `HashMap`) mit Kosten; warum `List<Integer>` und nicht `List<int>` (Generics brauchen Objekte – hier zahlt sich Lektion 08 aus); Generics lesen **und** schreiben (`<T>`, `<T extends Comparable<T>>`); typsichere eigene Klassen und Methoden
- **Übung:** uebungen/04-generics
- **Prüffrage:** Wann `Set` statt `List` – und was verhindert `<T>` gegenüber `Object`?
- **Übersetzung:** en: Collections and generics | fr: Collections et génériques
- **Stufe:** 2

### 14 Exceptions
- **Ziele:** `try`/`catch`/`finally`, `throw`/`throws`, checked vs. unchecked, eigene Exceptions; Fehler nicht verschlucken; try-with-resources; `assert` – standardmäßig abgeschaltet, deshalb nie für Eingabeprüfung, höchstens für interne Annahmen
- **Prüffrage:** Warum ist ein leerer `catch`-Block gefährlich?
- **Übersetzung:** en: Exceptions | fr: Exceptions
- **Stufe:** 2

### 15 Records und Enums
- **Ziele:** `record` für unveränderliche Datenklassen, `enum` statt Magic Strings, `switch` über Enums; `sealed`/`permits`/`non-sealed` – eine geschlossene Menge von Untertypen; zusammen mit Records und `switch` mit Pattern Matching (`case Kreis k ->`) das Java-21-Idiom für Datenmodelle, bei dem der Compiler Vollständigkeit prüft
- **Prüffrage:** Was garantiert ein Record, das eine normale Klasse nicht garantiert? Und: Was gewinnt man, wenn `Form` `sealed` ist und `Kreis` sowie `Rechteck` `permits`?
- **Übersetzung:** en: Records and enums | fr: Records et enums
- **Stufe:** 2

### 16 Streams
- **Ziele:** `stream()`, `filter`, `map`, `sorted`, `collect`; `Collectors.toList`, `joining`, `groupingBy`; `Optional` als Ergebnis von `max`/`findFirst`; wann eine Schleife lesbarer bleibt
- **Übung:** uebungen/03-streams
- **Prüffrage:** Was ist der Unterschied zwischen einer Zwischenoperation wie `filter` und einer Endoperation wie `collect` – und wann wird tatsächlich gerechnet?
- **Übersetzung:** en: Streams | fr: Streams
- **Stufe:** 2

Leitfaden: Ein Stream ist eine Fließband-Beschreibung, keine Datenstruktur – erst die
Endoperation setzt das Band in Gang. Mit einer vorhandenen Schleife beginnen und sie Schritt
für Schritt in `filter`/`map`/`collect` übersetzen (Refactoring-Rätsel); dann zeigen, wo
`groupingBy` eine ganze Map-Schleife ersetzt.

### 17 Nebenläufigkeit
- **Ziele:** `Thread` und `Runnable`; `ExecutorService` statt Threads von Hand; Race Conditions erkennen; Thread-Safety mit `synchronized`, `AtomicInteger`, `ConcurrentHashMap`; `volatile` – nur Sichtbarkeit, keine Atomarität (reicht für ein Stopp-Flag, nicht für einen Zähler); unveränderliche Objekte als sicherster Weg
- **Übung:** uebungen/05-nebenlaeufigkeit
- **Prüffrage:** Warum ist `zaehler++` aus zwei Threads nicht sicher, obwohl es wie eine Operation aussieht?
- **Übersetzung:** en: Concurrency | fr: Concurrence
- **Stufe:** 2

Leitfaden: Unsere Server sind Multiuser-Systeme – jeder Request ein Thread. Erst den Fehler
erleben (Übung: Zähler verliert Erhöhungen), dann die Werkzeuge. Regel: kein geteilter
veränderlicher Zustand; wenn doch, dann atomar oder synchronisiert, und dokumentiert.

### 18 Clean Code im Kleinen
- **Ziele:** Sprechende Namen, kleine Methoden, keine Magic Numbers, DRY; Code lesen und verbessern
- **Prüffrage:** Woran erkennt man, dass eine Methode zu viel tut?
- **Übersetzung:** en: Clean code in the small | fr: Clean code au quotidien
- **Stufe:** 2

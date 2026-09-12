---
thema: java
titel: Java
voraussetzungen: []
zielgruppe: [azubi, student]
---

# Java

Java ist die Hauptsprache in unseren Projekten. Der Lehrplan führt von der ersten Klasse
bis zu Collections, Exceptions und den modernen Sprachmitteln (Records, Streams), immer mit
dem Ziel, lesbaren und wartbaren Code zu schreiben.

Am Ende kann der Lernende ein kleines Programm aus mehreren Klassen entwerfen, mit
Objekten, Vererbung und Interfaces strukturieren, Collections sinnvoll wählen und Fehler
mit Exceptions sauber behandeln.

## Lektionen

### 01 Erste Klasse
- **Ziele:** Klasse, Methode, `main`; Kompilieren und Starten; Unterschied Klasse/Objekt; ein Programm in der IDE ausführen
- **Übung:** uebungen/01-erste-klasse
- **Prüffrage:** Was unterscheidet eine Klasse von einem Objekt?

Leitfaden: Bauplan und Haus. Die Übung enthält bereits einen Test – so sieht der Lernende
vom ersten Tag an, wie „fertig" definiert ist.

### 02 Variablen, Typen und Operatoren
- **Ziele:** Primitive Typen vs. `String`; `int`/`double`/`boolean`; Deklaration, Zuweisung; arithmetische und logische Operatoren; `final`
- **Prüffrage:** Warum ergibt `7 / 2` in Java `3`?

### 03 Kontrollfluss
- **Ziele:** `if`/`else`, `switch`, `for`, `while`, `break`/`continue`; Schleifen lesbar halten
- **Prüffrage:** Wann ist `while` die richtige Wahl statt `for`?

### 04 Methoden
- **Ziele:** Parameter, Rückgabewert, `static` vs. Instanzmethode; Methoden klein halten; sprechende Namen; Überladen
- **Übung:** uebungen/02-rechner
- **Prüffrage:** Was ist der Unterschied zwischen Parameter und Argument?

### 05 Klassen und Objekte
- **Ziele:** Felder, Konstruktoren, `this`; Kapselung mit `private` und Getter; `toString`
- **Prüffrage:** Warum sind Felder `private`, wenn es doch Getter gibt?

### 06 Referenz und Wert
- **Ziele:** Referenztypen vs. primitive Typen; `==` vs. `equals`; Objekte als Parameter; `null`
- **Prüffrage:** Zwei Variablen zeigen auf dasselbe Objekt – was passiert, wenn man über eine davon ein Feld ändert?

### 07 Vererbung und Polymorphie
- **Ziele:** `extends`, `super`, Überschreiben, `@Override`; wann Vererbung passt und wann nicht
- **Prüffrage:** Was ist Polymorphie – mit einem Beispiel?

### 08 Interfaces und Abstraktion
- **Ziele:** `interface`, `implements`; Programmieren gegen Schnittstellen; funktionale Interfaces und Lambdas
- **Prüffrage:** Warum nimmt eine Methode lieber ein `List<String>` entgegen als ein `ArrayList<String>`?

### 09 Collections und Generics
- **Ziele:** `List`, `Set`, `Map` und ihre Implementierungen; Generics lesen und schreiben; die richtige Struktur wählen
- **Prüffrage:** Wann `Set` statt `List`?

### 10 Exceptions
- **Ziele:** `try`/`catch`/`finally`, checked vs. unchecked, eigene Exceptions; Fehler nicht verschlucken; try-with-resources
- **Prüffrage:** Warum ist ein leerer `catch`-Block gefährlich?

### 11 Records und Enums
- **Ziele:** `record` für unveränderliche Datenklassen, `enum` statt Magic Strings, `switch` über Enums
- **Prüffrage:** Was garantiert ein Record, das eine normale Klasse nicht garantiert?

### 12 Streams
- **Ziele:** `stream()`, `filter`, `map`, `sorted`, `collect`; `Collectors.toList`, `joining`, `groupingBy`; `Optional` als Ergebnis von `max`/`findFirst`; wann eine Schleife lesbarer bleibt
- **Übung:** uebungen/03-streams
- **Prüffrage:** Was ist der Unterschied zwischen einer Zwischenoperation wie `filter` und einer Endoperation wie `collect` – und wann wird tatsächlich gerechnet?

Leitfaden: Ein Stream ist eine Fließband-Beschreibung, keine Datenstruktur – erst die
Endoperation setzt das Band in Gang. Mit einer vorhandenen Schleife beginnen und sie Schritt
für Schritt in `filter`/`map`/`collect` übersetzen; dann zeigen, wo `groupingBy` eine ganze
Map-Schleife ersetzt. `Optional` als Antwort auf „was, wenn die Liste leer ist" einführen.

### 13 Clean Code im Kleinen
- **Ziele:** Sprechende Namen, kleine Methoden, keine Magic Numbers, DRY; Code lesen und verbessern
- **Prüffrage:** Woran erkennt man, dass eine Methode zu viel tut?

---
thema: javascript
titel: JavaScript
voraussetzungen: [html]
zielgruppe: [azubi, student]
reihenfolge: 40
---

# JavaScript

JavaScript macht Seiten interaktiv: auf Klicks reagieren, Inhalte ändern, Daten laden.
Der Lehrplan beginnt in der Browser-Konsole und endet bei asynchronem Code und der
Fehlersuche mit den Entwicklerwerkzeugen.

Am Ende kann der Lernende Funktionen und Arrays sicher verwenden, das DOM lesen und
verändern, auf Ereignisse reagieren, Daten per `fetch` laden und Fehler systematisch finden.

## Lektionen

### 01 Einstieg: Konsole, Variablen, Typen
- **Ziele:** Skript einbinden, `console.log`; `let`/`const`; Zahlen, Strings, Booleans, `undefined`/`null`; Template-Strings
- **Prüffrage:** Warum `const` als Standard und `let` nur bei Bedarf?
- **Übersetzung:** en: Getting started: console, variables, types | fr: Premiers pas : console, variables, types
- **Stufe:** 1

### 02 Funktionen und Arrays
- **Ziele:** Funktionen deklarieren, Pfeilfunktionen; Arrays mit `map`, `filter`, `reduce`, `find`; Funktionen als Werte
- **Übung:** uebungen/01-funktionen-und-arrays
- **Prüffrage:** Was ist der Unterschied zwischen `map` und `forEach`?
- **Übersetzung:** en: Functions and arrays | fr: Fonctions et tableaux
- **Stufe:** 1

### 03 DOM: Elemente finden und ändern
- **Ziele:** `querySelector`, `textContent`, `classList`, Elemente erzeugen und einhängen
- **Prüffrage:** Was ist das DOM – und ist es dasselbe wie der HTML-Quelltext?
- **Übersetzung:** en: DOM: finding and changing elements | fr: DOM : trouver et modifier des éléments
- **Stufe:** 1

### 04 Events
- **Ziele:** `addEventListener`, Event-Objekt, `preventDefault`; Zustand in Variablen halten und anzeigen
- **Übung:** uebungen/02-dom-klick
- **Prüffrage:** Warum steht das Skript am Ende von `body` oder nutzt `defer`?
- **Übersetzung:** en: Events | fr: Événements
- **Stufe:** 1

### 05 Objekte und JSON
- **Ziele:** Objektliteral, Eigenschaften, Methoden; `JSON.stringify`/`parse`; Destructuring
- **Prüffrage:** Was ist der Unterschied zwischen einem JavaScript-Objekt und JSON?
- **Übersetzung:** en: Objects and JSON | fr: Objets et JSON
- **Stufe:** 2

### 06 Asynchron: Promises und fetch
- **Ziele:** Warum asynchron; `Promise`, `async`/`await`, `fetch`; Fehler mit `try`/`catch` behandeln
- **Prüffrage:** Was gibt eine `async`-Funktion immer zurück?
- **Übersetzung:** en: Asynchronous: promises and fetch | fr: Asynchrone : promesses et fetch
- **Stufe:** 2

### 07 Module und Struktur
- **Ziele:** `import`/`export`, Code in Dateien aufteilen, keine globalen Variablen
- **Prüffrage:** Warum sind globale Variablen in größeren Skripten ein Problem?
- **Übersetzung:** en: Modules and structure | fr: Modules et structure
- **Stufe:** 2

### 08 Fehler finden
- **Ziele:** Konsole lesen, Breakpoints setzen, Variablen beobachten, Stacktrace verstehen
- **Prüffrage:** Was sagt `Uncaught TypeError: Cannot read properties of undefined` über den Fehler aus?
- **Übersetzung:** en: Finding bugs | fr: Trouver les erreurs
- **Stufe:** 2

---
thema: frontend
titel: Modernes Frontend und TypeScript
voraussetzungen: [html, css, javascript]
zielgruppe: [azubi, student, kollege]
reihenfolge: 50
---

# Modernes Frontend und TypeScript

Dieses Thema führt von den Vanilla-Webgrundlagen zum Tooling moderner
Enterprise-Frontends. Der Lernende verlässt das manuelle Schreiben einzelner Skripte und
lernt, wie große, typsichere und schnelle Web-Anwendungen im Team strukturiert und gebaut
werden – und was Vaadin im Hintergrund tut, sobald ein Projekt eigene Client-Komponenten
mitbringt (Vaadin-Lektion 18).

Werkzeuge: Node.js (LTS) und npm; alles Weitere kommt per `npm install` ins Projekt.
Jede Übung ist ein eigenes npm-Projekt mit Tests (Vitest), die Abnahme läuft über
`npm test`, `npm run check` (TypeScript) und `npm run build` (Vite).

## Lektionen

### 01 Das Node-Ökosystem und Bundler: Vite
- **Ziele:** Node.js als Laufzeit für Entwicklungswerkzeuge (nicht für den Browser); `package.json` als Gegenstück zu `build.gradle`: Name, Skripte, `dependencies` vs. `devDependencies`; `npm install`, `npm run`, `npx`; `package-lock.json` ins Repository, `node_modules/` und `dist/` nicht; `npm ci` in der Pipeline; moderne Bundler am Beispiel **Vite**: Dev-Server mit Hot Module Replacement vs. Produktions-Build mit Minifizierung, Tree Shaking und gehashten Dateinamen (Cache Busting); `<script type="module">` als Einstiegspunkt; Firmenproxy: `npm config set cafile …` maschinenlokal in `~/.npmrc`, nie im Repo (dieselbe Regel wie `~/.gradle/gradle.properties`); `pnpm` als Alternative nur kennen
- **Übung:** uebungen/01-vite-build
- **Prüffrage:** Warum nutzt man im modernen Frontend ein Build-Tool wie Vite, anstatt die geschriebenen JavaScript-Dateien direkt im Browser zu laden? Und: Was bewirkt der Hash in `index-CeyL0XDW.js`?
- **Übersetzung:** en: The Node ecosystem and bundlers: Vite | fr: L'écosystème Node et les bundlers : Vite
- **Stufe:** 2

Leitfaden: Mit dem Klickzähler aus JavaScript-Übung 02 beginnen – der läuft ohne alles.
Dann die Frage: „Und wenn es 40 Dateien und drei Bibliotheken sind?" Der erste
`npm install` hinter dem Firmenproxy scheitert erfahrungsgemäß – das ist kein Umweg,
sondern Teil der Lektion.

### 02 Typsicherheit im Browser: TypeScript
- **Ziele:** Warum JavaScript in großen Projekten gefährlich ist: `'0' + 3`, `undefined` ohne Fehler, Tippfehler in Eigenschaftsnamen; der TypeScript-Compiler `tsc` und `tsconfig.json` (`strict`); Basistypen, Arrays, `interface`, Union-Typen (`'EUR' | 'USD'`), `string | undefined`; Typen für API-Antworten und Formulardaten; Kompilierzeit-Prüfung vs. Laufzeit: Typen werden beim Übersetzen gestrichen (*Type Erasure*, wie Generics in Java-Lektion 13) – Daten von außen müssen trotzdem geprüft werden; Fehlermeldungen lesen (`TS2339`, `TS18048`, `TS2345`)
- **Übung:** uebungen/02-ts-refactoring
- **Prüffrage:** Welchen Vorteil bietet TypeScript einem Java-Entwickler beim Schreiben von Frontend-Code – und was passiert mit den Typdefinitionen, wenn der Code im Browser ausgeführt wird?
- **Übersetzung:** en: Type safety in the browser: TypeScript | fr: La sûreté des types dans le navigateur : TypeScript
- **Stufe:** 2

Leitfaden: Die Übung beginnt mit drei roten Tests in JavaScript. Erst umbenennen (der
Compiler verlangt Typen), dann typisieren (der Compiler findet die drei Fehler), dann
beheben. Der Java-Entwickler erkennt alles wieder: Interface, `strict` wie `-Xlint`,
Erasure wie bei Generics.

### 03 Deklarative UIs und Komponenten-Denkweise
- **Ziele:** Abkehr von der manuellen DOM-Manipulation (`createElement`, `textContent` an fünf Stellen); deklaratives UI: Zustand ist die einzige Wahrheit, `render(zustand)` als reine Funktion liefert die Ansicht – „die UI ist eine Funktion des Zustands"; warum Drift-Fehler (eine Stelle vergessen) damit strukturell unmöglich werden; Event Delegation, weil `innerHTML` Elemente ersetzt; Komponenten als wiederverwendbare Bausteine mit klarem Datenfluss: Properties hinein, Events hinaus; Grenzen von `innerHTML` (alles neu) als Motivation für Lit, React und Vaadins Client-Komponenten
- **Übung:** uebungen/03-zaehler-deklarativ
- **Prüffrage:** Was bedeutet der Satz „Die UI ist eine Funktion des Zustands" im Vergleich zur klassischen, imperativen DOM-Änderung per Hand – und welche Fehlerklasse verschwindet dadurch?
- **Übersetzung:** en: Declarative UIs and thinking in components | fr: Interfaces déclaratives et pensée par composants
- **Stufe:** 2

Leitfaden: Den Fehler im imperativen Zähler erst finden lassen (Zurücksetzen vergisst
die Parität), dann die Frage stellen: „Wie verhinderst du, dass das beim nächsten Knopf
wieder passiert?" Die Antwort „ich denke dran" ist die falsche – die richtige ist, dass
die Parität nie gespeichert, sondern immer berechnet wird.

### 04 Web-Komponenten und Lit
- **Ziele:** Der W3C-Standard: Custom Elements (`customElements.define`), Shadow DOM, Templates; **Lit** als leichte Basis: `LitElement`, `static properties`, `render()` mit `html`-Template, `static styles` mit `css`; Attribut ↔ Property (`type: Number`); Ereignisse mit `@click`; `CustomEvent` mit `detail`, `bubbles` und `composed` – warum Events sonst im Shadow DOM stecken bleiben; CSS-Kapselung: nichts leckt hinein, nichts hinaus – Grundlage für Design-Systeme; Tests mit Vitest und jsdom (`updateComplete`); Bezug zu Vaadin: die Client-Komponenten sind Lit-Elemente, und eigene bringen Node/npm in den Gradle-Build (Vaadin-Lektion 18)
- **Übung:** uebungen/04-lit-komponente
- **Prüffrage:** Was verhindert das Shadow DOM bei einer Web-Komponente, und warum ist das für wiederverwendbare Design-Systeme in einer großen Firma so wichtig? Und: Warum braucht ein `CustomEvent` aus einer Komponente `composed: true`?
- **Übersetzung:** en: Web components and Lit | fr: Composants web et Lit
- **Stufe:** 3

Leitfaden: `index.html` der Übung hat absichtlich `.preis { color: red }` – der Preis
bleibt schwarz. Das ist der Moment, in dem Shadow DOM ohne Erklärung verständlich wird.
Danach das Event ohne `composed` feuern lassen und zusehen, wie die Seite nichts hört.

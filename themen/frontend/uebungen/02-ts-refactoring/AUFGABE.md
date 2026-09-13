# Übung 02: Drei Fehler, die JavaScript verschluckt

## Aufgabe

`src/` enthält drei JavaScript-Module und drei Tests in TypeScript. `npm install`, dann
`npm test`: drei von sieben Tests sind rot – und `npm run check` (der TypeScript-Compiler)
kann die Module nicht einmal lesen. Refactoring nach TypeScript:

1. Benenne `rechnen.js`, `wetter.js`, `bericht.js` in `.ts` um. `npm run check` meldet
   jetzt `implicitly has an 'any' type` an jedem Parameter – TypeScript will wissen, was
   hineinkommt.
2. Gib jedem Parameter und jeder Rückgabe einen Typ. Für die Messung und die
   Formulareingabe je ein `interface`.
3. Jetzt zeigt `npm run check` die **drei echten Fehler** – die, wegen derer die Tests rot
   sind. Behebe sie, bis Compiler und Tests grün sind.

## Abnahmekriterien

- `npm run check` meldet keinen Fehler; `npm test` zeigt `7 passed`.
- Kein `any` im Code; die Interfaces `Messung` und `Eingabe` sind exportiert.
- Du kannst die drei Fehler benennen und sagen, warum JavaScript sie nicht gemeldet hat.

## Hinweise

1. Parameter typisieren: `function summe(zahlen: number[]): number`. Ein Interface:
   ```ts
   export interface Messung { stadt: string; temperatur: number; }
   ```
2. Die drei Fehler, die der Compiler dann findet, haben Nummern: `TS2339` (Eigenschaft
   gibt es nicht – ein Tippfehler), `TS18048` (ist möglicherweise `undefined` – ein
   fehlender Fall) und `TS2345` (`string[]` passt nicht zu `number[]` – ein
   Formularfeld liefert Text). Lies die Meldung: Sie nennt Datei, Zeile und Grund.
3. Zur Laufzeit ist von den Typen nichts mehr da: `tsc` (oder Vite) streicht sie beim
   Übersetzen, der Browser bekommt gewöhnliches JavaScript. Typen prüfen beim Schreiben,
   nicht beim Ausführen – wie Generics in Java (Lektion 13), die nach dem Kompilieren
   auch verschwunden sind (*Type Erasure*). Wer Daten von außen bekommt (API, Formular),
   muss sie deshalb trotzdem prüfen; das Interface ist ein Versprechen, kein Wächter.

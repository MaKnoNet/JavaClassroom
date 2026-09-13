# Übung 03: Derselbe Zähler, deklarativ

## Aufgabe

`src/zaehler.js` ist ein Zähler, wie man ihn imperativ schreibt: Jeder Knopf hat einen
Listener, jeder Listener fasst das DOM an. Und einer hat etwas vergessen – `npm test`
zeigt es: Nach dem Zurücksetzen steht `0`, aber daneben noch `ungerade`.

Bau den Zähler um, ohne den Fehler *an der Stelle* zu flicken:

1. Ein Zustandsobjekt `{ wert: 0 }` ist die einzige Wahrheit.
2. Eine **reine Funktion** `render(zustand)` liefert das komplette HTML für diesen
   Zustand – ohne DOM, ohne Nebenwirkung. Exportiere sie.
3. Jede Änderung setzt einen neuen Zustand und rendert neu. Kein Listener schreibt mehr
   `textContent`.

## Abnahmekriterien

- `npm test`: `4 passed` – zwei Tests für `render`, zwei für das Verhalten.
- In `zaehler.js` gibt es genau **eine** Stelle, die `innerHTML` schreibt, und kein
  `textContent`.
- `npm run dev`, dann im Browser: `+`, `+`, `+`, Zurücksetzen → `0 (gerade)`.

## Hinweise

1. `render` ist nur ein Template-String mit `${zustand.wert}` und der daraus
   berechneten Parität. Weil die Parität *berechnet* wird, kann sie nicht mehr
   „vergessen" werden – das ist der ganze Trick.
2. `wurzel.innerHTML = render(zustand)` ersetzt auch die Knöpfe – mitsamt ihren
   Listenern. Hänge deshalb **einen** Klick-Listener an `wurzel` und schau auf
   `ereignis.target`, welcher Knopf es war (*Event Delegation*).
3. „Die UI ist eine Funktion des Zustands": Wer wissen will, was auf dem Bildschirm
   steht, muss nur den Zustand kennen, nicht die Geschichte aller Klicks. Genau so
   arbeiten Lit (Lektion 04), React und Vaadin-Komponenten – nur dass sie das DOM
   geschickter aktualisieren als `innerHTML`.

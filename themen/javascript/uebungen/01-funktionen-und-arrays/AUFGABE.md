# Übung 01: Funktionen und Arrays

## Aufgabe

Setze in `aufgabe.js` drei Funktionen um:

- `summe(zahlen)` – Summe aller Zahlen im Array; leeres Array → `0`.
- `nurGerade(zahlen)` – neues Array nur mit den geraden Zahlen, Reihenfolge bleibt.
- `groesste(zahlen)` – die größte Zahl; leeres Array → `undefined`.

Öffne `tests.html` im Browser: Sie zeigt, welche Fälle schon bestehen.

## Abnahmekriterien

- `tests.html` zeigt `bestanden: 6/6`.
- Keine Schleife mit Index (`for (let i = 0; …)`) – benutze `reduce`, `filter`, `Math.max`
  oder `for…of`.

## Hinweise

1. `zahlen.reduce((summe, z) => summe + z, 0)` – der zweite Parameter ist der Startwert
   und rettet dich beim leeren Array.
2. `zahlen.filter(z => z % 2 === 0)`; für die größte Zahl `Math.max(...zahlen)` – bei
   leerem Array liefert das `-Infinity`, also vorher `zahlen.length` prüfen.

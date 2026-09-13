# Lösung 02

`loesung/src/` ersetzt die drei `.js`-Dateien in `src/` (die `.js` löschen, die `.ts`
hineinkopieren; die Tests bleiben).

Die drei Fehler und warum JavaScript schwieg:

| Datei | Fehler | JavaScript | TypeScript |
|---|---|---|---|
| `wetter` | `messung.temp` statt `temperatur` | liefert `undefined`, Text lautet `Berlin: undefined °C` | `TS2339: Property 'temp' does not exist on type 'Messung'` |
| `wetter` | `groesste(...)` kann `undefined` sein, `.toFixed` darauf | `TypeError` – aber erst, wenn die Liste leer ist | `TS18048: 'hoechste' is possibly 'undefined'` |
| `bericht` | `summe` bekommt Strings aus dem Formular | `0 + '3' + '4'` ist `'034'` – kein Fehler, nur falsch | `TS2345: Argument of type 'string[]' is not assignable to 'number[]'` |

Warum so: Der Compiler kennt jetzt die Form der Daten (`interface`) und die Verträge der
Funktionen (Parameter- und Rückgabetypen) und prüft jede Verwendung dagegen – beim
Schreiben, in der IDE, bevor irgendetwas läuft. Genau die Fehlerklasse, die in JavaScript
erst beim Kunden auffällt. Der Preis: Für Daten von außen (API, Formular) muss man den
Typ *herstellen* (`Number(e.value)`), nicht nur behaupten – zur Laufzeit ist das
Interface weg.

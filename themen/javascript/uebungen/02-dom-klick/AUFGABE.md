# Übung 02: Ein Zähler mit Klicks

## Aufgabe

Setze in `app.js` einen Zähler um:

- Klick auf `+` erhöht die Zahl in `#anzeige` um 1, Klick auf `−` verringert sie um 1.
- `Zurücksetzen` setzt sie auf 0.
- Der Wert darf nicht unter 0 fallen.

`index.html` bleibt unverändert.

## Abnahmekriterien

- Dreimal `+`, einmal `−` → Anzeige `2`. Danach `Zurücksetzen` → `0`. Dann `−` → bleibt `0`.
- Der aktuelle Wert steht in **einer** Variablen; die Anzeige wird aus ihr aktualisiert,
  nicht aus dem Text im `span` zurückgelesen.

## Hinweise

1. Elemente holen: `document.querySelector("#plus")`. Reagieren:
   `element.addEventListener("click", () => { … })`.
2. Eine Funktion `zeige()` schreibt den Wert in `#anzeige` (`textContent`). Jeder Klick
   ändert die Variable und ruft dann `zeige()` auf – so gibt es nur einen Ort, der die
   Anzeige verändert.

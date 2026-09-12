---
thema: css
titel: CSS
voraussetzungen: [html]
zielgruppe: [azubi, student]
---

# CSS

CSS bestimmt, wie HTML aussieht: Farben, Schrift, Abstände, Anordnung. Der Lehrplan
führt vom ersten Selektor über das Box-Modell bis zu Flexbox, Grid und responsivem Layout.

Am Ende kann der Lernende eine Seite mit eigener Stylesheet-Datei gestalten, Layouts mit
Flexbox und Grid bauen und sie für Bildschirmgrößen vom Handy bis zum Monitor anpassen.

## Lektionen

### 01 Selektoren, Farben, Schrift
- **Ziele:** Stylesheet einbinden; Element-, Klassen- und ID-Selektoren; `color`, `background`, `font-family`, `font-size`; Kaskade und Spezifität im Ansatz
- **Übung:** uebungen/01-farben-und-schrift
- **Prüffrage:** Zwei Regeln treffen dasselbe Element – welche gewinnt, und warum?

### 02 Box-Modell
- **Ziele:** `margin`, `border`, `padding`, `width`; `box-sizing: border-box`; Abstände im Inspektor sehen
- **Prüffrage:** Wie breit ist ein Element mit `width: 200px; padding: 20px; border: 2px` – mit und ohne `border-box`?

### 03 Flexbox
- **Ziele:** `display: flex`, `justify-content`, `align-items`, `gap`, `flex-wrap`; Elemente nebeneinander und zentrieren
- **Übung:** uebungen/02-flexbox-layout
- **Prüffrage:** Was ist die Hauptachse, und was ändert `flex-direction: column` daran?

### 04 Grid
- **Ziele:** `display: grid`, `grid-template-columns`, `gap`, Bereiche benennen; wann Grid, wann Flexbox
- **Prüffrage:** Wann nimmt man Grid statt Flexbox?

### 05 Responsive Design
- **Ziele:** `@media`, `max-width`, relative Einheiten (`rem`, `%`, `vw`), Viewport-Meta; Mobile first
- **Prüffrage:** Warum `rem` statt `px` für Schriftgrößen?

### 06 Variablen und Wiederverwendung
- **Ziele:** `--variablen` und `var()`, gemeinsame Farbpalette, Klassen statt Wiederholung
- **Prüffrage:** Was passiert, wenn man eine CSS-Variable in `:root` und in einem Element definiert?

### 07 Positionierung
- **Ziele:** `position: relative/absolute/fixed/sticky`, `z-index`; wann Positionierung nötig ist und wann Layout reicht
- **Prüffrage:** Wovon hängt ab, worauf sich `position: absolute` bezieht?

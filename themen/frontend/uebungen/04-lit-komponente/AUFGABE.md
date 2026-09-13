# Übung 04: Eine Web-Komponente mit Lit

## Aufgabe

`<preis-anzeige>` ist als Lit-Element angelegt, aber ein Rumpf: `render()` liefert
nichts. `index.html` benutzt das Element zweimal, `npm test` beschreibt, was es können
soll. Setze es um:

1. **Anzeige:** Der Betrag erscheint deutsch formatiert in der eingestellten Währung
   (`19,99 €`, `1.250,00 $`) in einem `<span class="preis">`. Betrag und Währung kommen
   als Properties bzw. Attribute hinein (`betrag`, `waehrung`, schon deklariert).
2. **Knopf:** Ein `<button>` wechselt zwischen `EUR` und `USD`. Nach dem Klick zeigt die
   Komponente den Preis in der neuen Währung **und** feuert ein Event
   `waehrung-gewechselt` mit `detail: { waehrung }` nach außen.
3. **Kapselung:** CSS für `.preis` und den Knopf liegt als `static styles` in der
   Komponente. Die Seite hat `.preis { color: red }` – das darf im Element *nicht*
   wirken, und die Seite darf den Knopf nicht per `querySelector` finden.

## Abnahmekriterien

- `npm test`: `4 passed`; `npm run check` ohne Fehler.
- `npm run dev`: Beide Elemente zeigen ihren Preis, der Knopf schaltet um, und der Preis
  ist **nicht** rot.
- Das Event verlässt das Shadow DOM: Ein Listener auf `document.body` bekommt es mit.

## Hinweise

1. Formatieren mit `new Intl.NumberFormat('de-DE', { style: 'currency', currency:
   this.waehrung }).format(this.betrag)` – Achtung, zwischen Zahl und Zeichen setzt Intl
   ein geschütztes Leerzeichen; der Test normalisiert das.
2. Ereignis in Lit: `<button @click=${this.wechsle}>`. In `wechsle` die Property
   setzen (Lit rendert daraufhin von selbst neu – deklarativ wie in Übung 03) und
   `this.dispatchEvent(new CustomEvent('waehrung-gewechselt', { detail: { … },
   bubbles: true, composed: true }))`. Ohne `composed: true` bleibt das Event im
   Shadow DOM stecken.
3. `static styles = css\`…\`` – `:host` ist das Element selbst. Was hier steht, gilt
   nur innen; was außen steht, kommt nicht herein. Das ist das Shadow DOM: Aufbau und
   Stil der Komponente sind vor der Seite verborgen, deshalb kann ein Design-System
   hundert Komponenten liefern, ohne dass sich Klassen in die Quere kommen.
4. Für Vaadin-Lektion 18 merken: Vaadins Client-Komponenten sind genau solche
   Lit-Elemente – und sobald ein Projekt eigene mitbringt, braucht `./gradlew bootRun`
   Node und npm, die unsere Vaadin-Übungen bisher bewusst nicht brauchen.

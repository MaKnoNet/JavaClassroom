# Lösung 04

`loesung/src/preis-anzeige.ts` ersetzt `src/preis-anzeige.ts`.

Warum so: Eine Lit-Komponente ist Übung 03 in Reinform – `render()` ist die Funktion des
Zustands, nur dass der Zustand aus deklarierten Properties besteht (`betrag`,
`waehrung`) und Lit selbst merkt, wann es neu rendern muss. Das `html`-Template ist kein
String: Lit erkennt die dynamischen Stellen und aktualisiert beim nächsten Rendern nur
sie, nicht das ganze Element.

Der Datenfluss ist die eigentliche Lektion: Daten gehen **hinein** über Properties und
Attribute (`betrag="19.99"` wird dank `type: Number` zur Zahl), Änderungen gehen
**hinaus** als Event. Die Komponente ruft niemals in die Seite hinein – sie weiß nicht,
wer sie benutzt. `composed: true` ist nötig, weil das Shadow DOM Events sonst an seiner
Grenze anhält; `bubbles: true`, damit ein Listener weiter oben (etwa am Warenkorb)
genügt.

`static styles` landet im Shadow DOM: `.preis { color: red }` der Seite trifft nicht,
und die Regeln der Komponente lecken nicht nach außen. Genau das macht Web-Komponenten
für ein Design-System tauglich – und genau so sind Vaadins Client-Komponenten gebaut.

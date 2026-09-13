# Übung 02: Cross-Site Scripting und Output-Encoding

## Aufgabe

`GaestebuchSeite.html` klebt Autor und Text der Einträge unverändert ins HTML. Was ein
Nutzer ins Formular tippt, wird damit Teil der Seite – für *alle*, die sie danach
öffnen. Drei Tests zeigen, was das heißt:

- Ein `<script>`-Tag im Text wird im Browser **ausgeführt** (und schickt hier das Cookie
  des Lesers an einen fremden Server).
- Ein Anführungszeichen im Autor **verlässt das Attribut** und hängt ein `onmouseover`
  an.
- Ein `&` im Text macht das HTML ungültig.

Maskiere die Nutzerdaten beim Ausgeben – passend zum Kontext (Elementinhalt vs.
Attributwert). Die Bibliothek dafür ist schon im `build.gradle` (OWASP Java Encoder).

## Abnahmekriterien

- `./gradlew test` ist grün (vier Tests).
- Kein Filtern oder Verbieten beim *Speichern* – `Tom & Jerry` und `<3` sind gültige
  Kommentare. Maskiert wird beim *Ausgeben*.
- Kein selbst gebauter `replace("<", "&lt;")` – die Bibliothek kennt die Sonderfälle.

## Hinweise

1. `import org.owasp.encoder.Encode;` – `Encode.forHtml(text)` für Elementinhalt,
   `Encode.forHtmlAttribute(text)` für Attributwerte. Es gibt außerdem `forJavaScript`,
   `forUriComponent`, `forCssString`: **Der Kontext bestimmt die Maskierung.**
2. Warum nicht beim Speichern? Dieselben Daten landen später in einer E-Mail, einer
   CSV, einer JSON-API – jede braucht eine andere Maskierung. Wer beim Speichern HTML
   maskiert, hat `&amp;` in der CSV.
3. In Vaadin passiert das automatisch: `new Span(text)` setzt Text, kein HTML
   (Vaadin-Lektion 17 zeigt die Ausnahme `Html`-Komponente). In Thymeleaf ist `th:text`
   sicher und `th:utext` nicht. In JavaScript: `textContent` statt `innerHTML`
   (JavaScript-Lektion 05).
4. Das Cookie-Beispiel scheitert in echten Browsern oft am `HttpOnly`-Flag – das ist
   die zweite Verteidigungslinie, nicht die erste. Content-Security-Policy ist die dritte.

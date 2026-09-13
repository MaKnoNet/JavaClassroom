# Lösung 02: XSS und Output-Encoding

`GaestebuchSeite.java`: `Encode.forHtmlAttribute(autor)` im `title`-Attribut,
`Encode.forHtml(text)` im Elementinhalt.

**Warum zwei verschiedene Methoden:** Im Elementinhalt sind `<`, `>` und `&` gefährlich;
im Attribut zusätzlich `"` und `'`, weil sie das Attribut beenden. `forHtml` maskiert
vorsorglich beides, `forHtmlAttribute` ist auf Attribute zugeschnitten – wichtiger als
die Wahl zwischen diesen beiden ist, dass man in einem JavaScript- oder URL-Kontext
*nicht* HTML-maskiert (dort helfen `forJavaScript` und `forUriComponent`).

**Die Regel in einem Satz:** Eingaben werden validiert (passt die Form?), Ausgaben
werden maskiert (passt der Kontext?). Beides, nie eines statt des anderen.

**Was der Test nicht prüfen kann:** ob der Browser wirklich nichts ausführt. Wer es
sehen will, schreibt die HTML-Ausgabe der Startversion in eine Datei und öffnet sie –
`alert(1)` erscheint beim Überfahren des Eintrags mit der Maus.

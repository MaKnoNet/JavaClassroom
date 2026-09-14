# Lösung 05

Ein `loop`-Rahmen um den Lageraufruf (Bedingung im Kopf: „für jede Position"), danach ein
`alt`/`else` mit dem Fehlerfall zuerst. Die Exception ist eine gestrichelte Antwort an den
Controller – es gibt keinen eigenen Pfeiltyp für Exceptions; man beschriftet die Antwort.

Eine Vereinfachung, die im Diagramm bewusst bleibt: Im echten Code bricht die Schleife
beim ersten nicht verfügbaren Artikel ab. Das zeigt man entweder mit einem `break`-Rahmen
in der Schleife oder – wie hier – gar nicht, weil das Diagramm die Entscheidung *nach* der
Schleife erzählt. Beides ist üblich; wichtig ist, dass der Leser den Fehlerpfad findet.

Regel: Ein `alt` mit zwei Zweigen ist gut lesbar, drei gehen noch, ab vier nimmt man zwei
Diagramme oder ein `ref`.

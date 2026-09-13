Die Datei ersetzt `src/main/java/de/makno/lernen/Umsatz.java`.

Warum so: `readTree` baut für jede Buchung ein `JsonNode`-Objekt mit Kindern – ein
Vielfaches der Dateigröße im Speicher, und alles gleichzeitig. Bei 60 MB Datei und 64 MB
Heap ist das der `OutOfMemoryError`; auf einem Server mit tausend gleichzeitigen
Anfragen passiert dasselbe schon bei viel kleineren Dateien. Der `JsonParser` liefert
die Datei als Folge von Tokens (`START_OBJECT`, `FIELD_NAME`, `VALUE_NUMBER_FLOAT`, …);
im Speicher liegt immer nur das aktuelle Token und unsere Summe. Zwei Dinge muss man
dabei selbst tun, die der Baum abgenommen hätte: zum richtigen Array *vorspulen* und
verschachtelte Strukturen mit `skipChildren()` *überspringen*, sonst zählt man ein
`betrag` aus `details` mit. Der Preis des Streamings ist genau diese Handarbeit – deshalb
bleibt `readTree` für kleine Dokumente richtig und Streaming ist die Wahl für
Massendaten, Exporte und alles, dessen Größe man nicht kennt. Dasselbe Muster gibt es
für XML (StAX, `XMLStreamReader`) und CSV (Commons CSV iteriert ohnehin zeilenweise).

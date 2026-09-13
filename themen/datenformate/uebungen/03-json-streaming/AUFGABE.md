# Übung 03: Eine Datei, die nicht in den Speicher passt

## Aufgabe

`Umsatz.summeDom` summiert alle Beträge einer JSON-Datei – bequem über `readTree`.
Der Test erzeugt eine Datei mit 1,2 Millionen Buchungen (rund 60 MB) und lässt die
Tests mit **64 MB Heap** laufen (`maxHeapSize` in `build.gradle`). Ergebnis:
`OutOfMemoryError`. Genau das erwartet der dritte Test – er bleibt so.

Setze `summeStreaming` mit Jacksons `JsonParser` um: Token für Token lesen, nur die
laufende Summe im Speicher halten. Zwei Fallen stellt die kleine Testdatei:

1. Außerhalb von `buchungen` gibt es weitere Felder (`summeLautAbsender`) – ignorieren.
2. Eine Buchung kann verschachtelte Objekte mit einem eigenen `betrag` enthalten – der
   zählt nicht.

## Abnahmekriterien

- `./gradlew test` ist grün (drei Tests, inklusive `domStirbtAnDerGrossenDatei`).
- `summeStreaming` ruft weder `readTree` noch `readValue` auf.
- Der Speicherbedarf ist unabhängig von der Dateigröße: Verdopple `ANZAHL_BUCHUNGEN`
  im Test – Streaming läuft weiter, nur länger.

## Hinweise

1. `factory.createParser(Files.newInputStream(datei))` in try-with-resources;
   `parser.nextToken()` liefert das nächste Token oder `null` am Ende;
   `parser.currentName()` den Feldnamen, `parser.getDecimalValue()` eine Zahl als
   `BigDecimal`.
2. Erst bis `FIELD_NAME "buchungen"` gefolgt von `START_ARRAY` vorspulen. Dann ist jede
   Buchung ein `START_OBJECT` … `END_OBJECT`.
3. `parser.skipChildren()` überspringt ein verschachteltes Objekt oder Array komplett –
   aufrufen, wenn der Wert eines Feldes mit `START_OBJECT`/`START_ARRAY` beginnt.
4. Schau dir den dritten Test an: Er *erwartet* den `OutOfMemoryError`. In echtem Code
   fängt man den nie – er ist das Signal, dass die Architektur falsch ist, nicht der
   Aufruf.

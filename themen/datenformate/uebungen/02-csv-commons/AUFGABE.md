# Übung 02: CSV richtig lesen und schreiben

## Aufgabe

`KundenCsv` liest und schreibt eine Kundenliste als Semikolon-CSV mit `split` und
`StringBuilder`. Das geht gut, bis ein Name ein Komma, ein Semikolon oder
Anführungszeichen enthält – oder mit `=` beginnt. `./gradlew test` zeigt alle drei
Probleme. Baue um:

1. Import und Export mit **Apache Commons CSV** (steht schon in `build.gradle`):
   ein gemeinsames `CSVFormat` mit `;` als Trennzeichen und der Kopfzeile
   `ID;Name;Rolle`; Felder über ihren Spaltennamen lesen.
2. Beim **Export** jeden Text entschärfen, der mit `=`, `+`, `-`, `@`, Tab oder
   Wagenrücklauf beginnt: Hochkomma `'` davorsetzen.
3. Dateien weiterhin ausdrücklich in UTF-8.

## Abnahmekriterien

- `./gradlew test` ist grün (drei Tests).
- Kein `split` mehr in `KundenCsv`.
- Die Entschärfung ist eine eigene, kleine Methode – testbar, ohne Datei.

## Hinweise

1. `CSVFormat.DEFAULT.builder().setDelimiter(';').setHeader("ID", "Name", "Rolle")
   .setSkipHeaderRecord(true).get()`. Lesen: `CSVParser.parse(reader, format)` und
   über die `CSVRecord`s iterieren, `zeile.get("Name")`. Schreiben: `new
   CSVPrinter(writer, format)`, `printer.printRecord(a, b, c)` – die Kopfzeile schreibt
   das Format selbst – aber nur, wenn `skipHeaderRecord` **false** ist; zum Lesen
   brauchst du es **true**. Also zwei Formate aus einer Basis. Anführungszeichen setzt
   der Printer, wo sie nötig sind.
2. Reader und Writer mit `Files.newBufferedReader(datei, StandardCharsets.UTF_8)` –
   und in try-with-resources (Java-Lektion 14).
3. Warum Semikolon: Deutsches Excel erwartet es, weil das Komma `3,50` bedeutet.
   Warum nie `split`: `"Mustermann, Max"` ist *ein* Feld, `"Firma ""Blitz"" GmbH"`
   enthält maskierte Anführungszeichen, und ein Feld darf sogar Zeilenumbrüche enthalten.
4. Formula Injection trifft nicht deinen Server, sondern den Kollegen, der die Datei in
   Excel öffnet. Deshalb entschärfen beim Export – dort entsteht die Datei.

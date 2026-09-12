# Übung 02: Rechner mit Methoden

## Aufgabe

`src/main/java/de/makno/lernen/Rechner.java` enthält vier Methoden, die alle noch
`UnsupportedOperationException` werfen. Setze sie um:

- `addiere(a, b)`, `subtrahiere(a, b)`, `multipliziere(a, b)` – das Übliche.
- `dividiere(a, b)` – Division; bei `b == 0` soll eine `IllegalArgumentException` mit der
  Nachricht `Division durch 0` fliegen, statt `Infinity` zurückzugeben.

## Abnahmekriterien

- `./gradlew test` ist grün (fünf Tests in `RechnerTest`).
- Keine Methode länger als vier Zeilen.

## Hinweise

1. `double` teilt durch 0 ohne Fehler und liefert `Infinity` – deshalb musst du vorher
   selbst prüfen.
2. Eine Exception wirft man mit `throw new IllegalArgumentException("Division durch 0");`.

# Übung 02: Parametrisierte Tests

## Aufgabe

`Schaltjahr.istSchaltjahr` ist fertig. Schreibe **einen** parametrisierten Test mit
`@CsvSource`, der mindestens diese Fälle abdeckt:

| Jahr | Schaltjahr? | Warum |
|---|---|---|
| 2024 | ja | durch 4 teilbar |
| 2023 | nein | nicht durch 4 teilbar |
| 1900 | nein | durch 100, nicht durch 400 |
| 2000 | ja | durch 400 |

## Abnahmekriterien

- `./gradlew test` ist grün.
- `build/test-results/test/TEST-de.makno.lernen.SchaltjahrTest.xml` enthält `tests="4"`
  (oder mehr) und `failures="0"` – jede Zeile der `@CsvSource` zählt als eigener Test.
- Es gibt genau eine Testmethode.

## Hinweise

1. `@ParameterizedTest` und `@CsvSource` liegen in `org.junit.jupiter.params` bzw.
   `org.junit.jupiter.params.provider`. Sie sind über `junit-jupiter` bereits im Projekt.
2. Die Methode bekommt die Spalten als Parameter: `void test(int jahr, boolean erwartet)`.
   Eine Zeile in `@CsvSource` sieht so aus: `"2024, true"`.

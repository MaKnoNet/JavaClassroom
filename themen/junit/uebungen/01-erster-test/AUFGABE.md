# Übung 01: Dein erster Test

## Aufgabe

`Stapel` ist fertig. Schreibe in `StapelTest` mindestens vier Tests:

1. Ein neuer Stapel ist leer.
2. Nach `lege("a")` ist der Stapel nicht leer und hat die Größe 1.
3. `nimm()` liefert das zuletzt gelegte Element (lege „a", dann „b" – `nimm()` gibt „b").
4. `nimm()` auf einem leeren Stapel wirft `IllegalStateException`.

## Abnahmekriterien

- `./gradlew test` ist grün.
- `build/test-results/test/TEST-de.makno.lernen.StapelTest.xml` enthält `tests="4"` (oder
  mehr) und `failures="0"`.
- Jeder Test hat einen Namen, der das geprüfte Verhalten beschreibt.

## Hinweise

1. Ein Test ist eine Methode mit `@Test` darüber (`import org.junit.jupiter.api.Test`).
   Prüfen mit `assertTrue`, `assertEquals` aus `org.junit.jupiter.api.Assertions`.
2. Für Exceptions: `assertThrows(IllegalStateException.class, () -> stapel.nimm())`.

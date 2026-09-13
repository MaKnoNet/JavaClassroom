# Lösung 06: Circuit Breaker

`WetterService.java` und `resources/application.properties` (nach `src/main/resources/`).

Drei Dinge:

1. `@CircuitBreaker(name = "wetter", fallbackMethod = "unbekannt")` an `fuer`.
2. `private Wetter unbekannt(String stadt, Throwable ursache)` – loggt auf WARN und
   liefert `Wetter.unbekannt(stadt)`.
3. Die Schwellen in `application.properties`.

**Die drei Zustände:** CLOSED (normal, Fehler werden gezählt) → nach `minimum-number-of-calls`
Aufrufen mit mindestens `failure-rate-threshold` Prozent Fehlern OPEN (kein Aufruf
kommt durch, sofort Fallback) → nach `wait-duration-in-open-state` HALF_OPEN (ein
Probeaufruf; klappt er, CLOSED, sonst wieder OPEN).

**Warum das im Test sichtbar wird:** `verify(abruf, times(3))` – der vierte und fünfte
Aufruf erreichen die Attrappe nicht mehr. Ohne Schalter wären es fünf.

**Ohne Spring:** Dieselbe Bibliothek geht auch pur:
`CircuitBreaker.ofDefaults("wetter").decorateSupplier(() -> abruf.hole(stadt))`.
Die Annotation ist nur die bequeme Form; wer den Mechanismus verstehen will, baut ihn
einmal von Hand.

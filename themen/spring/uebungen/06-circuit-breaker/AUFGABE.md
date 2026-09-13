# Übung 06: Schutzschalter mit Resilience4j

## Aufgabe

`WetterService.fuer` ruft einen fremden Dienst. Fällt der aus, fliegt die Exception bis
zum Aufrufer durch – und jeder weitere Aufruf rennt wieder gegen den kaputten Dienst.
Bei tausend Nutzern pro Minute ist das der Moment, in dem ein Ausfall *dort* zum
Ausfall *hier* wird (kaskadierender Absturz).

Schütze die Methode mit einem **Circuit Breaker**:

1. Bei einem Fehler antwortet der Service mit `Wetter.unbekannt(stadt)` statt mit einer
   Exception.
2. Nach drei Fehlern öffnet sich der Schalter: Der Dienst wird nicht mehr gerufen, die
   Antwort kommt sofort aus dem Fallback.

Die Bibliothek ist schon im `build.gradle` (`resilience4j-spring-boot3` plus AOP).

## Abnahmekriterien

- `./gradlew test` ist grün (drei Tests).
- `WetterService` enthält keinen `try`/`catch` und keinen eigenen Zähler – das ist die
  Aufgabe der Bibliothek.
- Die Schwellen stehen in `application.properties`, nicht im Code.

## Hinweise

1. `@CircuitBreaker(name = "wetter", fallbackMethod = "unbekannt")` aus
   `io.github.resilience4j.circuitbreaker.annotation` an die Methode.
2. Die Fallback-Methode steht in derselben Klasse, hat **dieselben Parameter plus ein
   `Throwable`** am Ende und denselben Rückgabetyp. Sie wird bei jedem Fehler gerufen –
   und bei offenem Schalter mit einer `CallNotPermittedException`.
3. Konfiguration (Schlüssel `resilience4j.circuitbreaker.instances.wetter.*`):
   `sliding-window-size=3`, `minimum-number-of-calls=3`, `failure-rate-threshold=50`,
   `wait-duration-in-open-state=10s`. Lies dabei nach, was HALF_OPEN bedeutet – das ist
   der Teil, den man in der Prüfung erklären muss.
4. Der Test setzt den Schalter vor jedem Lauf mit `reset()` zurück, sonst wäre er nach
   dem dritten Test für die anderen schon offen.
5. Dieselbe Bibliothek liefert `@Retry` (wiederholen, mit Wartezeit) und `@RateLimiter`
   (Aufrufe pro Sekunde begrenzen). Reihenfolge, wenn mehrere an einer Methode stehen:
   Retry innen, Circuit Breaker außen – erst wird wiederholt, dann gezählt.

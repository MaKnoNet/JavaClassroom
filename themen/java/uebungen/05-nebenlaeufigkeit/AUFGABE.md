# Übung 05: Nebenläufigkeit

## Aufgabe

Zwei Teile:

1. **Race Condition beheben.** `ZaehlerTest` lässt 8 Threads je 100 000-mal `erhoehe()`
   aufrufen und erwartet 800 000. Führe den Test aus: Er schlägt fehl, obwohl der Code
   „offensichtlich richtig" aussieht. Mach `Zaehler` thread-sicher – auf zwei Arten, und
   entscheide dich für eine: `synchronized` oder `AtomicInteger`.
2. **ExecutorService einsetzen.** `ParalleleSumme.summe` soll jede Liste in einem eigenen
   Task summieren (`ExecutorService`, `submit`/`Future`) und die Teilergebnisse addieren.
   Pool am Ende schließen.

## Abnahmekriterien

- `./gradlew test` ist grün (drei Tests) – auch beim dritten und vierten Lauf, nicht nur
  einmal zufällig.
- In `ParalleleSumme` gibt es keine `new Thread(...)`-Aufrufe; der Pool wird geschlossen.

## Hinweise

1. `wert++` sind drei Schritte (lesen, +1, schreiben). Zwei Threads dazwischen – eine
   Erhöhung geht verloren. `synchronized` auf der Methode macht die drei Schritte
   unteilbar; `AtomicInteger.incrementAndGet()` erledigt es ohne Sperre.
2. `ExecutorService pool = Executors.newFixedThreadPool(listen.size())`, dann für jede
   Liste `pool.submit(() -> liste.stream().mapToLong(Integer::longValue).sum())` – das
   liefert `Future<Long>`; `future.get()` wartet auf das Ergebnis. `pool.shutdown()` nicht
   vergessen (ab Java 19 ist der Pool `AutoCloseable`: try-with-resources).

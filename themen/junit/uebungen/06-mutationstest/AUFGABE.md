# Übung 06: Mutationstest mit PIT

## Aufgabe

`Rabatt` ist fertig und `RabattTest` ist grün – trotzdem ist der Test schwach. PIT
(Pitest) verändert den Code absichtlich (dreht `>=` in `>`, löscht Aufrufe, ändert
Rückgabewerte) und prüft, ob die Tests das bemerken. Jede unbemerkte Änderung ist ein
„überlebender Mutant".

1. `./gradlew pitest` ausführen und den Bericht `build/reports/pitest/index.html` öffnen.
   Wie viele Mutanten überleben, und welche?
2. Tests in `RabattTest` ergänzen, bis der Mutation-Score mindestens **90 %** erreicht
   und `./gradlew pitest` ohne Fehler endet (der Build bricht unter der Schwelle ab).

`Rabatt.java` bleibt unverändert.

## Abnahmekriterien

- `./gradlew pitest` endet mit `BUILD SUCCESSFUL`.
- Der Bericht zeigt einen Mutation Score von mindestens 90 %.
- Jeder neue Test prüft ein benanntes Verhalten (Schwelle genau 100, Stammkunde ohne
  Mengenrabatt, kein Rabatt, Endpreis).

## Hinweise

1. Überlebende Mutanten stehen im Bericht mit der Art der Änderung, z. B.
   `changed conditional boundary` bei `betrag >= SCHWELLE` – dann fehlt ein Test mit
   genau `100`.
2. Denk an beide Methoden: `endpreis` hat noch gar keinen Test. Und an den Fall ohne
   jeden Rabatt – erst der fängt gelöschte Zuweisungen.

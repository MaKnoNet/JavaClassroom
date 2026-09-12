# Übung 03: Auswertungen mit Streams

## Aufgabe

`Mitarbeiter` ist ein fertiger Record. In `Auswertung.java` werfen vier Methoden noch
`UnsupportedOperationException`. Setze sie **ausschließlich mit Streams** um – keine
`for`- oder `while`-Schleife, keine von Hand befüllte Liste oder Map:

- `namenSortiert` – alle Namen, alphabetisch.
- `namenDerAbteilung` – nur die Namen einer Abteilung, in Listenreihenfolge, mit `", "`
  verbunden; unbekannte Abteilung → leerer String.
- `gehaltssummeProAbteilung` – Map von Abteilung auf Summe der Gehälter.
- `bestverdiener` – der Mitarbeiter mit dem höchsten Gehalt als `Optional`; leere Liste →
  leeres `Optional`.

## Abnahmekriterien

- `./gradlew test` ist grün (sechs Tests in `AuswertungTest`).
- Kein `for`, `while`, `new ArrayList` oder `new HashMap` in `Auswertung.java`.
- Jede Methode besteht aus genau einer Stream-Kette mit `return` davor.

## Hinweise

1. Der Einstieg ist immer `mitarbeiter.stream()`. Danach Zwischenoperationen (`filter`,
   `map`, `sorted`) und am Ende genau eine Endoperation: `toList()`,
   `collect(Collectors.joining(", "))`, `max(...)`.
2. Für die Summe je Abteilung: `Collectors.groupingBy(Mitarbeiter::abteilung,
   Collectors.summingDouble(Mitarbeiter::gehalt))`. Für den Bestverdiener:
   `max(Comparator.comparingDouble(Mitarbeiter::gehalt))` – das gibt bereits ein `Optional`.

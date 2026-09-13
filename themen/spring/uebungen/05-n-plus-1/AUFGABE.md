# Übung 05: Das N+1-Problem

## Aufgabe

`BestellService.stueckJeKunde()` liefert das richtige Ergebnis – der erste Test ist grün.
Der zweite Test zählt, was das kostet: **21 SQL-Anweisungen für 20 Bestellungen.** Eine
für die Bestellungen, dann je Bestellung eine weitere für ihre Positionen, sobald die
Schleife `positionen()` anfasst. Bei 20 Bestellungen ist das lästig, bei 20 000 legt es
die Datenbank lahm.

Sorge dafür, dass der Service mit **einer** Abfrage auskommt – ohne die Entities auf
`EAGER` zu stellen.

## Abnahmekriterien

- `./gradlew test` ist grün (zwei Tests).
- `Bestellung` und `Position` bleiben unverändert; kein `FetchType.EAGER`.
- Die Lösung liegt im Repository (Abfrage) und im Service (Aufruf), nicht im Test.

## Hinweise

1. Starte den Test einmal und lies die `Hibernate: select …`-Zeilen in der Ausgabe: Die
   erste holt die Bestellungen, dann folgt zwanzigmal dieselbe Abfrage mit anderer ID.
   Das ist das Muster, das du im Betrieb erkennen musst.
2. `@Query("select distinct b from Bestellung b left join fetch b.positionen")` im
   Repository – **JOIN FETCH** sagt Hibernate: Hol die Sammlung gleich mit. `distinct`,
   weil der Join je Position eine Zeile liefert.
3. Alternative ohne JPQL: `@EntityGraph(attributePaths = "positionen")` über einer
   Repository-Methode, z. B. einem überschriebenen `findAll()`.
4. Warum nicht `EAGER` an der Entity? Dann werden die Positionen *immer* mitgeladen –
   auch dort, wo niemand sie braucht. Die Fetch-Strategie gehört zur Abfrage, nicht zum
   Modell.
5. Der Test liest `Statistics.getPrepareStatementCount()` aus Hibernate. Dieselbe Zahl
   kannst du im Betrieb über `hibernate.generate_statistics` und das Log bekommen – so
   findet man N+1 in einer echten Anwendung.

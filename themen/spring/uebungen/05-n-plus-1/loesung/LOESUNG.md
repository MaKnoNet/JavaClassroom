# Lösung 05: N+1

Zwei Dateien:

- `BestellungRepository`: `@Query("select distinct b from Bestellung b left join fetch b.positionen") List<Bestellung> findAlleMitPositionen();`
- `BestellService`: `findAll()` → `findAlleMitPositionen()`.

Ergebnis: 1 statt 21 Anweisungen. Die Schleife bleibt gleich – die Positionen sind nur
schon da, wenn sie angefasst werden.

**Warum `distinct`:** Der SQL-Join liefert je Position eine Zeile, also 40 Zeilen für
20 Bestellungen. Ohne `distinct` bekäme man jede Bestellung zweimal in der Liste
(dasselbe Objekt, nicht zwei Kopien – aber die Summe wäre doppelt).

**Alternative Entity Graph** – gleicher Effekt, ohne JPQL:

```java
@EntityGraph(attributePaths = "positionen")
@Override
List<Bestellung> findAll();
```

**Was nicht geht:** `@OneToMany(fetch = FetchType.EAGER)`. Das erschlägt zwar diesen
Test, lädt die Positionen aber bei *jedem* Zugriff auf eine Bestellung mit – auch in
der Kundenliste, die nur Namen zeigt. Die Fetch-Strategie gehört zur Abfrage.

**Grenzen von JOIN FETCH:** Zwei Sammlungen gleichzeitig zu fetchen (`positionen` *und*
`zahlungen`) ergibt ein kartesisches Produkt – Hibernate weigert sich bei `List`
(`MultipleBagFetchException`). Dann `@BatchSize` an der Sammlung oder zwei Abfragen.

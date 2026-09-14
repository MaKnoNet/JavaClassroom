# Übung 06: Dekorierer und Adapter

## Aufgabe

`DatenbankKundendienst` ist teuer. Zwei Unterklassen versuchen, ihn zu verbessern –
eine protokolliert, eine cacht. Wer beides will, braucht eine dritte Klasse; mit jeder
weiteren Eigenschaft verdoppelt sich die Zahl. Der Test will stattdessen zwei
**Dekorierer** `Cachend(Kundendienst)` und `Protokollierend(Kundendienst, Consumer<String>)`,
die dasselbe Interface implementieren und in beliebiger Reihenfolge ineinandergesteckt
werden – und einen **Adapter** `AltesSystemAdapter(AltesKundenSystem)`, der das fremde
System als `Kundendienst` verfügbar macht.

## Abnahmekriterien

- `./gradlew test` ist grün (sechs Tests).
- `Cachend` und `Protokollierend` erben nicht von `DatenbankKundendienst` (Reflection-Test).
- `AltesKundenSystem` bleibt unverändert.

## Hinweise

1. Ein Dekorierer: `implements Kundendienst`, Feld `private final Kundendienst innen`,
   Konstruktor nimmt es entgegen, `kunde(nummer)` tut sein Extra und ruft `innen.kunde(nummer)`.
2. Die beiden Tests mit vertauschter Reihenfolge sind kein Zufall: Protokoll *um* Cache
   sieht jeden Aufruf, Cache *um* Protokoll nur den ersten. Erklären können, warum.
3. Der Adapter: `holeKundenSatz` will einen String und liefert `nr;Name;Status` – der
   Adapter wandelt die Nummer um, ruft das alte System und schneidet das zweite Feld aus.
4. Die alten Unterklassen dürfen bleiben oder weg – der Test braucht sie nicht mehr.
   Im echten Projekt: weg.

# Lösung 06

Zwei Dekorierer (`Cachend`, `Protokollierend`) und ein Adapter (`AltesSystemAdapter`).
Beide Dekorierer implementieren `Kundendienst` und halten ein `Kundendienst innen` –
sie erben *nicht* von `DatenbankKundendienst`. Genau das macht sie kombinierbar: Die zwei
Tests mit vertauschter Reihenfolge zeigen, dass „Protokoll um Cache" und „Cache um
Protokoll" verschiedene Dinge sind – und beide ohne neue Klasse gehen. Mit Vererbung
hätte jede Kombination eine eigene Unterklasse gebraucht (2 Eigenschaften → 3 Klassen,
3 → 7, 4 → 15).

Der Adapter zeigt den Unterschied: Er umhüllt auch, aber er *wechselt* die Schnittstelle
(String rein, CSV raus → int rein, Name raus). Der Dekorierer *behält* sie. Im
Klassendiagramm sieht man es am Pfeil: Der Dekorierer realisiert dasselbe Interface wie
sein Inhalt, der Adapter ein anderes als seine Quelle.

`java.io` ist das Vorbild: `new BufferedReader(new InputStreamReader(System.in))` – zwei
Dekorierer um einen Stream, seit Java 1.1.

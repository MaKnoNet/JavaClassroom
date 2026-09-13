# Übung 07: Der Automat, der sich nicht testen lässt

## Aufgabe

`GetraenkeAutomat` baut sich seine `KartenBezahlung` im Konstruktor selbst mit `new`. Im
Test gibt es kein Kartenterminal – jeder Kauf endet mit `IllegalStateException`, und der
Test `GetraenkeAutomatTest` **kompiliert nicht einmal**: Er verlangt ein Interface
`BezahlService` und einen Konstruktor, der es entgegennimmt. Baue um:

1. Interface `BezahlService` mit `boolean belaste(double betrag)` anlegen.
2. `KartenBezahlung implements BezahlService` – am Verhalten ändert sich nichts.
3. `GetraenkeAutomat` bekommt den `BezahlService` per Konstruktor und hält ihn in einem
   `final`-Feld. Kein `new KartenBezahlung()` mehr in dieser Klasse.

## Abnahmekriterien

- `./gradlew test` ist grün (vier Tests).
- `GetraenkeAutomat` enthält weder `new KartenBezahlung` noch das Wort `KartenBezahlung`.
- `KartenBezahlung` bleibt inhaltlich unverändert – nur `implements` und `@Override` kommen dazu.

## Hinweise

1. Das Interface braucht genau eine Methode – die, die der Automat tatsächlich aufruft.
   Nicht mehr: Was der Automat nicht braucht, gehört nicht in seine Schnittstelle.
2. Der Test baut den Automaten mit `new GetraenkeAutomat(bezahlung)`, wobei `bezahlung`
   ein Mockito-Mock ist (JUnit-Lektion 08). Genau das ist der Gewinn: Der Test entscheidet,
   wie „bezahlen" sich verhält – ohne Terminal, ohne Netz.
3. Dieselbe Idee kommt in Spring-Lektion 01 wieder – dort übernimmt der Container das `new`.

# Übung 01: Der Automat im Container

## Aufgabe

Derselbe Getränkeautomat wie in Java-Übung 07 – aber jetzt soll **Spring** ihn bauen. Der
Test `GetraenkeAutomatTest` startet den Container (`@SpringBootTest`), lässt sich den
Automaten geben (`@Autowired`) und ersetzt die Bezahlung durch einen Mock
(`@MockitoBean`). Im Moment scheitert er: Spring kennt weder den Automaten noch die
Kartenbezahlung, und der Automat baut sich seine Bezahlung noch selbst.

1. `KartenBezahlung` mit `@Service` als Komponente registrieren.
2. `GetraenkeAutomat` ebenfalls – und die Bezahlung per **Konstruktor** entgegennehmen
   statt sie mit `new` zu erzeugen. Das Feld bleibt `final`.
3. `AutomatAnwendung` ist fertig: `@SpringBootApplication` schaltet das
   Komponenten-Scanning für `de.makno.lernen` ein.

## Abnahmekriterien

- `./gradlew test` ist grün (drei Tests).
- `GetraenkeAutomat` enthält kein `new` für die Bezahlung und kein `@Autowired` an einem
  Feld.
- Beide Klassen tragen `@Service`.

## Hinweise

1. Bei genau einem Konstruktor braucht Spring kein `@Autowired` – es nimmt ihn
   automatisch und sucht für jeden Parameter eine passende Bean.
2. Fehlermeldung lesen: `No qualifying bean of type 'de.makno.lernen.GetraenkeAutomat'`
   heißt „nicht registriert"; `Parameter 0 of constructor … required a bean of type
   'BezahlService' that could not be found` hieße „der Automat ist da, aber seine
   Abhängigkeit nicht".
3. Der dritte Test prüft, dass `kartenBezahlung` als Bean existiert, obwohl der Mock sie
   im Test ersetzt: Im Betrieb gibt es keinen Mock – dann muss die echte Klasse da sein.

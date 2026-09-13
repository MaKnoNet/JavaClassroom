# Übung 03: Abhängigkeiten mocken

## Aufgabe

`Bestellservice` braucht ein `Lager` und eine `Benachrichtigung` – im echten System eine
Datenbank und ein E-Mail-Versand. Beides gibt es in der Übung nicht, und beides soll im
Unit-Test auch nicht laufen. Schreibe in `BestellserviceTest` mindestens drei Tests, die
beide Abhängigkeiten mit **Mockito** ersetzen:

1. Artikel verfügbar → `bestelle` liefert `true`, `lager.entnehme` wurde mit Artikel und
   Menge aufgerufen, der Kunde bekam `Bestellung bestätigt: 2 x Schraube`.
2. Artikel nicht verfügbar → `bestelle` liefert `false`, `lager.entnehme` wurde **nie**
   aufgerufen, der Kunde bekam `Leider nicht verfügbar: Schraube`.
3. Ein Test deiner Wahl – zum Beispiel: Die Verfügbarkeit wird mit genau der bestellten
   Menge abgefragt.

## Abnahmekriterien

- `./gradlew test` ist grün.
- `build/test-results/test/TEST-de.makno.lernen.BestellserviceTest.xml` enthält `tests="3"`
  (oder mehr) und `failures="0"`.
- Keine eigene Klasse, die `Lager` oder `Benachrichtigung` implementiert – die Doubles
  kommen von Mockito.

## Hinweise

1. `Lager lager = mock(Lager.class);` (statischer Import `org.mockito.Mockito.*`).
   Antworten festlegen: `when(lager.istVerfuegbar("Schraube", 2)).thenReturn(true);`
   Ohne `when` liefert ein Mock für `boolean` immer `false` – das ist der zweite Fall
   gratis.
2. Aufrufe prüfen: `verify(lager).entnehme("Schraube", 2);` und
   `verify(lager, never()).entnehme(anyString(), anyInt());`
   (`anyString`, `anyInt` aus `org.mockito.ArgumentMatchers`).

# Übung 03: Beobachter

## Aufgabe

`Lager` erzeugt sich `Anzeige` und `Protokoll` selbst und ruft beide bei jeder Änderung
direkt auf. Der Test will das Lager als **Subjekt**, das Beobachter benachrichtigt, die es
nicht kennt: ein Record `BestandsEreignis(String artikel, int bestand)`, ein Interface
`BestandsBeobachter` mit `void bestandGeaendert(BestandsEreignis ereignis)`, im Lager
`registriere(…)` und `entferne(…)`. `Anzeige` und `Protokoll` werden zu gewöhnlichen
Beobachtern, die von außen registriert werden.

## Abnahmekriterien

- `./gradlew test` ist grün (fünf Tests).
- `Lager` hat kein Feld vom Typ `Anzeige` oder `Protokoll` – der Test prüft das per
  Reflection.
- Wirft ein Beobachter eine Exception, bekommen die übrigen das Ereignis trotzdem, und
  der Bestand ist geändert.

## Hinweise

1. Beobachter in einer Liste halten; `benachrichtige` baut das Ereignis einmal und ruft
   jeden Beobachter damit. Das Interface mit einer Methode – dann sind Lambdas erlaubt
   (der Test nutzt sie).
2. Für den Fehlerfall: `try`/`catch (RuntimeException e)` um *jeden einzelnen* Aufruf –
   nicht um die Schleife. Im Betrieb würde man loggen (Java-Lektion 15).
3. `Anzeige` und `Protokoll`: `implements BestandsBeobachter`, die alte Methode wird zu
   `bestandGeaendert`. Ihre Getter bleiben.
4. Wer sich im Ereignis selbst abmelden will, ändert die Liste während der Schleife.
   `CopyOnWriteArrayList` aus `java.util.concurrent` erlaubt genau das.
5. Dasselbe Muster hast du in JavaScript schon benutzt: `element.addEventListener("click", …)`
   ist `registriere`, das Event ist das `BestandsEreignis`.

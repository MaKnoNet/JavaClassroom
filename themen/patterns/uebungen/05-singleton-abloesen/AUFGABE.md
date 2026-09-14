# Übung 05: Singleton ablösen

## Aufgabe

`Konfiguration` ist ein Singleton, `Preisrechner` holt sie sich per
`Konfiguration.getInstance()`. Der Test will den Steuersatz variieren (19 %, 7 %, 8 %) und
zwei Rechner mit verschiedenen Konfigurationen nebeneinander betreiben – mit dem Singleton
unmöglich. Löse es ab: `Konfiguration` wird ein gewöhnliches Wertobjekt mit öffentlichem
Konstruktor `(int mwstProzent, String waehrung)`, `Preisrechner` bekommt es per
Konstruktor.

## Abnahmekriterien

- `./gradlew test` ist grün (vier Tests).
- `Konfiguration` hat kein `getInstance()` und kein statisches Feld mit einer Instanz
  (Reflection-Test); `Preisrechner` ruft nirgends `getInstance()`.

## Hinweise

1. Ein Record reicht: `public record Konfiguration(int mwstProzent, String waehrung) {}` –
   die Zugriffsmethoden heißen dann genauso wie vorher.
2. `Preisrechner`: Feld `private final Konfiguration konfiguration`, Konstruktor, und in
   `brutto`/`formatiert` das Feld statt `getInstance()`.
3. Das ist derselbe Umbau wie in Java-Übung 07 (`BezahlService`) – dort ging es um
   Testbarkeit mit Mockito, hier darum, dass „eine Instanz" nicht heißen muss „die Klasse
   verwaltet sie selbst".
4. Wer im Betrieb genau eine Konfiguration will: eine Bean im Spring-Container
   (Spring-Lektion 01). Der Container ist das Singleton, die Klasse weiß nichts davon.

# Übung 04: Builder

## Aufgabe

`new Bestellung("Anna", "Weg 1", null, true, 0, null)` – wer weiß, was das vierte
Argument bedeutet? Der Test will Bestellungen so erzeugen:
`Bestellung.fuer("Anna").lieferadresse("Weg 1").express().rabatt(10).build()`. Baue den
Builder: Kunde ist Pflicht (Einstieg), Lieferadresse ist Pflicht (geprüft in `build()`),
Rechnungsadresse ist standardmäßig die Lieferadresse, Express standardmäßig aus, Rabatt 0
und nur zwischen 0 und 100, Gutschein optional. Das Ergebnis ist unveränderlich.

## Abnahmekriterien

- `./gradlew test` ist grün (fünf Tests).
- `Bestellung` hat keinen öffentlichen Konstruktor und keine Setter (Reflection-Test).
- Fehlende Lieferadresse → `IllegalStateException` in `build()`; ungültiger Rabatt →
  `IllegalArgumentException` sofort beim Setzen.

## Hinweise

1. `public static Builder fuer(String kunde)` in `Bestellung`; der Builder ist eine
   `public static final class Builder` darin, mit privatem Konstruktor. So kommt man nur
   über `fuer` hinein.
2. Jede Builder-Methode setzt ein Feld und gibt `this` zurück – das ermöglicht die Kette.
3. `build()` ruft den privaten Konstruktor `Bestellung(Builder b)`, der die Werte
   übernimmt und die Rechnungsadresse ableitet.
4. Prüfen an der richtigen Stelle: Was ein einzelner Wert allein verletzen kann (Rabatt),
   sofort; was nur im Ganzen prüfbar ist (Pflichtfelder), in `build()`.

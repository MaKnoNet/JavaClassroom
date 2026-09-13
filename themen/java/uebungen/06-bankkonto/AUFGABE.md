# Übung 06: Kapselung – das Bankkonto

## Aufgabe

`Bankkonto` hat zwei öffentliche Felder. Jeder Code kann `konto.kontostand = -1000000`
schreiben, und niemand merkt es. Baue die Klasse so um, dass der Test kompiliert und
grün wird:

1. Felder `private`; Inhaber über den Konstruktor `Bankkonto(String inhaber)` setzen.
2. Getter `inhaber()` und `kontostand()` – kein Setter für den Kontostand.
3. `einzahlen(double betrag)`: Beträge ≤ 0 werfen `IllegalArgumentException`, der
   Kontostand bleibt unverändert.
4. `abheben(double betrag)`: Beträge ≤ 0 werfen `IllegalArgumentException`; ist der
   Betrag größer als der Kontostand, `IllegalStateException` – ebenfalls ohne Änderung.

## Abnahmekriterien

- `./gradlew test` ist grün (vier Tests).
- Kein Feld ist `public`; es gibt keine Methode, die den Kontostand direkt setzt.

## Hinweise

1. Der Zustand wird nur an zwei Stellen verändert (`einzahlen`, `abheben`), und beide
   prüfen zuerst. Das ist Kapselung: Die Klasse garantiert selbst, dass ihr Zustand
   sinnvoll bleibt – egal, wer sie benutzt.
2. Prüfen vor dem Ändern (Guard Clause): `if (betrag <= 0) throw new
   IllegalArgumentException("Betrag muss positiv sein");` – erst danach `kontostand += betrag`.

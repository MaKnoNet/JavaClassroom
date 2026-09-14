# Übung 02: Strategie

## Aufgabe

`Versandkosten.berechne` entscheidet per `switch` über einen Enum. Der Test will die
Versandart als **Strategie**: ein Interface `VersandStrategie` mit `double kosten(double
gewichtKg)`, je eine Klasse `StandardVersand`, `ExpressVersand`, `Abholung`, und einen
Kontext `Versandkosten`, der die Strategie im Konstruktor bekommt und in `berechne(gewicht)`
nur delegiert. Der letzte Test definiert eine Versandart, die es im Startcode nicht gibt –
als Lambda – und erwartet, dass `Versandkosten` sie unverändert verarbeitet.

Regeln: Standard 4,99 € plus 1,00 € je Kilo über 5 kg; Express 9,99 € plus 2,50 € je Kilo
über 5 kg; Abholung 0 €.

## Abnahmekriterien

- `./gradlew test` ist grün (vier Tests).
- `Versandkosten` enthält weder `switch` noch `if` über die Versandart und kennt keine
  konkrete Strategieklasse.
- Die Zuschlagsregel steht nur einmal (DRY) – ohne Vererbung zwischen den Strategien.

## Hinweise

1. Das Interface braucht genau eine Methode. Mit `@FunctionalInterface` markieren – dann
   geht das Lambda aus dem Test, und der Compiler passt auf, dass es bei einer Methode bleibt.
2. Der Kontext: ein `private final VersandStrategie strategie`, Konstruktor, `berechne`
   ruft `strategie.kosten(gewichtKg)`. Mehr nicht.
3. Gemeinsamen Code (Zuschlag ab 5 kg) in eine kleine Hilfsklasse mit statischer Methode
   legen, die beide Strategien aufrufen – nicht in eine gemeinsame Oberklasse.
4. Klassendiagramm zum Mitzeichnen: `Versandkosten ──► <<interface>> VersandStrategie`,
   darunter drei gestrichelte Pfeile mit Dreieck von den Klassen zum Interface.

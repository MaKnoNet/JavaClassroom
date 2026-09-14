# Übung 02: Klassenstruktur

## Aufgabe

`src/main/java/de/makno/lernen/Konto.java` ist die Vorlage. Bilde sie in `diagramm.puml`
exakt ab: jedes Feld, jede Methode, der Konstruktor – mit der richtigen Sichtbarkeit
(`-` private, `+` public, `#` protected, `~` package-private), Parametern als `name: Typ`
und Rückgabetyp nach dem Doppelpunkt; das statische Feld mit `{static}`.

## Abnahmekriterien

- `./gradlew test` ist grün (vier Tests) – die Tests vergleichen Zeile für Zeile.
- Das gerenderte Diagramm zeigt zwei Fächer: Attribute oben, Methoden unten.

## Hinweise

1. Eine Zeile je Element, z. B. `- stand: double` und `+ einzahlen(betrag: double): void`.
   Leerzeichen genau so: Sichtbarkeit, Leerzeichen, Name.
2. Der Konstruktor: `+ Konto(inhaber: String)` – ohne Rückgabetyp.
3. `+ {static} MAX_STAND: double` – PlantUML unterstreicht es beim Rendern.
4. Ein Test sagt dir, welche Zeile fehlt oder falsch ist; rendern hilft zu sehen, ob
   PlantUML ein Element als Methode (mit Klammern) oder Attribut erkannt hat.

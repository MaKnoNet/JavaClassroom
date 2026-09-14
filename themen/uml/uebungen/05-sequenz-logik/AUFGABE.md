# Übung 05: Schleifen und Bedingungen

## Aufgabe

Der Anfang steht: Der Controller ruft `bestelle(bestellung)` am Service. Zeichne den Rest
nach der Beschreibung in `diagramm.puml`: eine `loop`-Schleife über die Positionen mit dem
Lageraufruf, dann `alt`/`else` – im Fehlerfall die Exception als Antwort an den
Controller, im Erfolgsfall `speichere(bestellung)` am Repository und die Bestellnummer
zurück.

## Abnahmekriterien

- `./gradlew test` ist grün (fünf Tests): `loop` mit Bedingung, `alt`/`else`, Exception
  als gestrichelte Antwort, Speichern und Antwort, jeder Rahmen mit `end` geschlossen,
  Schleife vor der Entscheidung.

## Hinweise

1. Rahmen: `loop für jede Position` … `end`; `alt Bedingung` … `else andere Bedingung` … `end`.
   Einrücken hilft dem Auge, PlantUML ist es egal.
2. Die Bedingung im `loop`-Kopf muss das Wort „Position" enthalten – so weiß der Leser,
   worüber iteriert wird.
3. Es gibt keinen Exception-Pfeil: `BestellService --> BestellController : BestellungUngueltigException`
   ist die übliche Schreibweise.
4. `activate`/`deactivate` wie in Übung 04 – der Service bleibt bis zum Ende aktiv.

# Übung 06: Zustandsautomat

## Aufgabe

Zeichne den Lebenszyklus eines Support-Tickets nach der Beschreibung in `diagramm.puml`:
Start, vier Übergänge mit Ereignis, Bedingung und Aktion in der UML-Notation, die interne
Aktivität `do / bearbeite` im Zustand InBearbeitung, Endzustand.

## Abnahmekriterien

- `./gradlew test` ist grün (fünf Tests): Diagrammtyp, Start und Ende, der Übergang mit
  Bedingung und Aktion in exakter Notation, die übrigen Übergänge, die interne Aktivität.

## Hinweise

1. Übergang: `Offen --> InBearbeitung : zuweisen [Bearbeiter gesetzt] / benachrichtigeBearbeiter`
   – Ereignis, dann Bedingung in eckigen Klammern, dann Schrägstrich und Aktion.
2. Interne Aktivität: `InBearbeitung : do / bearbeite` – eine Zeile mit dem Zustandsnamen,
   Doppelpunkt, Text.
3. Ende: `Geschlossen --> [*]`.
4. Rendern: Der Startpunkt ist ein schwarzer Kreis, das Ende ein Doppelkreis. Wenn
   PlantUML ein Klassendiagramm daraus macht, fehlt `[*]` am Anfang.

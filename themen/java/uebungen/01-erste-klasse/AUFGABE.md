# Übung 01: Erste Klasse

## Aufgabe

In `src/main/java/de/makno/lernen/Begruessung.java` fehlt der Inhalt der Methode
`begruesse`. Sie soll für einen Namen den Text `Hallo, <Name>!` liefern. Ist der Name
leer oder `null`, soll `Hallo, Unbekannt!` herauskommen.

Zusätzlich soll `main` die Begrüßung für „Welt" auf der Konsole ausgeben.

## Abnahmekriterien

- `./gradlew test` ist grün (beide Tests in `BegruessungTest`).
- Das Programm lässt sich in der IDE starten und gibt `Hallo, Welt!` aus.

## Hinweise

1. Schau in den Test: Er zeigt genau, welche Eingabe welche Ausgabe erwartet.
2. Strings verbindet man mit `+`. Ob ein String leer ist, sagt `name.isEmpty()` – aber
   Vorsicht: bei `null` wirft das eine Exception, prüfe `null` zuerst.

Warum so: Jede Methode ist eine Kette aus Zwischenoperationen (`filter`, `map`, `sorted`)
und genau einer Endoperation (`toList`, `collect`, `max`). `groupingBy` mit `summingDouble`
ersetzt die typische Map-Schleife mit `getOrDefault`. `max` liefert `Optional`, damit der
leere Fall im Typ sichtbar ist statt als `null`. Methodenreferenzen (`Mitarbeiter::name`)
sind kürzer als `m -> m.name()` und sagen dasselbe.
Die Datei ersetzt `src/main/java/de/makno/lernen/Auswertung.java`.

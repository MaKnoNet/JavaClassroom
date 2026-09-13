Warum so: Der leere `catch` war kein Stilproblem, sondern ein verstecktes `null`-Handling –
die Lösung prüft `null` direkt, statt eine Exception zu provozieren und zu verschlucken.
Die übrigen vier Verstöße sind reine Form, aber genau die Art, über die Reviews sonst
Zeit verlieren; Checkstyle nimmt sie dem Review ab. Die Datei ersetzt
`src/main/java/de/makno/lernen/Rechner.java`.

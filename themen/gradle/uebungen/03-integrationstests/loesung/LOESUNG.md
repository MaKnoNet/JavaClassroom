Warum so: Ein Filter auf `.class`-Dateien statt auf Quellen, weil Gradle die kompilierten
Klassen ausführt. Der zweite Task teilt sich Klassen und Classpath mit `test`, hat aber
eigene Ergebnisse unter `build/test-results/integrationTest/`. `shouldRunAfter test`
sorgt für die sinnvolle Reihenfolge, `check.dependsOn` dafür, dass die Pipeline mit einem
Befehl beides bekommt. In größeren Projekten nimmt man ein eigenes Source-Set
(`src/integrationTest/java`); die Namenskonvention reicht für den Anfang.
Die Datei ersetzt `build.gradle`.

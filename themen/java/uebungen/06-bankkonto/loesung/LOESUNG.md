Warum so: `inhaber` ist `final` – er ändert sich nie, also gibt es keinen Setter. Der
Kontostand ändert sich nur über zwei Methoden, die beide prüfen, bevor sie schreiben; die
gemeinsame Prüfung steckt in einer privaten Hilfsmethode (DRY). `IllegalArgumentException`
für falsche Eingaben, `IllegalStateException` für „geht gerade nicht" – zwei verschiedene
Fehlerarten, zwei Exceptions. Die Datei ersetzt `src/main/java/de/makno/lernen/Bankkonto.java`.

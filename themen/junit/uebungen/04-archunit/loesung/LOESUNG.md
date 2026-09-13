Warum so: Die Regel steht als Test im Repository – sie läuft bei jedem Build und in der
Pipeline, niemand muss sie im Review im Kopf haben. `because(...)` macht die Fehlermeldung
lesbar. Die Reparatur zeigt das Prinzip: Fachlogik liefert Werte, die Oberfläche
entscheidet über die Darstellung. `ArchitekturTest.java` ersetzt die Datei unter
`src/test/java/de/makno/lernen/`, `Preisrechner.java` die unter
`src/main/java/de/makno/lernen/service/`.

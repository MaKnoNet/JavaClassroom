package de.makno.lernen;

/** Zeigt den Bestand an – steht hier für jede Oberfläche (Vaadin-Grid, Konsole, Dashboard). */
public class Anzeige {

    private String letzteZeile = "";

    public void zeige(String artikel, int bestand) {
        letzteZeile = artikel + ": " + bestand + " Stück";
    }

    public String letzteZeile() {
        return letzteZeile;
    }
}

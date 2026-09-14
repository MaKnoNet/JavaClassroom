package de.makno.lernen;

/** Zeigt den Bestand an – jetzt ein Beobachter wie jeder andere. */
public class Anzeige implements BestandsBeobachter {

    private String letzteZeile = "";

    @Override
    public void bestandGeaendert(BestandsEreignis ereignis) {
        letzteZeile = ereignis.artikel() + ": " + ereignis.bestand() + " Stück";
    }

    public String letzteZeile() {
        return letzteZeile;
    }
}

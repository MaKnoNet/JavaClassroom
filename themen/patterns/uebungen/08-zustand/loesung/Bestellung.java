package de.makno.lernen;

/** Die Bestellung kennt keine Regeln mehr – sie fragt ihren Zustand und übernimmt den Folgezustand. */
public class Bestellung {

    private BestellZustand zustand = new Neu();

    public void bezahle() {
        zustand = zustand.bezahlen();
    }

    public void versende() {
        zustand = zustand.versenden();
    }

    public void storniere() {
        zustand = zustand.stornieren();
    }

    public void retourniere() {
        zustand = zustand.retournieren();
    }

    public String status() {
        return zustand.name();
    }
}

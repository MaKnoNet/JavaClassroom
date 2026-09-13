package de.makno.lernen;

/** Ein Bankkonto, das seinen Zustand selbst schützt. */
public class Bankkonto {

    private final String inhaber;
    private double kontostand;

    public Bankkonto(String inhaber) {
        this.inhaber = inhaber;
    }

    public String inhaber() {
        return inhaber;
    }

    public double kontostand() {
        return kontostand;
    }

    public void einzahlen(double betrag) {
        pruefePositiv(betrag);
        kontostand += betrag;
    }

    public void abheben(double betrag) {
        pruefePositiv(betrag);
        if (betrag > kontostand) {
            throw new IllegalStateException("Kontostand reicht nicht: " + kontostand);
        }
        kontostand -= betrag;
    }

    private static void pruefePositiv(double betrag) {
        if (betrag <= 0) {
            throw new IllegalArgumentException("Betrag muss positiv sein: " + betrag);
        }
    }
}

package de.makno.lernen;

/** Rabattregeln: ab 100 € gibt es 10 %, Stammkunden 5 % zusätzlich, nie mehr als 15 %. */
public final class Rabatt {

    private static final double SCHWELLE = 100.0;
    private static final double MENGENRABATT = 0.10;
    private static final double STAMMKUNDENRABATT = 0.05;
    private static final double MAXIMUM = 0.15;

    private Rabatt() {}

    public static double prozent(double betrag, boolean stammkunde) {
        double rabatt = 0.0;
        if (betrag >= SCHWELLE) {
            rabatt += MENGENRABATT;
        }
        if (stammkunde) {
            rabatt += STAMMKUNDENRABATT;
        }
        return Math.min(rabatt, MAXIMUM);
    }

    public static double endpreis(double betrag, boolean stammkunde) {
        return betrag * (1 - prozent(betrag, stammkunde));
    }
}

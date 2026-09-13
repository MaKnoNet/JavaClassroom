package de.makno.lernen.service;

/** Fachlogik – gibt zurück, zeigt nichts an. */
public class Preisrechner {

    private static final double MEHRWERTSTEUER = 0.19;

    public double brutto(double netto) {
        return netto * (1 + MEHRWERTSTEUER);
    }
}

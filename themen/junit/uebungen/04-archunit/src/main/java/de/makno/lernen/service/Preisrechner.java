package de.makno.lernen.service;

import de.makno.lernen.ui.Konsole;

/** Fachlogik. Enthält absichtlich eine Abhängigkeit in die falsche Richtung. */
public class Preisrechner {

    private static final double MEHRWERTSTEUER = 0.19;

    public double brutto(double netto) {
        double brutto = netto * (1 + MEHRWERTSTEUER);
        Konsole.zeige("Brutto: " + brutto);
        return brutto;
    }
}

package de.makno.lernen;

/**
 * Versandkosten je Versandart. Jede neue Art bedeutet: ein neuer Enum-Wert, ein neuer
 * Zweig im switch – und jeder, der diese Klasse benutzt, muss neu ausgeliefert werden.
 */
public class Versandkosten {

    public enum Versandart { STANDARD, EXPRESS, ABHOLUNG }

    public double berechne(Versandart art, double gewichtKg) {
        switch (art) {
            case STANDARD:
                return 4.99 + zuschlagAb5Kg(gewichtKg, 1.00);
            case EXPRESS:
                return 9.99 + zuschlagAb5Kg(gewichtKg, 2.50);
            case ABHOLUNG:
                return 0.0;
            default:
                throw new IllegalArgumentException("Unbekannte Versandart: " + art);
        }
    }

    private static double zuschlagAb5Kg(double gewichtKg, double proKg) {
        return gewichtKg > 5 ? (gewichtKg - 5) * proKg : 0;
    }
}

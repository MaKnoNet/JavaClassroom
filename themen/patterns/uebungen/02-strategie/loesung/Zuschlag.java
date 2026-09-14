package de.makno.lernen;

/** Gemeinsame Rechenregel der Strategien – DRY, ohne dass sie voneinander erben müssen. */
final class Zuschlag {

    private Zuschlag() {}

    static double ab5Kg(double gewichtKg, double proKg) {
        return gewichtKg > 5 ? (gewichtKg - 5) * proKg : 0;
    }
}

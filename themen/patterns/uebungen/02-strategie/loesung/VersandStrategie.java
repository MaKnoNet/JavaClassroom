package de.makno.lernen;

/** Eine Methode – deshalb geht auch ein Lambda als Strategie. */
@FunctionalInterface
public interface VersandStrategie {

    double kosten(double gewichtKg);
}

package de.makno.lernen;

public class StandardVersand implements VersandStrategie {

    @Override
    public double kosten(double gewichtKg) {
        return 4.99 + Zuschlag.ab5Kg(gewichtKg, 1.00);
    }
}

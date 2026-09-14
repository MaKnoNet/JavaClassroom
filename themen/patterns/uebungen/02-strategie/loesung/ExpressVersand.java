package de.makno.lernen;

public class ExpressVersand implements VersandStrategie {

    @Override
    public double kosten(double gewichtKg) {
        return 9.99 + Zuschlag.ab5Kg(gewichtKg, 2.50);
    }
}

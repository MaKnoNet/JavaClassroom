package de.makno.lernen;

public class Rechner {

    public double addiere(double a, double b) {
        return a + b;
    }

    public double subtrahiere(double a, double b) {
        return a - b;
    }

    public double multipliziere(double a, double b) {
        return a * b;
    }

    public double dividiere(double a, double b) {
        if (b == 0) {
            throw new IllegalArgumentException("Division durch 0");
        }
        return a / b;
    }
}

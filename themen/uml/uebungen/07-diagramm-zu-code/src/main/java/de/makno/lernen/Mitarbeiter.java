package de.makno.lernen;

public class Mitarbeiter {

    private final String name;
    private final double grundgehalt;

    public Mitarbeiter(String name, double grundgehalt) {
        this.name = name;
        this.grundgehalt = grundgehalt;
    }

    public String name() {
        return name;
    }

    public double grundgehalt() {
        return grundgehalt;
    }
}

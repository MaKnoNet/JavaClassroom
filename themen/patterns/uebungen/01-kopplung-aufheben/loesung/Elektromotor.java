package de.makno.lernen;

public class Elektromotor implements Antrieb {

    @Override
    public String starte() {
        return "Elektromotor summt";
    }

    @Override
    public int reichweiteKm() {
        return 350;
    }
}

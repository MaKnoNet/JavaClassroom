package de.makno.lernen;

public class BenzinMotor implements Antrieb {

    @Override
    public String starte() {
        return "Benzinmotor läuft";
    }

    @Override
    public int reichweiteKm() {
        return 600;
    }
}

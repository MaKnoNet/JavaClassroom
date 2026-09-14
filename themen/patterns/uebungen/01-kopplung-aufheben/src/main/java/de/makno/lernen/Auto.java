package de.makno.lernen;

/** Ein Auto mit fest eingebautem Benzinmotor – das "new" im Feld ist die harte Kopplung. */
public class Auto {

    private final BenzinMotor motor = new BenzinMotor();

    public String fahre() {
        return motor.starte() + " – Reichweite " + motor.reichweiteKm() + " km";
    }
}

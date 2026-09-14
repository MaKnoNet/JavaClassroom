package de.makno.lernen;

/**
 * Der Versuch, den Motor per Vererbung zu tauschen: eine Unterklasse je Antrieb. Für den
 * Hybrid bräuchte es eine dritte Klasse, die den Code beider kopiert.
 */
public class ElektroAuto extends Auto {

    private final Elektromotor motor = new Elektromotor();

    @Override
    public String fahre() {
        return motor.starte() + " – Reichweite " + motor.reichweiteKm() + " km";
    }
}

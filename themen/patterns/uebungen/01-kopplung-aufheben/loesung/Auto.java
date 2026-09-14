package de.makno.lernen;

/** Das Auto HAT einen Antrieb und bekommt ihn von außen – Dependency Injection per Konstruktor. */
public class Auto {

    private final Antrieb antrieb;

    public Auto(Antrieb antrieb) {
        this.antrieb = antrieb;
    }

    public String fahre() {
        return antrieb.starte() + " – Reichweite " + antrieb.reichweiteKm() + " km";
    }
}

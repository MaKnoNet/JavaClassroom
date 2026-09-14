package de.makno.lernen;

/** Ein fremdes, altes System mit eigener Schnittstelle – darf nicht geändert werden. */
public class AltesKundenSystem {

    public String holeKundenSatz(String kundenNr) {
        return kundenNr + ";Kunde " + kundenNr + ";AKTIV";
    }
}

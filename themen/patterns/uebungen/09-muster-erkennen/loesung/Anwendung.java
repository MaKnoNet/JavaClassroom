package de.makno.lernen;

public class Anwendung {

    private final Begruessung begruessung = new Begruessung();

    public String willkommen(String name) {
        return begruessung.begruesse(name);
    }
}

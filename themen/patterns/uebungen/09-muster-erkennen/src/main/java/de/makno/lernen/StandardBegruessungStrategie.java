package de.makno.lernen;

public class StandardBegruessungStrategie implements BegruessungStrategie {

    @Override
    public String begruesse(String name) {
        return "Hallo " + name + "!";
    }
}

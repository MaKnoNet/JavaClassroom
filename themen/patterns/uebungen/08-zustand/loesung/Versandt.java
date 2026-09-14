package de.makno.lernen;

public class Versandt implements BestellZustand {

    @Override
    public String name() {
        return "VERSANDT";
    }

    @Override
    public BestellZustand retournieren() {
        return new Retourniert();
    }
}

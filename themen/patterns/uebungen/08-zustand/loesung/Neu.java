package de.makno.lernen;

public class Neu implements BestellZustand {

    @Override
    public String name() {
        return "NEU";
    }

    @Override
    public BestellZustand bezahlen() {
        return new Bezahlt();
    }

    @Override
    public BestellZustand stornieren() {
        return new Storniert();
    }
}

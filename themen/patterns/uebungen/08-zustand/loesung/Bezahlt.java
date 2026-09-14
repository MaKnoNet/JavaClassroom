package de.makno.lernen;

public class Bezahlt implements BestellZustand {

    @Override
    public String name() {
        return "BEZAHLT";
    }

    @Override
    public BestellZustand versenden() {
        return new Versandt();
    }

    @Override
    public BestellZustand stornieren() {
        return new Storniert();
    }
}

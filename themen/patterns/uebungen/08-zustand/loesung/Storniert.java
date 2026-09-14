package de.makno.lernen;

/** Endzustand: erlaubt keinen Übergang – die defaults des Interface werfen. */
public class Storniert implements BestellZustand {

    @Override
    public String name() {
        return "STORNIERT";
    }
}

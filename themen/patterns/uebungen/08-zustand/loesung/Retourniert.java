package de.makno.lernen;

/** Der neue Zustand: eine Klasse und ein Übergang in Versandt – sonst nichts. */
public class Retourniert implements BestellZustand {

    @Override
    public String name() {
        return "RETOURNIERT";
    }
}

package de.makno.lernen;

/**
 * Klassisches Singleton: private Konstruktor, eine Instanz, globaler Zugriff. Wer sie
 * benutzt, holt sie sich still per getInstance() – nichts im Konstruktor verrät das.
 */
public final class Konfiguration {

    private static final Konfiguration INSTANZ = new Konfiguration();

    private final int mwstProzent = 19;
    private final String waehrung = "EUR";

    private Konfiguration() {}

    public static Konfiguration getInstance() {
        return INSTANZ;
    }

    public int mwstProzent() {
        return mwstProzent;
    }

    public String waehrung() {
        return waehrung;
    }
}

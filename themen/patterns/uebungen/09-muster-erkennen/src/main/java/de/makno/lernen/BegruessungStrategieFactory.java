package de.makno.lernen;

/**
 * Teil 2: Muster-Overkill. Eine Begrüßung – mit Singleton, Fabrik und Strategie. Vier
 * Klassen für eine Zeile. Baue es auf das zurück, was gebraucht wird: eine Klasse
 * Begruessung mit begruesse(name).
 */
public final class BegruessungStrategieFactory {

    private static final BegruessungStrategieFactory INSTANZ = new BegruessungStrategieFactory();

    private BegruessungStrategieFactory() {}

    public static BegruessungStrategieFactory getInstance() {
        return INSTANZ;
    }

    public BegruessungStrategie erzeugeStrategie() {
        return new StandardBegruessungStrategie();
    }
}

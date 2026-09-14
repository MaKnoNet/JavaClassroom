package de.makno.lernen;

/** So wird die Begrüßung heute benutzt. Die einzige Variante, die es je gab. */
public class Anwendung {

    public String willkommen(String name) {
        return BegruessungStrategieFactory.getInstance().erzeugeStrategie().begruesse(name);
    }
}

package de.makno.lernen;

import java.io.IOException;

/** Die andere Richtung: Wir sind der Client und holen Wetter von einem fremden Server. */
public class WetterAbruf {

    private final String basisUrl;

    public WetterAbruf(String basisUrl) {
        this.basisUrl = basisUrl;
    }

    /**
     * Ruft GET {basisUrl}/api/wetter/{stadt} auf und wandelt das JSON in ein {@link Wetter}.
     *
     * @throws IllegalStateException wenn der Server nicht mit 200 antwortet
     */
    public Wetter hole(String stadt) throws IOException, InterruptedException {
        throw new UnsupportedOperationException("noch nicht umgesetzt");
    }
}

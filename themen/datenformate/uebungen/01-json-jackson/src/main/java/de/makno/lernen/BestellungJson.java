package de.makno.lernen;

import java.io.IOException;
import java.nio.file.Path;

/** Wandelt Bestellungen in JSON und zurück. Rumpf – der ObjectMapper fehlt noch. */
public class BestellungJson {

    public String schreibe(Bestellung bestellung) throws IOException {
        throw new UnsupportedOperationException("noch nicht umgesetzt");
    }

    public Bestellung lies(String json) throws IOException {
        throw new UnsupportedOperationException("noch nicht umgesetzt");
    }

    /** Schreibt die Bestellung als UTF-8-Datei. */
    public void speichere(Bestellung bestellung, Path datei) throws IOException {
        throw new UnsupportedOperationException("noch nicht umgesetzt");
    }

    /** Liest eine UTF-8-Datei. */
    public Bestellung lade(Path datei) throws IOException {
        throw new UnsupportedOperationException("noch nicht umgesetzt");
    }
}

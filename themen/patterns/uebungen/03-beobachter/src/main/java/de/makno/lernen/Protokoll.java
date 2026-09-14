package de.makno.lernen;

import java.util.ArrayList;
import java.util.List;

/** Schreibt jede Bestandsänderung mit. */
public class Protokoll {

    private final List<String> zeilen = new ArrayList<>();

    public void schreibe(String artikel, int bestand) {
        zeilen.add(artikel + "=" + bestand);
    }

    public List<String> zeilen() {
        return List.copyOf(zeilen);
    }
}

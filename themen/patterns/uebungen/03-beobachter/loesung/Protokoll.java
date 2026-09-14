package de.makno.lernen;

import java.util.ArrayList;
import java.util.List;

public class Protokoll implements BestandsBeobachter {

    private final List<String> zeilen = new ArrayList<>();

    @Override
    public void bestandGeaendert(BestandsEreignis ereignis) {
        zeilen.add(ereignis.artikel() + "=" + ereignis.bestand());
    }

    public List<String> zeilen() {
        return List.copyOf(zeilen);
    }
}

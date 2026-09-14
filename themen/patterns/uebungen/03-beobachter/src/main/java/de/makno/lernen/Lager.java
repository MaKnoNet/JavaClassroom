package de.makno.lernen;

import java.util.HashMap;
import java.util.Map;

/**
 * Das Lager kennt Anzeige und Protokoll persönlich und ruft sie direkt. Jeder neue
 * Interessent (Nachbestellung, E-Mail, Statistik) braucht ein Feld und einen Aufruf hier.
 */
public class Lager {

    private final Map<String, Integer> bestand = new HashMap<>();
    private final Anzeige anzeige = new Anzeige();
    private final Protokoll protokoll = new Protokoll();

    public void lege(String artikel, int menge) {
        bestand.merge(artikel, menge, Integer::sum);
        anzeige.zeige(artikel, bestand.get(artikel));
        protokoll.schreibe(artikel, bestand.get(artikel));
    }

    public void entnimm(String artikel, int menge) {
        bestand.merge(artikel, -menge, Integer::sum);
        anzeige.zeige(artikel, bestand.get(artikel));
        protokoll.schreibe(artikel, bestand.get(artikel));
    }

    public int bestand(String artikel) {
        return bestand.getOrDefault(artikel, 0);
    }

    public Anzeige anzeige() {
        return anzeige;
    }

    public Protokoll protokoll() {
        return protokoll;
    }
}

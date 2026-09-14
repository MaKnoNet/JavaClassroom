package de.makno.lernen;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Das Subjekt: kennt nur das Interface BestandsBeobachter. Wer sich registriert, ist dem
 * Lager egal – Anzeige, Protokoll, morgen die Nachbestellung. Nicht thread-safe im
 * Bestand; die Beobachterliste ist es, damit ein Beobachter sich beim Ereignis abmelden darf.
 */
public class Lager {

    private final Map<String, Integer> bestand = new HashMap<>();
    private final List<BestandsBeobachter> beobachter = new CopyOnWriteArrayList<>();

    public void registriere(BestandsBeobachter neu) {
        beobachter.add(neu);
    }

    public void entferne(BestandsBeobachter alt) {
        beobachter.remove(alt);
    }

    public void lege(String artikel, int menge) {
        bestand.merge(artikel, menge, Integer::sum);
        benachrichtige(artikel);
    }

    public void entnimm(String artikel, int menge) {
        bestand.merge(artikel, -menge, Integer::sum);
        benachrichtige(artikel);
    }

    public int bestand(String artikel) {
        return bestand.getOrDefault(artikel, 0);
    }

    private void benachrichtige(String artikel) {
        BestandsEreignis ereignis = new BestandsEreignis(artikel, bestand(artikel));
        for (BestandsBeobachter b : beobachter) {
            try {
                b.bestandGeaendert(ereignis);
            } catch (RuntimeException e) {
                // Ein kaputter Beobachter darf weder das Lager noch die anderen Beobachter stoppen.
                // Im Betrieb: loggen (Java-Lektion 19). Hier bewusst nur weiterlaufen.
            }
        }
    }
}

package de.makno.lernen;

import java.util.HashMap;
import java.util.Map;

/**
 * Dekorierer: implementiert dasselbe Interface und HAT ein umhülltes Objekt. Was drin
 * steckt – Datenbank, Adapter, ein anderer Dekorierer – ist egal.
 */
public class Cachend implements Kundendienst {

    private final Kundendienst innen;
    private final Map<Integer, String> cache = new HashMap<>();

    public Cachend(Kundendienst innen) {
        this.innen = innen;
    }

    @Override
    public String kunde(int nummer) {
        return cache.computeIfAbsent(nummer, innen::kunde);
    }
}

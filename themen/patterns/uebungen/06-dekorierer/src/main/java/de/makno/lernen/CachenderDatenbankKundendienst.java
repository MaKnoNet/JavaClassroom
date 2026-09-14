package de.makno.lernen;

import java.util.HashMap;
import java.util.Map;

/**
 * Zweiter Versuch per Vererbung: eine Unterklasse, die Ergebnisse merkt. Wer beides will,
 * braucht eine dritte Klasse – und für jede weitere Eigenschaft verdoppelt sich die Zahl.
 */
public class CachenderDatenbankKundendienst extends DatenbankKundendienst {

    private final Map<Integer, String> cache = new HashMap<>();

    @Override
    public String kunde(int nummer) {
        return cache.computeIfAbsent(nummer, super::kunde);
    }
}

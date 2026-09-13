package de.makno.lernen;

import java.util.Collections;
import java.util.List;

/** Generische Hilfsmethoden. */
public final class Werkzeuge {

    private Werkzeuge() {}

    public static <T extends Comparable<T>> T groesstes(List<T> werte) {
        return Collections.max(werte);
    }

    public static <T> Paar<T, T> endenVon(List<T> werte) {
        return new Paar<>(werte.get(0), werte.get(werte.size() - 1));
    }
}

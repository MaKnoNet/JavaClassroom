package de.makno.lernen;

import java.util.List;

/** Generische Hilfsmethoden. Aufgabe: die Signaturen so ändern, dass die Tests kompilieren. */
public final class Werkzeuge {

    private Werkzeuge() {}

    /** Das größte Element einer Liste – für alles, was sich vergleichen lässt. */
    public static Object groesstes(List<?> werte) {
        throw new UnsupportedOperationException("noch nicht generisch");
    }

    /** Das erste und das letzte Element als Paar. */
    public static Object endenVon(List<?> werte) {
        throw new UnsupportedOperationException("noch nicht generisch");
    }
}

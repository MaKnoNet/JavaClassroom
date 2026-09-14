package de.makno.lernen;

/**
 * Ein Zustand weiß, welche Übergänge von ihm aus erlaubt sind, und liefert den Folgezustand.
 * Die default-Methoden verbieten alles; jeder Zustand erlaubt nur, was das Diagramm zeigt.
 */
public interface BestellZustand {

    String name();

    default BestellZustand bezahlen() {
        throw new IllegalStateException("Bezahlen nicht möglich im Zustand " + name());
    }

    default BestellZustand versenden() {
        throw new IllegalStateException("Versenden nicht möglich im Zustand " + name());
    }

    default BestellZustand stornieren() {
        throw new IllegalStateException("Stornieren nicht möglich im Zustand " + name());
    }

    default BestellZustand retournieren() {
        throw new IllegalStateException("Retournieren nicht möglich im Zustand " + name());
    }
}

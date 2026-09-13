package de.makno.lernen;

/** Spricht mit dem Kartenterminal. In der Übung gibt es keins. */
public class KartenBezahlung implements BezahlService {

    @Override
    public boolean belaste(double betrag) {
        throw new IllegalStateException("Kein Kartenterminal angeschlossen");
    }
}

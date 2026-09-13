package de.makno.lernen;

/** Was der Automat vom Bezahlen wissen muss – und nicht mehr. */
public interface BezahlService {

    /** @return true, wenn der Betrag abgebucht wurde. */
    boolean belaste(double betrag);
}

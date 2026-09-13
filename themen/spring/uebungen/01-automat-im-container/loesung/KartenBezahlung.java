package de.makno.lernen;

import org.springframework.stereotype.Service;

/** Spricht mit dem Kartenterminal. In der Übung gibt es keins. */
@Service
public class KartenBezahlung implements BezahlService {

    @Override
    public boolean belaste(double betrag) {
        throw new IllegalStateException("Kein Kartenterminal angeschlossen");
    }
}

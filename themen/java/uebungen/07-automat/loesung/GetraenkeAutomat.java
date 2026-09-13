package de.makno.lernen;

import java.util.Map;

/** Verkauft Getränke. Wie bezahlt wird, entscheidet, wer den Automaten baut. */
public class GetraenkeAutomat {

    private static final Map<String, Double> PREISE = Map.of(
            "Cola", 1.50,
            "Wasser", 1.00,
            "Kaffee", 2.20);

    private final BezahlService bezahlung;

    public GetraenkeAutomat(BezahlService bezahlung) {
        this.bezahlung = bezahlung;
    }

    /**
     * @return true, wenn bezahlt wurde und das Getränk ausgegeben ist
     * @throws IllegalArgumentException bei unbekanntem Getränk
     */
    public boolean kaufe(String getraenk) {
        Double preis = PREISE.get(getraenk);
        if (preis == null) {
            throw new IllegalArgumentException("Unbekanntes Getränk: " + getraenk);
        }
        return bezahlung.belaste(preis);
    }
}

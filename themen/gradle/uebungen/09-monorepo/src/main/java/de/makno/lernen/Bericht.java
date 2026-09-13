package de.makno.lernen;

import java.util.List;
import java.util.Locale;

/** Erzeugt einen Textbericht – gehört nach :bericht, hängt von :kern ab. */
public class Bericht {

    private final Rabattrechner rechner = new Rabattrechner();

    public String erstelle(List<Double> preise, int prozent) {
        StringBuilder text = new StringBuilder("Rabatt: " + prozent + " %\n");
        for (double preis : preise) {
            text.append(String.format(Locale.ROOT, "%.2f -> %.2f%n", preis, rechner.preisNachRabatt(preis, prozent)));
        }
        return text.toString();
    }
}

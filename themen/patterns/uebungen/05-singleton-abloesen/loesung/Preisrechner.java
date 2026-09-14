package de.makno.lernen;

/** Die Abhängigkeit steht im Konstruktor – sichtbar, austauschbar, testbar. */
public class Preisrechner {

    private final Konfiguration konfiguration;

    public Preisrechner(Konfiguration konfiguration) {
        this.konfiguration = konfiguration;
    }

    public double brutto(double netto) {
        return netto * (1 + konfiguration.mwstProzent() / 100.0);
    }

    public String formatiert(double netto) {
        return String.format(java.util.Locale.ROOT, "%.2f %s", brutto(netto), konfiguration.waehrung());
    }
}

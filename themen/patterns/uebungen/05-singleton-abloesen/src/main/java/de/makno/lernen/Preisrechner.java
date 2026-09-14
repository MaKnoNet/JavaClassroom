package de.makno.lernen;

/** Rechnet Netto in Brutto um – mit dem Steuersatz aus der globalen Konfiguration. */
public class Preisrechner {

    public double brutto(double netto) {
        return netto * (1 + Konfiguration.getInstance().mwstProzent() / 100.0);
    }

    public String formatiert(double netto) {
        return String.format(java.util.Locale.ROOT, "%.2f %s", brutto(netto), Konfiguration.getInstance().waehrung());
    }
}

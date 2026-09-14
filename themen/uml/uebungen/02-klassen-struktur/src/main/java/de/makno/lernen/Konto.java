package de.makno.lernen;

/** Die Vorlage: Bilde genau diese Klasse in diagramm.puml ab – Sichtbarkeiten, Typen, static. */
public class Konto {

    public static final double MAX_STAND = 1_000_000;

    private final String inhaber;
    private double stand;
    protected int buchungen;

    public Konto(String inhaber) {
        this.inhaber = inhaber;
    }

    public double stand() {
        return stand;
    }

    public void einzahlen(double betrag) {
        pruefe(betrag);
        stand += betrag;
        buchungen++;
    }

    private void pruefe(double betrag) {
        if (betrag <= 0 || stand + betrag > MAX_STAND) {
            throw new IllegalArgumentException("Ungültiger Betrag: " + betrag);
        }
    }

    String inhaber() {
        return inhaber;
    }
}

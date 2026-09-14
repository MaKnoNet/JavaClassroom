package de.makno.lernen;

/** Hängt am Interface (Assoziation), benutzt Mitarbeiter nur als Parameter (Abhängigkeit). */
public class Gehaltsrechner {

    private final Gehaltsregel regel;

    public Gehaltsrechner(Gehaltsregel regel) {
        this.regel = regel;
    }

    public double brutto(Mitarbeiter mitarbeiter) {
        return regel.berechne(mitarbeiter.grundgehalt());
    }
}

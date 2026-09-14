package de.makno.lernen;

/** Komposition: Der Hybrid HAT zwei Antriebe – keine dritte Auto-Klasse, kein kopierter Code. */
public class HybridAntrieb implements Antrieb {

    private final Antrieb erster;
    private final Antrieb zweiter;

    public HybridAntrieb(Antrieb erster, Antrieb zweiter) {
        this.erster = erster;
        this.zweiter = zweiter;
    }

    @Override
    public String starte() {
        return erster.starte() + " + " + zweiter.starte();
    }

    @Override
    public int reichweiteKm() {
        return erster.reichweiteKm() + zweiter.reichweiteKm();
    }
}

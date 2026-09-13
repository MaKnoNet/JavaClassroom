package de.makno.lernen;

/** Begrüßt, wen die Quelle nennt. */
public class Begruessung {

    private final Namensquelle quelle;

    public Begruessung(Namensquelle quelle) {
        this.quelle = quelle;
    }

    public String text() {
        return "Hallo " + quelle.name();
    }
}

package de.makno.lernen;

/** Die Geschäftslogik – gehört nach :kern, braucht nichts außer Java. */
public class Rabattrechner {

    private static final int VOLLER_PREIS = 100;

    /** @throws IllegalArgumentException wenn der Prozentsatz nicht zwischen 0 und 100 liegt */
    public double preisNachRabatt(double preis, int prozent) {
        if (prozent < 0 || prozent > VOLLER_PREIS) {
            throw new IllegalArgumentException("Rabatt muss zwischen 0 und 100 liegen: " + prozent);
        }
        return preis * (VOLLER_PREIS - prozent) / VOLLER_PREIS;
    }
}

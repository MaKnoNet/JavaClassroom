package de.makno.lernen;

public class StandardRegel implements Gehaltsregel {

    private static final double ZULAGE = 0.10;

    @Override
    public double berechne(double grundgehalt) {
        return grundgehalt * (1 + ZULAGE);
    }
}

package de.makno.lernen;

import java.util.List;

public class Rechner {

    private static final int MAX_WERT = 1000;

    public int berechneSumme(List<Integer> zahlen) {
        int summe = 0;
        for (int zahl : zahlen) {
            if (zahl > MAX_WERT) {
                continue;
            }
            summe += zahl;
        }
        if (summe < 0) {
            summe = 0;
        }
        return summe;
    }

    public boolean istLeer(List<Integer> zahlen) {
        return zahlen == null || zahlen.isEmpty();
    }
}

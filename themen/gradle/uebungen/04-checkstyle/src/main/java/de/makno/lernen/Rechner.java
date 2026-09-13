package de.makno.lernen;

import java.util.*;

public class Rechner {

    private static final int maxWert = 1000;

    public int BerechneSumme(List<Integer> zahlen) {
        int summe = 0;
        for (int zahl : zahlen) {
            if (zahl > maxWert) continue;
            summe += zahl;
        }
        if (summe < 0) summe = 0;
        return summe;
    }

    public boolean istLeer(List<Integer> zahlen) {
        try {
            return zahlen.isEmpty();
        } catch (NullPointerException e) {
        }
        return true;
    }
}

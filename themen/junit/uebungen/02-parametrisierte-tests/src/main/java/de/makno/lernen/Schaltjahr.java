package de.makno.lernen;

public final class Schaltjahr {

    private Schaltjahr() {}

    public static boolean istSchaltjahr(int jahr) {
        if (jahr % 400 == 0) {
            return true;
        }
        if (jahr % 100 == 0) {
            return false;
        }
        return jahr % 4 == 0;
    }
}

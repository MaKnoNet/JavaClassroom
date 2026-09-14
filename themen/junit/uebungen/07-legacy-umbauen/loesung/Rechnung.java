package de.makno.lernen;

import java.util.List;

/**
 * Dieselbe Rechnung, umgebaut unter dem Schutz der Charakterisierungstests: jede Regel hat
 * einen Namen, jeder Schritt eine Methode. Das Verhalten ist unverändert – auch die
 * Eigenheit, dass die Steuer auf das Netto vor Rabatt gerechnet wird.
 */
public class Rechnung {

    private static final String DEUTSCHLAND = "DE";
    private static final String OESTERREICH = "AT";
    private static final String BUCH = "BUCH";
    private static final String STAMMKUNDE = "STAMM";
    private static final String MITARBEITER = "MITARBEITER";

    private static final double MWST_DE = 0.19;
    private static final double MWST_DE_BUCH = 0.07;
    private static final double MWST_AT = 0.20;
    private static final double MWST_AT_BUCH = 0.10;
    private static final int MENGENRABATT_AB_STUECK = 10;
    private static final double MENGENRABATT_FAKTOR = 0.9;
    private static final double STAMMRABATT_AB_NETTO = 100;
    private static final double STAMMRABATT = 0.05;
    private static final double MITARBEITERRABATT = 0.2;
    private static final double EXPRESSVERSAND = 9.99;
    private static final double STANDARDVERSAND = 4.99;
    private static final double VERSANDFREI_AB_NETTO = 50;

    public double berechne(List<Position> positionen, String kundentyp, boolean express, String land) {
        double netto = 0;
        double steuer = 0;
        for (Position position : positionen) {
            double zeilenbetrag = zeilenbetrag(position);
            netto += zeilenbetrag;
            steuer += zeilenbetrag * steuersatz(land, position.kategorie());
        }
        double summe = netto - rabatt(kundentyp, netto) + steuer + versand(express, netto);
        return aufCentGerundet(summe);
    }

    private static double zeilenbetrag(Position position) {
        double betrag = position.preis() * position.menge();
        return position.menge() >= MENGENRABATT_AB_STUECK ? betrag * MENGENRABATT_FAKTOR : betrag;
    }

    private static double steuersatz(String land, String kategorie) {
        boolean buch = kategorie.equals(BUCH);
        if (land.equals(DEUTSCHLAND)) {
            return buch ? MWST_DE_BUCH : MWST_DE;
        }
        if (land.equals(OESTERREICH)) {
            return buch ? MWST_AT_BUCH : MWST_AT;
        }
        return 0;
    }

    private static double rabatt(String kundentyp, double netto) {
        if (kundentyp.equals(STAMMKUNDE) && netto > STAMMRABATT_AB_NETTO) {
            return netto * STAMMRABATT;
        }
        if (kundentyp.equals(MITARBEITER)) {
            return netto * MITARBEITERRABATT;
        }
        return 0;
    }

    private static double versand(boolean express, double netto) {
        if (express) {
            return EXPRESSVERSAND;
        }
        return netto < VERSANDFREI_AB_NETTO ? STANDARDVERSAND : 0;
    }

    private static double aufCentGerundet(double betrag) {
        return Math.round(betrag * 100) / 100.0;
    }
}

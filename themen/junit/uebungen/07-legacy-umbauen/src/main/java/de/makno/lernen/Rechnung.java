package de.makno.lernen;

import java.util.List;

/**
 * Gewachsen über Jahre, von vier Leuten, ohne Tests. Funktioniert – und niemand traut sich
 * ran. Die Charakterisierungstests halten fest, was sie heute tut. Bau sie um, ohne dass
 * die Tests rot werden.
 */
public class Rechnung {

    public double berechne(List<Position> positionen, String kundentyp, boolean express, String land) {
        double netto = 0;
        double steuer = 0;
        for (Position p : positionen) {
            double zeile = p.preis() * p.menge();
            if (p.menge() >= 10) {
                zeile = zeile * 0.9;
            }
            netto = netto + zeile;
            if (land.equals("DE")) {
                if (p.kategorie().equals("BUCH")) {
                    steuer = steuer + zeile * 0.07;
                } else {
                    steuer = steuer + zeile * 0.19;
                }
            } else if (land.equals("AT")) {
                if (p.kategorie().equals("BUCH")) {
                    steuer = steuer + zeile * 0.10;
                } else {
                    steuer = steuer + zeile * 0.20;
                }
            } else {
                steuer = steuer + 0;
            }
        }
        double rabatt = 0;
        if (kundentyp.equals("STAMM")) {
            if (netto > 100) {
                rabatt = netto * 0.05;
            }
        } else if (kundentyp.equals("MITARBEITER")) {
            rabatt = netto * 0.2;
        }
        double versand = 0;
        if (express) {
            versand = 9.99;
        } else {
            if (netto < 50) {
                versand = 4.99;
            }
        }
        double summe = netto - rabatt + steuer + versand;
        return Math.round(summe * 100) / 100.0;
    }
}

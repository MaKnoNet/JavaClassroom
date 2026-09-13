package de.makno.lernen;

import java.util.Locale;

/** Der Einstiegspunkt für die Kommandozeile – gehört nach :konsole, hängt von :kern ab. */
public class Konsole {

    public static void main(String[] args) {
        double preis = args.length > 0 ? Double.parseDouble(args[0]) : 100.0;
        int prozent = args.length > 1 ? Integer.parseInt(args[1]) : 20;
        double ergebnis = new Rabattrechner().preisNachRabatt(preis, prozent);
        System.out.println(String.format(Locale.ROOT, "%.2f EUR mit %d %% Rabatt: %.2f EUR", preis, prozent, ergebnis));
    }
}

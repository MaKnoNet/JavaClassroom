package de.makno.lernen;

/** Der Kontext: kennt nur das Interface. Eine neue Versandart ist eine neue Klasse – hier ändert sich nichts. */
public class Versandkosten {

    private final VersandStrategie strategie;

    public Versandkosten(VersandStrategie strategie) {
        this.strategie = strategie;
    }

    public double berechne(double gewichtKg) {
        return strategie.kosten(gewichtKg);
    }
}

package de.makno.lernen;

/**
 * Eine Bestellung mit vielen optionalen Angaben – und einem Konstruktor, den niemand
 * lesen kann: new Bestellung("Anna", "Weg 1", null, true, 0, null). Was ist das vierte?
 */
public class Bestellung {

    private final String kunde;
    private final String lieferadresse;
    private final String rechnungsadresse;
    private final boolean express;
    private final int rabattProzent;
    private final String gutscheincode;

    public Bestellung(String kunde, String lieferadresse, String rechnungsadresse,
                      boolean express, int rabattProzent, String gutscheincode) {
        this.kunde = kunde;
        this.lieferadresse = lieferadresse;
        this.rechnungsadresse = rechnungsadresse;
        this.express = express;
        this.rabattProzent = rabattProzent;
        this.gutscheincode = gutscheincode;
    }

    public String kunde() { return kunde; }
    public String lieferadresse() { return lieferadresse; }
    public String rechnungsadresse() { return rechnungsadresse; }
    public boolean express() { return express; }
    public int rabattProzent() { return rabattProzent; }
    public String gutscheincode() { return gutscheincode; }
}

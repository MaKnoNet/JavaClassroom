package de.makno.lernen;

import java.math.BigDecimal;

/** Ein Artikel im Lager. Noch eine gewöhnliche Klasse – die Datenbank kennt sie nicht. */
public class Produkt {

    private Long id;
    private String name;
    private BigDecimal preis;
    private Kategorie kategorie;

    public Produkt(String name, BigDecimal preis, Kategorie kategorie) {
        this.name = name;
        this.preis = preis;
        this.kategorie = kategorie;
    }

    public Long id() {
        return id;
    }

    public String name() {
        return name;
    }

    public BigDecimal preis() {
        return preis;
    }

    public Kategorie kategorie() {
        return kategorie;
    }
}

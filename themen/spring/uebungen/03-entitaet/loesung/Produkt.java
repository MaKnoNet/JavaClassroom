package de.makno.lernen;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.math.BigDecimal;

/** Ein Artikel im Lager – Tabelle PRODUKT mit Fremdschlüssel KATEGORIE_ID. */
@Entity
public class Produkt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal preis;

    /** Viele Produkte gehören zu einer Kategorie; die Spalte liegt auf der Produkt-Seite. */
    @ManyToOne
    private Kategorie kategorie;

    /** Für Hibernate: Es baut Objekte zuerst leer und füllt die Felder danach. */
    protected Produkt() {}

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

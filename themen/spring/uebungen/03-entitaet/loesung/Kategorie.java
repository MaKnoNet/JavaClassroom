package de.makno.lernen;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/** Eine Warengruppe – Tabelle KATEGORIE mit ID und NAME. */
@Entity
public class Kategorie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    /** Für Hibernate: Es baut Objekte zuerst leer und füllt die Felder danach. */
    protected Kategorie() {}

    public Kategorie(String name) {
        this.name = name;
    }

    public Long id() {
        return id;
    }

    public String name() {
        return name;
    }
}

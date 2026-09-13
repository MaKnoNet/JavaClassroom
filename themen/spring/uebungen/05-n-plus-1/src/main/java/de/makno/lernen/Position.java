package de.makno.lernen;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

/** Eine Zeile einer Bestellung: Artikel und Menge. */
@Entity
public class Position {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Bestellung bestellung;

    @Column(nullable = false)
    private String artikel;

    private int menge;

    protected Position() {}

    Position(Bestellung bestellung, String artikel, int menge) {
        this.bestellung = bestellung;
        this.artikel = artikel;
        this.menge = menge;
    }

    public String artikel() {
        return artikel;
    }

    public int menge() {
        return menge;
    }
}

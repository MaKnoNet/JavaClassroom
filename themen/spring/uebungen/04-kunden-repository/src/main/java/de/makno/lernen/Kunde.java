package de.makno.lernen;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.math.BigDecimal;

/** Ein Kunde mit Guthaben. Fertig – so, wie Übung 03 es gelehrt hat. */
@Entity
public class Kunde {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal guthaben;

    protected Kunde() {}

    public Kunde(String email, String name, BigDecimal guthaben) {
        this.email = email;
        this.name = name;
        this.guthaben = guthaben;
    }

    public Long id() {
        return id;
    }

    public String email() {
        return email;
    }

    public String name() {
        return name;
    }

    public BigDecimal guthaben() {
        return guthaben;
    }

    public void umbenennen(String neuerName) {
        this.name = neuerName;
    }

    /** Positiv = Gutschrift, negativ = Belastung. */
    public void buche(BigDecimal betrag) {
        this.guthaben = this.guthaben.add(betrag);
    }
}

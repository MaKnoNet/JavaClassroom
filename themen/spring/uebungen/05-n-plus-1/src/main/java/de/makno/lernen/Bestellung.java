package de.makno.lernen;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

/** Eine Bestellung eines Kunden mit ihren Positionen. */
@Entity
public class Bestellung {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String kunde;

    // Sammlungen sind bei JPA standardmäßig LAZY: Die Positionen werden erst geladen,
    // wenn jemand sie anfasst – je Bestellung mit einer eigenen SQL-Abfrage.
    @OneToMany(mappedBy = "bestellung", cascade = CascadeType.ALL)
    private List<Position> positionen = new ArrayList<>();

    protected Bestellung() {}

    public Bestellung(String kunde) {
        this.kunde = kunde;
    }

    public Position fuegeHinzu(String artikel, int menge) {
        Position position = new Position(this, artikel, menge);
        positionen.add(position);
        return position;
    }

    public Long id() {
        return id;
    }

    public String kunde() {
        return kunde;
    }

    public List<Position> positionen() {
        return positionen;
    }
}

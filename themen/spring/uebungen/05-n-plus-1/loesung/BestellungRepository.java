package de.makno.lernen;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BestellungRepository extends JpaRepository<Bestellung, Long> {

    /**
     * JOIN FETCH: Bestellungen und Positionen in EINER Abfrage. DISTINCT, weil der Join
     * je Position eine Zeile liefert und Hibernate die Bestellung sonst mehrfach zurückgäbe.
     */
    @Query("select distinct b from Bestellung b left join fetch b.positionen")
    List<Bestellung> findAlleMitPositionen();
}

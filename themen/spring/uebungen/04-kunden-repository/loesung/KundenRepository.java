package de.makno.lernen;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/** Der Datenzugriff für Kunden – Spring Data schreibt die Implementierung beim Start. */
public interface KundenRepository extends JpaRepository<Kunde, Long> {

    /** Abgeleitete Abfrage: Der Methodenname ist die Query (`where email = ?`). */
    Optional<Kunde> findByEmail(String email);
}

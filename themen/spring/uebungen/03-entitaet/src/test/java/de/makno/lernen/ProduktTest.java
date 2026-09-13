package de.makno.lernen;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import jakarta.persistence.PersistenceException;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

/** Startet nur den JPA-Teil von Spring mit einer H2-Datenbank im Speicher. */
@DataJpaTest
class ProduktTest {

    @Autowired
    private TestEntityManager datenbank;

    @Test
    void gespeichertesProduktBekommtEineId() {
        Produkt schraube = new Produkt("Schraube", new BigDecimal("0.05"), null);

        datenbank.persistAndFlush(schraube);

        assertNotNull(schraube.id(), "Die Id vergibt die Datenbank beim Speichern");
    }

    @Test
    void produktKommtMitSeinerKategorieZurueck() {
        Kategorie werkzeug = datenbank.persist(new Kategorie("Werkzeug"));
        Produkt hammer = datenbank.persistAndFlush(new Produkt("Hammer", new BigDecimal("12.90"), werkzeug));
        datenbank.clear(); // Cache leeren – ab hier muss wirklich die Datenbank antworten.

        Produkt geladen = datenbank.find(Produkt.class, hammer.id());

        assertEquals("Hammer", geladen.name());
        assertEquals(0, new BigDecimal("12.90").compareTo(geladen.preis()));
        assertEquals("Werkzeug", geladen.kategorie().name());
        assertEquals(new ProduktUebersicht("Hammer", geladen.preis(), "Werkzeug"), ProduktUebersicht.von(geladen));
    }

    @Test
    void produktOhneNamenLehntDieDatenbankAb() {
        Produkt namenlos = new Produkt(null, new BigDecimal("1.00"), null);

        assertThrows(PersistenceException.class, () -> datenbank.persistAndFlush(namenlos));
    }
}

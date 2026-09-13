package de.makno.lernen;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

/** CRUD über das Repository – jeder Test läuft in einer eigenen, am Ende verworfenen Transaktion. */
@DataJpaTest
class KundenRepositoryTest {

    @Autowired
    private KundenRepository kunden;

    @Autowired
    private TestEntityManager datenbank;

    @Test
    void createUndRead() {
        kunden.save(new Kunde("anna@example.org", "Anna", new BigDecimal("10.00")));

        Optional<Kunde> gefunden = kunden.findByEmail("anna@example.org");

        assertTrue(gefunden.isPresent());
        assertEquals("Anna", gefunden.get().name());
    }

    @Test
    void unbekannteEmailLiefertLeer() {
        assertTrue(kunden.findByEmail("niemand@example.org").isEmpty());
    }

    @Test
    void update() {
        Kunde anna = kunden.saveAndFlush(new Kunde("anna@example.org", "Anna", new BigDecimal("10.00")));

        anna.umbenennen("Anna Berg");
        kunden.saveAndFlush(anna);
        datenbank.clear(); // ab hier muss wirklich die Datenbank antworten

        assertEquals("Anna Berg", kunden.findById(anna.id()).orElseThrow().name());
    }

    @Test
    void delete() {
        Kunde anna = kunden.save(new Kunde("anna@example.org", "Anna", new BigDecimal("10.00")));

        kunden.delete(anna);

        assertTrue(kunden.findByEmail("anna@example.org").isEmpty());
        assertEquals(0, kunden.count());
    }
}

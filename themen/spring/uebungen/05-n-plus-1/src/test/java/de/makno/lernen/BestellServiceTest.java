package de.makno.lernen;

import static org.junit.jupiter.api.Assertions.assertEquals;

import jakarta.persistence.EntityManager;
import java.util.Map;
import org.hibernate.SessionFactory;
import org.hibernate.stat.Statistics;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

/**
 * Prüft das Ergebnis UND den Preis: Wie viele SQL-Anweisungen braucht der Service für
 * zwanzig Bestellungen? Hibernate zählt mit (generate_statistics=true in application.properties).
 */
@DataJpaTest
@Import(BestellService.class)
class BestellServiceTest {

    private static final int ANZAHL_BESTELLUNGEN = 20;

    @Autowired
    private BestellService service;

    @Autowired
    private TestEntityManager datenbank;

    @Autowired
    private EntityManager entityManager;

    private Statistics statistik;

    @BeforeEach
    void bestellungenAnlegen() {
        for (int i = 0; i < ANZAHL_BESTELLUNGEN; i++) {
            Bestellung bestellung = new Bestellung(i % 2 == 0 ? "Anna" : "Ben");
            bestellung.fuegeHinzu("Schraube", 10);
            bestellung.fuegeHinzu("Mutter", 5);
            datenbank.persist(bestellung);
        }
        // In die Datenbank schreiben und den Persistenzkontext leeren: Ab jetzt muss alles
        // wirklich per SQL geladen werden – so wie im Betrieb bei einer neuen Anfrage.
        datenbank.flush();
        datenbank.clear();
        statistik = entityManager.getEntityManagerFactory().unwrap(SessionFactory.class).getStatistics();
        statistik.clear();
    }

    @Test
    void summiertStueckJeKunde() {
        Map<String, Integer> summen = service.stueckJeKunde();

        assertEquals(Map.of("Anna", 150, "Ben", 150), summen);
    }

    @Test
    void brauchtEineEinzigeAbfrage() {
        service.stueckJeKunde();

        long anweisungen = statistik.getPrepareStatementCount();
        assertEquals(1, anweisungen, "SQL-Anweisungen für " + ANZAHL_BESTELLUNGEN + " Bestellungen – N+1?");
    }
}

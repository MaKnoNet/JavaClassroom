package de.makno.lernen;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Ganze Anwendung, echte Transaktionen: Dieser Test ist absichtlich NICHT transaktional,
 * damit sichtbar wird, was nach einem Fehler wirklich in der Datenbank steht.
 */
@SpringBootTest
class KundenServiceTest {

    @Autowired
    private KundenService service;

    @Autowired
    private KundenRepository kunden;

    @BeforeEach
    void zweiKunden() {
        kunden.deleteAll();
        kunden.save(new Kunde("anna@example.org", "Anna", new BigDecimal("100.00")));
        kunden.save(new Kunde("ben@example.org", "Ben", BigDecimal.ZERO));
    }

    @Test
    void uebertragungBuchtBeideSeiten() {
        service.uebertrage("anna@example.org", "ben@example.org", new BigDecimal("30.00"));

        assertEquals(0, new BigDecimal("70.00").compareTo(guthabenVon("anna@example.org")));
        assertEquals(0, new BigDecimal("30.00").compareTo(guthabenVon("ben@example.org")));
    }

    @Test
    void fehlerMittendrinLaesstDenSenderUnveraendert() {
        assertThrows(IllegalArgumentException.class,
                () -> service.uebertrage("anna@example.org", "niemand@example.org", new BigDecimal("30.00")));

        assertEquals(0, new BigDecimal("100.00").compareTo(guthabenVon("anna@example.org")),
                "Die Belastung von Anna muss zurückgerollt sein – Ben hat das Geld nie bekommen");
    }

    private BigDecimal guthabenVon(String email) {
        return kunden.findByEmail(email).orElseThrow().guthaben();
    }
}

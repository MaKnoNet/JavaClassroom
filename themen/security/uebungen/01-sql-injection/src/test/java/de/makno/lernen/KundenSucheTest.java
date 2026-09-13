package de.makno.lernen;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class KundenSucheTest {

    private static final String URL = "jdbc:h2:mem:kunden;DB_CLOSE_DELAY=-1";

    private final KundenSuche suche = new KundenSuche(URL);

    @BeforeEach
    void tabelleFuellen() throws Exception {
        try (Connection verbindung = DriverManager.getConnection(URL);
                Statement anweisung = verbindung.createStatement()) {
            anweisung.execute("drop table if exists kunde");
            anweisung.execute("create table kunde (id int primary key, name varchar(100), rolle varchar(20))");
            anweisung.execute("insert into kunde values (1, 'Anna', 'ADMIN'), (2, 'Ben', 'USER'), (3, 'O''Brien', 'USER')");
        }
    }

    @Test
    void findetKundenNachName() throws Exception {
        assertEquals(List.of(new Kunde(2, "Ben", "USER")), suche.nachName("Ben"));
    }

    @Test
    void unbekannterNameLiefertNichts() throws Exception {
        assertTrue(suche.nachName("Niemand").isEmpty());
    }

    @Test
    void apostrophImNamenIstEinNormalesZeichen() throws Exception {
        assertEquals(List.of(new Kunde(3, "O'Brien", "USER")), suche.nachName("O'Brien"));
    }

    @Test
    void eingabeKannDieAbfrageNichtUmschreiben() throws Exception {
        // Der Klassiker: Wird das ' als SQL gelesen, lautet die Bedingung  name = '' OR '1'='1  – immer wahr.
        List<Kunde> treffer = suche.nachName("' OR '1'='1");

        assertTrue(treffer.isEmpty(), "Injection hat funktioniert – alle Kunden ausgeliefert: " + treffer);
    }

    @Test
    void eingabeKannKeineZweiteAnweisungAnhaengen() throws Exception {
        suche.nachName("x'; delete from kunde; --");

        assertEquals(1, suche.nachName("Anna").size(), "Die Tabelle wurde geleert");
    }
}

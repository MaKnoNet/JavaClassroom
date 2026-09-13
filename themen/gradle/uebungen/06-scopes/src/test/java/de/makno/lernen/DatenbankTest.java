package de.makno.lernen;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class DatenbankTest {

    @Test
    void datenbankAntwortet() throws Exception {
        assertEquals("Hallo Welt", new Datenbank().begruesse("Welt"));
    }
}

package de.makno.lernen;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.Test;

class RechnerTest {

    @Test
    void summiertUndIgnoriertZuGrosseWerte() {
        assertEquals(6, new Rechner().berechneSumme(List.of(1, 2, 3, 5000)));
    }

    @Test
    void nullGiltAlsLeer() {
        assertTrue(new Rechner().istLeer(null));
    }
}

package de.makno.lernen;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Test;

class BerichtTest {

    @Test
    void berichtListetJedenPreis() {
        String text = new Bericht().erstelle(List.of(100.0, 50.0), 10);

        assertEquals("Rabatt: 10 %\n100.00 -> 90.00\n50.00 -> 45.00\n", text.replace("\r\n", "\n"));
    }
}

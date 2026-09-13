package de.makno.lernen;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;

class BegruessungTest {

    @Test
    void begruesstDenNamenAusDerQuelle() {
        Namensquelle quelle = mock(Namensquelle.class);
        when(quelle.name()).thenReturn("Welt");

        assertEquals("Hallo Welt", new Begruessung(quelle).text());
    }
}

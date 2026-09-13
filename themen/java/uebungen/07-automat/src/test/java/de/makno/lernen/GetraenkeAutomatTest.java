package de.makno.lernen;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GetraenkeAutomatTest {

    private BezahlService bezahlung;
    private GetraenkeAutomat automat;

    @BeforeEach
    void aufbauen() {
        bezahlung = mock(BezahlService.class);
        automat = new GetraenkeAutomat(bezahlung);
    }

    @Test
    void kaufBelastetDenPreisDesGetraenks() {
        when(bezahlung.belaste(1.50)).thenReturn(true);

        assertTrue(automat.kaufe("Cola"));
        verify(bezahlung).belaste(1.50);
    }

    @Test
    void abgelehnteZahlungGibtNichtsAus() {
        when(bezahlung.belaste(2.20)).thenReturn(false);

        assertFalse(automat.kaufe("Kaffee"));
    }

    @Test
    void unbekanntesGetraenkBelastetNichts() {
        IllegalArgumentException fehler =
                assertThrows(IllegalArgumentException.class, () -> automat.kaufe("Limo"));

        assertEquals("Unbekanntes Getränk: Limo", fehler.getMessage());
        verify(bezahlung, never()).belaste(anyDouble());
    }

    @Test
    void kartenBezahlungIstEinBezahlService() {
        BezahlService service = new KartenBezahlung();

        assertThrows(IllegalStateException.class, () -> service.belaste(1.00));
    }
}

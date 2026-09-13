package de.makno.lernen;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BestellserviceTest {

    private static final String KUNDE = "anna@example.org";
    private static final String ARTIKEL = "Schraube";
    private static final int MENGE = 2;

    private Lager lager;
    private Benachrichtigung benachrichtigung;
    private Bestellservice service;

    @BeforeEach
    void aufbauen() {
        lager = mock(Lager.class);
        benachrichtigung = mock(Benachrichtigung.class);
        service = new Bestellservice(lager, benachrichtigung);
    }

    @Test
    void verfuegbarerArtikelWirdEntnommenUndBestaetigt() {
        when(lager.istVerfuegbar(ARTIKEL, MENGE)).thenReturn(true);

        boolean ausgefuehrt = service.bestelle(KUNDE, ARTIKEL, MENGE);

        assertTrue(ausgefuehrt);
        verify(lager).entnehme(ARTIKEL, MENGE);
        verify(benachrichtigung).sende(KUNDE, "Bestellung bestätigt: 2 x Schraube");
    }

    @Test
    void nichtVerfuegbarerArtikelWirdAbgelehntOhneEntnahme() {
        when(lager.istVerfuegbar(ARTIKEL, MENGE)).thenReturn(false);

        boolean ausgefuehrt = service.bestelle(KUNDE, ARTIKEL, MENGE);

        assertFalse(ausgefuehrt);
        verify(lager, never()).entnehme(anyString(), anyInt());
        verify(benachrichtigung).sende(KUNDE, "Leider nicht verfügbar: Schraube");
    }

    @Test
    void verfuegbarkeitWirdMitBestellterMengeAbgefragt() {
        service.bestelle(KUNDE, ARTIKEL, 7);

        verify(lager).istVerfuegbar(ARTIKEL, 7);
    }
}

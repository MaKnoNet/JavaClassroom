package de.makno.lernen;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

/**
 * Der Wetterdienst fällt aus. Ein Service ohne Schutz reicht jeden Fehler durch und
 * hämmert weiter auf den kaputten Dienst ein; einer mit Schutzschalter antwortet mit
 * „unbekannt" und lässt den Dienst nach drei Fehlern in Ruhe.
 */
@SpringBootTest
class WetterServiceTest {

    private static final String STADT = "Köln";
    private static final int FEHLER_BIS_OFFEN = 3;

    @Autowired
    private WetterService service;

    @Autowired
    private CircuitBreakerRegistry schutzschalter;

    @MockitoBean
    private WetterAbruf abruf;

    @BeforeEach
    void schutzschalterZuruecksetzen() {
        schutzschalter.circuitBreaker("wetter").reset();
    }

    @Test
    void liefertWetterWennDerDienstAntwortet() {
        Wetter sonnig = new Wetter(STADT, 24.5, "sonnig");
        when(abruf.hole(STADT)).thenReturn(sonnig);

        assertEquals(sonnig, service.fuer(STADT));
    }

    @Test
    void antwortetMitUnbekanntStattMitException() {
        when(abruf.hole(anyString())).thenThrow(new IllegalStateException("Wetterdienst antwortete mit 503"));

        assertEquals(Wetter.unbekannt(STADT), service.fuer(STADT));
    }

    @Test
    void laesstDenDienstNachDreiFehlernInRuhe() {
        when(abruf.hole(anyString())).thenThrow(new IllegalStateException("Wetterdienst antwortete mit 503"));

        for (int i = 0; i < FEHLER_BIS_OFFEN + 2; i++) {
            assertEquals(Wetter.unbekannt(STADT), service.fuer(STADT));
        }

        verify(abruf, times(FEHLER_BIS_OFFEN)).hole(STADT);
        assertEquals(CircuitBreaker.State.OPEN, schutzschalter.circuitBreaker("wetter").getState());
    }
}

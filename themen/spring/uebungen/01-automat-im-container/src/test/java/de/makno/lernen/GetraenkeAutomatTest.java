package de.makno.lernen;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@SpringBootTest
class GetraenkeAutomatTest {

    @Autowired
    private GetraenkeAutomat automat;

    @MockitoBean
    private BezahlService bezahlung;

    @Autowired
    private ApplicationContext kontext;

    @Test
    void containerBautDenAutomatenMitDemMock() {
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
    void ohneMockWaereDieKartenBezahlungImContainer() {
        // Der Mock ersetzt die echte Bean nur im Test – die Klasse selbst muss trotzdem
        // als Komponente registriert sein, sonst gäbe es im Betrieb keine Bezahlung.
        assertTrue(kontext.containsBeanDefinition("kartenBezahlung"));
        assertInstanceOf(BezahlService.class, kontext.getBean("kartenBezahlung"));
    }
}

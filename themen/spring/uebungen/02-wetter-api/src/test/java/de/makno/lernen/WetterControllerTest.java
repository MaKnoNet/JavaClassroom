package de.makno.lernen;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

/** Spricht über echtes HTTP mit dem Server – der Port wird zufällig gewählt. */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class WetterControllerTest {

    @Autowired
    private TestRestTemplate client;

    @Test
    void bekannteStadtLiefert200MitJson() {
        ResponseEntity<Wetter> antwort = client.getForEntity("/api/wetter/berlin", Wetter.class);

        assertEquals(HttpStatus.OK, antwort.getStatusCode());
        MediaType typ = antwort.getHeaders().getContentType();
        assertNotNull(typ);
        assertTrue(typ.isCompatibleWith(MediaType.APPLICATION_JSON), "Content-Type war " + typ);
        assertEquals(new Wetter("Berlin", 21.5), antwort.getBody());
    }

    @Test
    void grossschreibungSpieltKeineRolle() {
        ResponseEntity<Wetter> antwort = client.getForEntity("/api/wetter/Hamburg", Wetter.class);

        assertEquals(HttpStatus.OK, antwort.getStatusCode());
        assertEquals("Hamburg", antwort.getBody().stadt());
    }

    @Test
    void unbekannteStadtLiefert404() {
        ResponseEntity<String> antwort = client.getForEntity("/api/wetter/atlantis", String.class);

        assertEquals(HttpStatus.NOT_FOUND, antwort.getStatusCode());
    }
}

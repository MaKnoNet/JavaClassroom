package de.makno.lernen;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class KundenApiTest {

    @Autowired
    private TestRestTemplate client;

    @Test
    void anlegenUndAuflisten() {
        ResponseEntity<Kunde> angelegt = client.postForEntity("/api/kunden", new KundenController.NeuerKunde("Anna"), Kunde.class);
        assertEquals(HttpStatus.CREATED, angelegt.getStatusCode());

        ResponseEntity<Kunde[]> alle = client.getForEntity("/api/kunden", Kunde[].class);
        assertEquals(1, alle.getBody().length);
        assertEquals("Anna", alle.getBody()[0].getName());
    }
}

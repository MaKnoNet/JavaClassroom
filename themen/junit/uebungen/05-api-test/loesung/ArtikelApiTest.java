package de.makno.lernen;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ArtikelApiTest {

    private static final HttpClient CLIENT = HttpClient.newHttpClient();
    private static ArtikelServer server;

    @BeforeAll
    static void serverStarten() throws IOException {
        server = new ArtikelServer();
        server.start();
    }

    @AfterAll
    static void serverStoppen() {
        server.stop();
    }

    private static HttpResponse<String> hole(String pfad) throws IOException, InterruptedException {
        HttpRequest anfrage = HttpRequest.newBuilder(URI.create(server.basisUrl() + pfad)).GET().build();
        return CLIENT.send(anfrage, HttpResponse.BodyHandlers.ofString());
    }

    @Test
    void listeLiefertAlleArtikelAlsJson() throws Exception {
        HttpResponse<String> antwort = hole("/api/artikel");

        assertEquals(200, antwort.statusCode());
        assertTrue(antwort.headers().firstValue("Content-Type").orElse("").startsWith("application/json"));
        assertTrue(antwort.body().contains("\"Schraube\""));
    }

    @Test
    void einzelnerArtikelWirdGefunden() throws Exception {
        HttpResponse<String> antwort = hole("/api/artikel/2");

        assertEquals(200, antwort.statusCode());
        assertTrue(antwort.body().contains("\"name\":\"Mutter\""));
    }

    @Test
    void unbekannteIdLiefert404() throws Exception {
        assertEquals(404, hole("/api/artikel/99").statusCode());
    }

    @Test
    void ungueltigeIdLiefert400() throws Exception {
        assertEquals(400, hole("/api/artikel/abc").statusCode());
    }
}

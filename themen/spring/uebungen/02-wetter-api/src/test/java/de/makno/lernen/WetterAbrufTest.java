package de.makno.lernen;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/** Ein simulierter fremder Wetterdienst – JDK-Bordmittel, kein Spring, kein Internet. */
class WetterAbrufTest {

    private static final String JSON_BERLIN = "{\"stadt\":\"Berlin\",\"temperatur\":21.5}";

    private static HttpServer fremderDienst;
    private static WetterAbruf abruf;

    @BeforeAll
    static void starteFremdenDienst() throws IOException {
        fremderDienst = HttpServer.create(new InetSocketAddress("127.0.0.1", 0), 0);
        fremderDienst.createContext("/api/wetter/", austausch -> {
            boolean bekannt = austausch.getRequestURI().getPath().endsWith("/berlin");
            byte[] koerper = (bekannt ? JSON_BERLIN : "").getBytes(StandardCharsets.UTF_8);
            austausch.getResponseHeaders().add("Content-Type", "application/json");
            austausch.sendResponseHeaders(bekannt ? 200 : 404, koerper.length);
            try (OutputStream aus = austausch.getResponseBody()) {
                aus.write(koerper);
            }
        });
        fremderDienst.start();
        abruf = new WetterAbruf("http://127.0.0.1:" + fremderDienst.getAddress().getPort());
    }

    @AfterAll
    static void stoppeFremdenDienst() {
        fremderDienst.stop(0);
    }

    @Test
    void jsonAntwortWirdZuWetter() throws Exception {
        assertEquals(new Wetter("Berlin", 21.5), abruf.hole("berlin"));
    }

    @Test
    void fehlerstatusWirdZurAusnahme() {
        IllegalStateException fehler =
                assertThrows(IllegalStateException.class, () -> abruf.hole("atlantis"));

        assertEquals("Wetterdienst antwortete mit 404", fehler.getMessage());
    }
}

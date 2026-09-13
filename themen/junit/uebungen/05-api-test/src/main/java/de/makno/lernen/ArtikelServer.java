package de.makno.lernen;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * Kleiner HTTP-Server mit einer JSON-API – fertig, wird getestet, nicht geändert.
 * GET /api/artikel      → Liste aller Artikel
 * GET /api/artikel/{id} → ein Artikel, 404 wenn unbekannt, 400 wenn id keine Zahl ist
 */
public final class ArtikelServer {

    record Artikel(int id, String name, double preis) {
        String alsJson() {
            return "{\"id\":" + id + ",\"name\":\"" + name + "\",\"preis\":" + preis + "}";
        }
    }

    private static final List<Artikel> ARTIKEL = List.of(
            new Artikel(1, "Schraube", 0.10),
            new Artikel(2, "Mutter", 0.05),
            new Artikel(3, "Scheibe", 0.02));
    private static final String JSON = "application/json; charset=utf-8";
    private static final String PFAD = "/api/artikel";

    private final HttpServer server;

    /** Port 0 = das Betriebssystem wählt einen freien Port. */
    public ArtikelServer() throws IOException {
        server = HttpServer.create(new InetSocketAddress("127.0.0.1", 0), 0);
        server.createContext(PFAD, this::behandle);
    }

    public void start() {
        server.start();
    }

    public void stop() {
        server.stop(0);
    }

    public String basisUrl() {
        return "http://127.0.0.1:" + server.getAddress().getPort();
    }

    private void behandle(HttpExchange exchange) throws IOException {
        String pfad = exchange.getRequestURI().getPath();
        if (!"GET".equals(exchange.getRequestMethod())) {
            antworte(exchange, 405, "{\"fehler\":\"nur GET\"}");
        } else if (pfad.equals(PFAD)) {
            antworte(exchange, 200, "[" + String.join(",", ARTIKEL.stream().map(Artikel::alsJson).toList()) + "]");
        } else if (pfad.startsWith(PFAD + "/")) {
            einzelnen(exchange, pfad.substring(PFAD.length() + 1));
        } else {
            antworte(exchange, 404, "{\"fehler\":\"nicht gefunden\"}");
        }
    }

    private void einzelnen(HttpExchange exchange, String idText) throws IOException {
        try {
            int id = Integer.parseInt(idText);
            Artikel treffer = ARTIKEL.stream().filter(a -> a.id() == id).findFirst().orElse(null);
            if (treffer == null) {
                antworte(exchange, 404, "{\"fehler\":\"kein Artikel " + id + "\"}");
            } else {
                antworte(exchange, 200, treffer.alsJson());
            }
        } catch (NumberFormatException e) {
            antworte(exchange, 400, "{\"fehler\":\"id muss eine Zahl sein\"}");
        }
    }

    private void antworte(HttpExchange exchange, int status, String json) throws IOException {
        byte[] daten = json.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().set("Content-Type", JSON);
        exchange.sendResponseHeaders(status, daten.length);
        try (OutputStream aus = exchange.getResponseBody()) {
            aus.write(daten);
        }
    }
}

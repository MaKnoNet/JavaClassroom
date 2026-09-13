package de.makno.lernen;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/** Die andere Richtung: Wir sind der Client und holen Wetter von einem fremden Server. */
public class WetterAbruf {

    private static final int OK = 200;

    private final String basisUrl;
    private final HttpClient client = HttpClient.newHttpClient();
    private final ObjectMapper json = new ObjectMapper();

    public WetterAbruf(String basisUrl) {
        this.basisUrl = basisUrl;
    }

    /**
     * Ruft GET {basisUrl}/api/wetter/{stadt} auf und wandelt das JSON in ein {@link Wetter}.
     *
     * @throws IllegalStateException wenn der Server nicht mit 200 antwortet
     */
    public Wetter hole(String stadt) throws IOException, InterruptedException {
        HttpRequest anfrage = HttpRequest.newBuilder(URI.create(basisUrl + "/api/wetter/" + stadt))
                .GET()
                .build();
        HttpResponse<String> antwort = client.send(anfrage, HttpResponse.BodyHandlers.ofString());
        if (antwort.statusCode() != OK) {
            throw new IllegalStateException("Wetterdienst antwortete mit " + antwort.statusCode());
        }
        return json.readValue(antwort.body(), Wetter.class);
    }
}

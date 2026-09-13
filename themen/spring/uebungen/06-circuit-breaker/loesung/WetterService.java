package de.makno.lernen;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/** Liefert Wetter für eine Stadt – und tut das auch, wenn der Dienst dahinter wackelt. */
@Service
public class WetterService {

    private static final Logger LOG = LoggerFactory.getLogger(WetterService.class);

    private final WetterAbruf abruf;

    public WetterService(WetterAbruf abruf) {
        this.abruf = abruf;
    }

    /**
     * Der Schutzschalter „wetter" (Schwellen in application.properties) zählt Fehler mit.
     * Ist er offen, wird {@link #abruf} gar nicht erst gerufen – der Aufruf landet sofort
     * im Fallback, und der kranke Dienst bekommt Zeit, sich zu erholen.
     */
    @CircuitBreaker(name = "wetter", fallbackMethod = "unbekannt")
    public Wetter fuer(String stadt) {
        return abruf.hole(stadt);
    }

    /** Fallback: gleiche Parameter plus die Ursache; wird bei jedem Fehler UND bei offenem Schalter gerufen. */
    private Wetter unbekannt(String stadt, Throwable ursache) {
        LOG.warn("Kein Wetter für {}: {}", stadt, ursache.toString());
        return Wetter.unbekannt(stadt);
    }
}

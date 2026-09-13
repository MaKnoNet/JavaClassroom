package de.makno.lernen;

import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import org.springframework.stereotype.Service;

/** Kennt das Wetter für drei Städte. Fertig – im echten Leben stünde hier eine Datenbank. */
@Service
public class WetterService {

    private static final Map<String, Wetter> DATEN = Map.of(
            "berlin", new Wetter("Berlin", 21.5),
            "hamburg", new Wetter("Hamburg", 18.0),
            "muenchen", new Wetter("München", 24.0));

    /** @return das Wetter, oder leer, wenn die Stadt unbekannt ist. */
    public Optional<Wetter> fuer(String stadt) {
        return Optional.ofNullable(DATEN.get(stadt.toLowerCase(Locale.ROOT)));
    }
}

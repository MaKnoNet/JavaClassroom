package de.makno.lernen;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/** Auswertungen über eine Mitarbeiterliste – ausschließlich mit Streams, ohne Schleifen. */
public class Auswertung {

    /** Alle Namen, alphabetisch sortiert. */
    public List<String> namenSortiert(List<Mitarbeiter> mitarbeiter) {
        throw new UnsupportedOperationException("noch nicht umgesetzt");
    }

    /** Nur die Namen der Mitarbeiter einer Abteilung, mit ", " verbunden – z. B. "Anna, Ben". */
    public String namenDerAbteilung(List<Mitarbeiter> mitarbeiter, String abteilung) {
        throw new UnsupportedOperationException("noch nicht umgesetzt");
    }

    /** Summe der Gehälter je Abteilung. */
    public Map<String, Double> gehaltssummeProAbteilung(List<Mitarbeiter> mitarbeiter) {
        throw new UnsupportedOperationException("noch nicht umgesetzt");
    }

    /** Der Mitarbeiter mit dem höchsten Gehalt; leer, wenn die Liste leer ist. */
    public Optional<Mitarbeiter> bestverdiener(List<Mitarbeiter> mitarbeiter) {
        throw new UnsupportedOperationException("noch nicht umgesetzt");
    }
}

package de.makno.lernen;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/** Auswertungen über eine Mitarbeiterliste – ausschließlich mit Streams, ohne Schleifen. */
public class Auswertung {

    private static final String TRENNZEICHEN = ", ";

    public List<String> namenSortiert(List<Mitarbeiter> mitarbeiter) {
        return mitarbeiter.stream()
                .map(Mitarbeiter::name)
                .sorted()
                .toList();
    }

    public String namenDerAbteilung(List<Mitarbeiter> mitarbeiter, String abteilung) {
        return mitarbeiter.stream()
                .filter(m -> m.abteilung().equals(abteilung))
                .map(Mitarbeiter::name)
                .collect(Collectors.joining(TRENNZEICHEN));
    }

    public Map<String, Double> gehaltssummeProAbteilung(List<Mitarbeiter> mitarbeiter) {
        return mitarbeiter.stream()
                .collect(Collectors.groupingBy(
                        Mitarbeiter::abteilung,
                        Collectors.summingDouble(Mitarbeiter::gehalt)));
    }

    public Optional<Mitarbeiter> bestverdiener(List<Mitarbeiter> mitarbeiter) {
        return mitarbeiter.stream()
                .max(Comparator.comparingDouble(Mitarbeiter::gehalt));
    }
}

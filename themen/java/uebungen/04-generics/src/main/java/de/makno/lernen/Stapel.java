package de.makno.lernen;

import java.util.ArrayList;
import java.util.List;

/**
 * Ein Stapel (LIFO) – bisher nur für Strings. Aufgabe: generisch machen, damit er für
 * jeden Typ funktioniert, ohne Casts und ohne Object.
 */
public class Stapel {

    private final List<String> elemente = new ArrayList<>();

    public void lege(String element) {
        elemente.add(element);
    }

    public String nimm() {
        if (elemente.isEmpty()) {
            throw new IllegalStateException("Stapel ist leer");
        }
        return elemente.remove(elemente.size() - 1);
    }

    public boolean istLeer() {
        return elemente.isEmpty();
    }
}

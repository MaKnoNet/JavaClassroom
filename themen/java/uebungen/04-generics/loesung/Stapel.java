package de.makno.lernen;

import java.util.ArrayList;
import java.util.List;

/** Ein Stapel (LIFO) für beliebige Typen. */
public class Stapel<T> {

    private final List<T> elemente = new ArrayList<>();

    public void lege(T element) {
        elemente.add(element);
    }

    public T nimm() {
        if (elemente.isEmpty()) {
            throw new IllegalStateException("Stapel ist leer");
        }
        return elemente.remove(elemente.size() - 1);
    }

    public boolean istLeer() {
        return elemente.isEmpty();
    }
}

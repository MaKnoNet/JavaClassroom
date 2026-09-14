package de.makno.lernen;

import java.util.ArrayList;
import java.util.List;

/** Komposition mit "*": eine Liste, im Konstruktor angelegt, nach außen nur als Kopie. */
public class Firma {

    private final String name;
    private final List<Mitarbeiter> mitarbeiter = new ArrayList<>();

    public Firma(String name) {
        this.name = name;
    }

    public void stelleEin(Mitarbeiter neu) {
        mitarbeiter.add(neu);
    }

    public List<Mitarbeiter> mitarbeiter() {
        return List.copyOf(mitarbeiter);
    }

    public String name() {
        return name;
    }
}

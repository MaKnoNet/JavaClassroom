package de.makno.lernen;

/** Eine Warengruppe. Noch eine gewöhnliche Klasse – die Datenbank kennt sie nicht. */
public class Kategorie {

    private Long id;
    private String name;

    public Kategorie(String name) {
        this.name = name;
    }

    public Long id() {
        return id;
    }

    public String name() {
        return name;
    }
}

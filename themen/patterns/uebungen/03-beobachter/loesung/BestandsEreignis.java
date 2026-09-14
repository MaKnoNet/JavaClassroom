package de.makno.lernen;

/** Push-Variante: Das Ereignis trägt alles, was ein Beobachter wissen muss. */
public record BestandsEreignis(String artikel, int bestand) {}

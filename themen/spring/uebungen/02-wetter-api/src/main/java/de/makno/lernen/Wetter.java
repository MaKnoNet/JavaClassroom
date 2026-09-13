package de.makno.lernen;

/** Das, was über die Leitung geht – Jackson macht daraus JSON und zurück. */
public record Wetter(String stadt, double temperatur) {}

package de.makno.lernen;

/**
 * Ein gewöhnliches, unveränderliches Wertobjekt. Wie viele es gibt, entscheidet der
 * Aufrufer – im Betrieb eines (vom Container erzeugt), im Test so viele wie nötig.
 */
public record Konfiguration(int mwstProzent, String waehrung) {}

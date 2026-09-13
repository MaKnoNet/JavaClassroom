package de.makno.lernen;

/** Zugriff auf den Lagerbestand – im echten System eine Datenbank oder ein Webservice. */
public interface Lager {

    boolean istVerfuegbar(String artikel, int menge);

    void entnehme(String artikel, int menge);
}

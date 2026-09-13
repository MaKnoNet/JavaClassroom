package de.makno.lernen;

/**
 * Ein Bankkonto ohne Kapselung: Jeder kann den Kontostand direkt setzen – auch auf
 * Unsinn. Aufgabe: das Feld verstecken und den Zugriff über prüfende Methoden führen.
 */
public class Bankkonto {

    public String inhaber;
    public double kontostand;
}

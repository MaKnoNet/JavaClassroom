package de.makno.lernen;

/**
 * Überflüssig geworden: new Auto(new Elektromotor()) tut dasselbe. Die Klasse bleibt nur,
 * damit alter Code weiter kompiliert – im echten Projekt löscht man sie.
 */
public class ElektroAuto extends Auto {

    public ElektroAuto() {
        super(new Elektromotor());
    }
}

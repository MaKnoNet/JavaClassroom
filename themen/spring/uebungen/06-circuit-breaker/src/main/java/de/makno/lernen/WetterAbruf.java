package de.makno.lernen;

/**
 * Der fremde Dienst – im echten Leben ein HTTP-Aufruf (Spring-Übung 02). Hier nur die
 * Schnittstelle; der Test stellt eine Attrappe bereit, die nach Wunsch ausfällt.
 */
public interface WetterAbruf {

    /** @throws IllegalStateException wenn der Dienst nicht antwortet */
    Wetter hole(String stadt);
}

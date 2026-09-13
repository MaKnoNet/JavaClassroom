package de.makno.lernen;

/** Ein Wetterbericht; {@link #unbekannt(String)} ist die Antwort, wenn der Dienst nicht erreichbar ist. */
public record Wetter(String stadt, double temperatur, String zustand) {

    public static Wetter unbekannt(String stadt) {
        return new Wetter(stadt, Double.NaN, "unbekannt");
    }
}

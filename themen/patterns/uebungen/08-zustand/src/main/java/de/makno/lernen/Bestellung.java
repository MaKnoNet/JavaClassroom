package de.makno.lernen;

/**
 * Eine Bestellung mit Lebenszyklus: NEU → BEZAHLT → VERSANDT; stornieren geht nur, solange
 * nichts versandt ist. Jede Methode prüft den Zustand selbst – vier Methoden, je ein if.
 * Ein neuer Zustand heißt: alle vier anfassen.
 */
public class Bestellung {

    public enum Status { NEU, BEZAHLT, VERSANDT, STORNIERT }

    private Status status = Status.NEU;

    public void bezahle() {
        if (status != Status.NEU) {
            throw new IllegalStateException("Bezahlen nicht möglich im Zustand " + status);
        }
        status = Status.BEZAHLT;
    }

    public void versende() {
        if (status != Status.BEZAHLT) {
            throw new IllegalStateException("Versenden nicht möglich im Zustand " + status);
        }
        status = Status.VERSANDT;
    }

    public void storniere() {
        if (status == Status.VERSANDT || status == Status.STORNIERT) {
            throw new IllegalStateException("Stornieren nicht möglich im Zustand " + status);
        }
        status = Status.STORNIERT;
    }

    public String status() {
        return status.name();
    }
}

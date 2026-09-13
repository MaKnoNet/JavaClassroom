package de.makno.lernen;

/** Nimmt Bestellungen an. Fertig – getestet wird er, nicht geändert. */
public class Bestellservice {

    private final Lager lager;
    private final Benachrichtigung benachrichtigung;

    public Bestellservice(Lager lager, Benachrichtigung benachrichtigung) {
        this.lager = lager;
        this.benachrichtigung = benachrichtigung;
    }

    /** @return true, wenn die Bestellung ausgeführt wurde. */
    public boolean bestelle(String kunde, String artikel, int menge) {
        if (!lager.istVerfuegbar(artikel, menge)) {
            benachrichtigung.sende(kunde, "Leider nicht verfügbar: " + artikel);
            return false;
        }
        lager.entnehme(artikel, menge);
        benachrichtigung.sende(kunde, "Bestellung bestätigt: " + menge + " x " + artikel);
        return true;
    }
}

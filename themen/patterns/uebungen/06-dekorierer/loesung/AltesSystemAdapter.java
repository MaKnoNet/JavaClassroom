package de.makno.lernen;

/**
 * Adapter: macht aus der fremden Schnittstelle (String rein, CSV-Zeile raus) unser
 * Interface. Anders als der Dekorierer ändert er den Typ, statt ihn zu behalten.
 */
public class AltesSystemAdapter implements Kundendienst {

    private final AltesKundenSystem altesSystem;

    public AltesSystemAdapter(AltesKundenSystem altesSystem) {
        this.altesSystem = altesSystem;
    }

    @Override
    public String kunde(int nummer) {
        String[] felder = altesSystem.holeKundenSatz(String.valueOf(nummer)).split(";");
        return felder[1];
    }
}

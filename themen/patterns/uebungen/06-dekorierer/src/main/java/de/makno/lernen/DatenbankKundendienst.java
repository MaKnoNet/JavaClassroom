package de.makno.lernen;

/** Der "teure" Zugriff – steht hier für eine echte Datenbankabfrage. Zählt seine Aufrufe. */
public class DatenbankKundendienst implements Kundendienst {

    private int aufrufe;

    @Override
    public String kunde(int nummer) {
        aufrufe++;
        return "Kunde " + nummer;
    }

    public int aufrufe() {
        return aufrufe;
    }
}

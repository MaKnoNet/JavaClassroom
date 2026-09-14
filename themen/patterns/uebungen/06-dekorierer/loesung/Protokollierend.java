package de.makno.lernen;

import java.util.function.Consumer;

/** Dekorierer: protokolliert und reicht weiter. Vor oder nach dem Cache – die Reihenfolge entscheidet der Aufrufer. */
public class Protokollierend implements Kundendienst {

    private final Kundendienst innen;
    private final Consumer<String> protokoll;

    public Protokollierend(Kundendienst innen, Consumer<String> protokoll) {
        this.innen = innen;
        this.protokoll = protokoll;
    }

    @Override
    public String kunde(int nummer) {
        protokoll.accept("kunde(" + nummer + ")");
        return innen.kunde(nummer);
    }
}

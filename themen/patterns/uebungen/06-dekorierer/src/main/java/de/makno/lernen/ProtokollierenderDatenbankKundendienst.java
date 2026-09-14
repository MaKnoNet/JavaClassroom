package de.makno.lernen;

import java.util.function.Consumer;

/** Erster Versuch per Vererbung: eine Unterklasse, die jeden Aufruf protokolliert. */
public class ProtokollierenderDatenbankKundendienst extends DatenbankKundendienst {

    private final Consumer<String> protokoll;

    public ProtokollierenderDatenbankKundendienst(Consumer<String> protokoll) {
        this.protokoll = protokoll;
    }

    @Override
    public String kunde(int nummer) {
        protokoll.accept("kunde(" + nummer + ")");
        return super.kunde(nummer);
    }
}

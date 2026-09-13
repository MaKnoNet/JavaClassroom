package de.makno.lernen;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/** Das Datenmodell – Records, weil JSON-Daten unveränderlich durch das System fließen. */
public record Bestellung(int nummer, String kunde, LocalDate datum, List<Position> positionen) {

    public record Position(String artikel, int menge, BigDecimal preis) {}
}

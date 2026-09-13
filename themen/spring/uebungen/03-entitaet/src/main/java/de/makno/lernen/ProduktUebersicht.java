package de.makno.lernen;

import java.math.BigDecimal;

/**
 * Das Produkt, wie es nach außen geht (Liste, JSON): unveränderlich, ohne Datenbank-Id.
 * Ein Record darf das sein – eine Entity nicht.
 */
public record ProduktUebersicht(String name, BigDecimal preis, String kategorie) {

    public static ProduktUebersicht von(Produkt produkt) {
        String kategorie = produkt.kategorie() == null ? "" : produkt.kategorie().name();
        return new ProduktUebersicht(produkt.name(), produkt.preis(), kategorie);
    }
}

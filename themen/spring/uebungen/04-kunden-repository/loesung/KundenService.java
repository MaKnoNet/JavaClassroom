package de.makno.lernen;

import java.math.BigDecimal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** Geschäftslogik über Kunden – kennt die Datenbank nur über das Repository. */
@Service
public class KundenService {

    private final KundenRepository kunden;

    public KundenService(KundenRepository kunden) {
        this.kunden = kunden;
    }

    /**
     * Bucht {@code betrag} von einem Kunden auf einen anderen um – ganz oder gar nicht.
     *
     * @throws IllegalArgumentException wenn eine der E-Mail-Adressen unbekannt ist
     */
    @Transactional
    public void uebertrage(String vonEmail, String nachEmail, BigDecimal betrag) {
        Kunde von = finde(vonEmail);
        von.buche(betrag.negate());
        kunden.save(von);

        Kunde nach = finde(nachEmail);
        nach.buche(betrag);
        kunden.save(nach);
    }

    private Kunde finde(String email) {
        return kunden.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Unbekannter Kunde: " + email));
    }
}

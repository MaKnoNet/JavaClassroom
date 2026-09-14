package de.makno.lernen;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

/** Dekorierer: Eigenschaften als Hüllen um dasselbe Interface – beliebig kombinierbar. Adapter: fremde Schnittstelle anpassen. */
class KundendienstTest {

    @Test
    void cacheVermeidetDenZweitenDatenbankzugriff() {
        DatenbankKundendienst datenbank = new DatenbankKundendienst();
        Kundendienst dienst = new Cachend(datenbank);

        assertEquals("Kunde 7", dienst.kunde(7));
        assertEquals("Kunde 7", dienst.kunde(7));
        assertEquals(1, datenbank.aufrufe());
    }

    @Test
    void protokollUmCacheSiehtJedenAufruf() {
        DatenbankKundendienst datenbank = new DatenbankKundendienst();
        List<String> protokoll = new ArrayList<>();
        Kundendienst dienst = new Protokollierend(new Cachend(datenbank), protokoll::add);

        dienst.kunde(1);
        dienst.kunde(1);

        assertEquals(List.of("kunde(1)", "kunde(1)"), protokoll);
        assertEquals(1, datenbank.aufrufe());
    }

    @Test
    void cacheUmProtokollSiehtNurDenErstenAufruf() {
        DatenbankKundendienst datenbank = new DatenbankKundendienst();
        List<String> protokoll = new ArrayList<>();
        Kundendienst dienst = new Cachend(new Protokollierend(datenbank, protokoll::add));

        dienst.kunde(1);
        dienst.kunde(1);

        assertEquals(List.of("kunde(1)"), protokoll);
    }

    @Test
    void dekoriererSindKeineUnterklassenDerDatenbank() {
        assertTrue(Kundendienst.class.isAssignableFrom(Cachend.class));
        assertTrue(Kundendienst.class.isAssignableFrom(Protokollierend.class));
        assertFalse(DatenbankKundendienst.class.isAssignableFrom(Cachend.class), "Cachend erbt nicht von der Datenbank");
        assertFalse(DatenbankKundendienst.class.isAssignableFrom(Protokollierend.class), "Protokollierend erbt nicht von der Datenbank");
    }

    @Test
    void adapterMachtDasAlteSystemZumKundendienst() {
        Kundendienst dienst = new AltesSystemAdapter(new AltesKundenSystem());

        assertEquals("Kunde 42", dienst.kunde(42));
    }

    @Test
    void adapterLaesstSichWieJederKundendienstDekorieren() {
        List<String> protokoll = new ArrayList<>();
        Kundendienst dienst = new Protokollierend(new Cachend(new AltesSystemAdapter(new AltesKundenSystem())), protokoll::add);

        assertEquals("Kunde 3", dienst.kunde(3));
        assertEquals(List.of("kunde(3)"), protokoll);
    }
}

package de.makno.lernen;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

/** Das Lager meldet Änderungen an Beobachter, die es nicht kennt. */
class LagerTest {

    @Test
    void beobachterErfaehrtJedeBestandsaenderung() {
        Lager lager = new Lager();
        List<String> gesehen = new ArrayList<>();
        lager.registriere(ereignis -> gesehen.add(ereignis.artikel() + ":" + ereignis.bestand()));

        lager.lege("Schraube", 10);
        lager.entnimm("Schraube", 4);

        assertEquals(List.of("Schraube:10", "Schraube:6"), gesehen);
    }

    @Test
    void abgemeldeterBeobachterBekommtNichtsMehr() {
        Lager lager = new Lager();
        List<String> gesehen = new ArrayList<>();
        BestandsBeobachter beobachter = ereignis -> gesehen.add(ereignis.artikel());
        lager.registriere(beobachter);
        lager.lege("Mutter", 1);

        lager.entferne(beobachter);
        lager.lege("Mutter", 1);

        assertEquals(List.of("Mutter"), gesehen);
    }

    @Test
    void anzeigeUndProtokollSindGewoehnlicheBeobachter() {
        Lager lager = new Lager();
        Anzeige anzeige = new Anzeige();
        Protokoll protokoll = new Protokoll();
        lager.registriere(anzeige);
        lager.registriere(protokoll);

        lager.lege("Hammer", 3);
        lager.entnimm("Hammer", 1);

        assertEquals("Hammer: 2 Stück", anzeige.letzteZeile());
        assertEquals(List.of("Hammer=3", "Hammer=2"), protokoll.zeilen());
    }

    @Test
    void lagerKenntKeineKonkretenBeobachterklassen() {
        boolean kenntAnzeigeOderProtokoll = Arrays.stream(Lager.class.getDeclaredFields())
                .anyMatch(f -> f.getType() == Anzeige.class || f.getType() == Protokoll.class);

        assertTrue(!kenntAnzeigeOderProtokoll, "Lager darf kein Feld vom Typ Anzeige oder Protokoll haben");
    }

    @Test
    void fehlerInEinemBeobachterStopptDieAnderenNicht() {
        Lager lager = new Lager();
        List<String> gesehen = new ArrayList<>();
        lager.registriere(ereignis -> { throw new IllegalStateException("Anzeige kaputt"); });
        lager.registriere(ereignis -> gesehen.add(ereignis.artikel()));

        lager.lege("Säge", 1);

        assertEquals(List.of("Säge"), gesehen);
        assertEquals(1, lager.bestand("Säge"));
    }
}

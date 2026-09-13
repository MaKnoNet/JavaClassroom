package de.makno.lernen;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.Test;

/** Diese Tests kompilieren erst, wenn Stapel, Paar und Werkzeuge generisch sind. */
class GenericsTest {

    @Test
    void stapelFuerZahlenOhneCast() {
        Stapel<Integer> stapel = new Stapel<>();
        stapel.lege(41);
        stapel.lege(42);
        int oben = stapel.nimm();
        assertEquals(42, oben);
    }

    @Test
    void stapelFuerStringsFunktioniertWeiter() {
        Stapel<String> stapel = new Stapel<>();
        stapel.lege("a");
        String oben = stapel.nimm();
        assertEquals("a", oben);
        assertTrue(stapel.istLeer());
    }

    @Test
    void groesstesFunktioniertFuerZahlenUndTexte() {
        int zahl = Werkzeuge.groesstes(List.of(3, 9, 2));
        String text = Werkzeuge.groesstes(List.of("Birne", "Apfel", "Zitrone"));
        assertEquals(9, zahl);
        assertEquals("Zitrone", text);
    }

    @Test
    void paarBehaeltBeideTypen() {
        Paar<String, Integer> paar = new Paar<>("Anna", 30);
        String name = paar.erstes();
        int alter = paar.zweites();
        assertEquals("Anna", name);
        assertEquals(30, alter);
    }

    @Test
    void endenVonLiefertErstesUndLetztes() {
        Paar<String, String> enden = Werkzeuge.endenVon(List.of("x", "y", "z"));
        assertEquals("x", enden.erstes());
        assertEquals("z", enden.zweites());
    }
}

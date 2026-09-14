package de.makno.lernen;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/** Pfeilart und Richtung entscheiden – nicht nur, dass eine Linie da ist. */
class BeziehungenTest {

    @Test
    void gueltig() throws Exception {
        Puml puml = Puml.laden();
        assertTrue(puml.syntaxOk(), "PlantUML meldet: " + puml.typ());
    }

    @Test
    void vererbungUndRealisierungZeigenZurAbstraktion() throws Exception {
        Puml puml = Puml.laden();
        assertTrue(puml.hatBeziehung("Kunde", "-+[|]>", "Person") || puml.hatBeziehung("Person", "<[|]-+", "Kunde"),
                "Kunde --|> Person (Generalisierung, leeres Dreieck an der Oberklasse)");
        assertTrue(puml.hatBeziehung("Kunde", "[.]+[|]>", "Zahlungsfaehig") || puml.hatBeziehung("Zahlungsfaehig", "<[|][.]+", "Kunde"),
                "Kunde ..|> Zahlungsfaehig (Realisierung, gestrichelt)");
    }

    @Test
    void adresseIstAggregation() throws Exception {
        Puml puml = Puml.laden();
        assertTrue(puml.hatBeziehungBeliebig("Kunde", "o-+>?", "<?-+o", "Adresse"),
                "Kunde o-- Adresse: offene Raute – die Adresse überlebt den Kunden");
    }

    @Test
    void bestellpositionIstKomposition() throws Exception {
        Puml puml = Puml.laden();
        assertTrue(puml.hatBeziehungBeliebig("Bestellung", "[*]-+>?", "<?-+[*]", "Bestellposition"),
                "Bestellung *-- Bestellposition: gefüllte Raute – ohne Bestellung keine Position");
        assertTrue(puml.hatMultiplizitaet("Bestellung", "Bestellposition", "1..*"),
                "Multiplizität 1..* am Ende der Bestellposition");
    }

    @Test
    void assoziationenMitMultiplizitaeten() throws Exception {
        Puml puml = Puml.laden();
        assertTrue(puml.hatBeziehungBeliebig("Kunde", "-+>?", "<?-+", "Bestellung"), "Kunde -- Bestellung");
        assertTrue(puml.hatMultiplizitaet("Kunde", "Bestellung", "*") || puml.hatMultiplizitaet("Kunde", "Bestellung", "0..*"),
                "* (oder 0..*) am Ende der Bestellung");
        assertTrue(puml.hatMultiplizitaet("Bestellung", "Kunde", "1"), "1 am Ende des Kunden");
        assertTrue(puml.hatBeziehungBeliebig("Bestellposition", "-+>?", "<?-+", "Artikel"), "Bestellposition --> Artikel");
        assertTrue(puml.hatMultiplizitaet("Bestellposition", "Artikel", "1"), "1 am Ende des Artikels");
    }
}

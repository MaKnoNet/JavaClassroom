package de.makno.lernen;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.Collection;
import org.junit.jupiter.api.Test;

/** Erst das Diagramm, dann der Code – beides muss zusammenpassen. */
class DiagrammZuCodeTest {

    @Test
    void diagrammOhneZyklusMitInterface() throws Exception {
        Puml puml = Puml.laden();
        assertTrue(puml.syntaxOk(), "PlantUML meldet: " + puml.typ());
        assertTrue(puml.hatElement("interface", "Gehaltsregel"), "interface Gehaltsregel");
        assertTrue(puml.hatBeziehung("StandardRegel", "[.]+[|]>", "Gehaltsregel") || puml.hatBeziehung("Gehaltsregel", "<[|][.]+", "StandardRegel"),
                "StandardRegel ..|> Gehaltsregel");
        assertTrue(puml.hatBeziehungBeliebig("Gehaltsrechner", "-+>", "<-+", "Gehaltsregel"), "Gehaltsrechner --> Gehaltsregel");
        assertFalse(puml.hatBeziehung("Mitarbeiter", "-+>", "Gehaltsrechner"), "der Zyklus Mitarbeiter --> Gehaltsrechner muss weg");
        assertFalse(puml.hatBeziehung("Gehaltsrechner", "-+>", "Mitarbeiter"), "Gehaltsrechner hängt nicht mehr an Mitarbeiter");
    }

    @Test
    void diagrammFirmaBesitztMitarbeiter() throws Exception {
        Puml puml = Puml.laden();
        assertTrue(puml.hatBeziehungBeliebig("Firma", "[*]-+>?", "<?-+[*]", "Mitarbeiter"), "Firma *-- Mitarbeiter (Komposition)");
        assertTrue(puml.hatMultiplizitaet("Firma", "Mitarbeiter", "*"), "* am Mitarbeiter-Ende");
    }

    @Test
    void codeFirmaHatEineSammlungVonMitarbeitern() throws Exception {
        Field feld = Arrays.stream(Firma.class.getDeclaredFields())
                .filter(f -> Collection.class.isAssignableFrom(f.getType()))
                .findFirst().orElseThrow(() -> new AssertionError("Firma braucht ein Feld vom Typ List<Mitarbeiter>"));
        ParameterizedType typ = (ParameterizedType) feld.getGenericType();
        assertEquals(Mitarbeiter.class, typ.getActualTypeArguments()[0], "Elementtyp Mitarbeiter");

        Firma firma = new Firma("ACME");
        firma.stelleEin(new Mitarbeiter("Anna", 3000));
        firma.stelleEin(new Mitarbeiter("Ben", 4000));
        assertEquals(2, firma.mitarbeiter().size());
    }

    @Test
    void codeGehaltsrechnerHaengtNurAmInterface() throws Exception {
        assertTrue(Gehaltsregel.class.isInterface(), "Gehaltsregel ist ein Interface");
        assertTrue(Gehaltsregel.class.isAssignableFrom(StandardRegel.class), "StandardRegel implementiert Gehaltsregel");
        assertTrue(Arrays.stream(Gehaltsrechner.class.getConstructors())
                .anyMatch(c -> Arrays.asList(c.getParameterTypes()).contains(Gehaltsregel.class)),
                "Gehaltsrechner bekommt die Gehaltsregel per Konstruktor");
        assertFalse(Arrays.stream(Gehaltsrechner.class.getDeclaredFields()).anyMatch(f -> f.getType() == Mitarbeiter.class),
                "Gehaltsrechner hält keinen Mitarbeiter als Feld");
        assertFalse(Arrays.stream(Mitarbeiter.class.getDeclaredFields()).anyMatch(f -> f.getType() == Gehaltsrechner.class),
                "Mitarbeiter kennt den Gehaltsrechner nicht (kein Zyklus)");
    }

    @Test
    void codeRechnetMitDerRegel() {
        Gehaltsrechner rechner = new Gehaltsrechner(new StandardRegel());
        assertEquals(3300.0, rechner.brutto(new Mitarbeiter("Anna", 3000)), 0.001, "StandardRegel: 10 % Zulage");

        Gehaltsrechner ohneZulage = new Gehaltsrechner(grundgehalt -> grundgehalt);
        assertEquals(3000.0, ohneZulage.brutto(new Mitarbeiter("Anna", 3000)), 0.001, "eine andere Regel als Lambda");
    }
}

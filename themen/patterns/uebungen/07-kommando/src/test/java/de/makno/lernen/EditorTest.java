package de.makno.lernen;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/** Jede Änderung ist ein Kommando-Objekt mit ausfuehren() und rueckgaengig(); der Editor führt Buch. */
class EditorTest {

    @Test
    void kommandoIstEinInterfaceMitZweiMethoden() {
        assertTrue(Kommando.class.isInterface());
        assertEquals(2, Kommando.class.getMethods().length, "ausfuehren() und rueckgaengig()");
    }

    @Test
    void kommandosAendernDenText() {
        Editor editor = new Editor();

        editor.fuehreAus(new Schreiben(editor, "Hallo"));
        editor.fuehreAus(new Schreiben(editor, " Welt"));
        editor.fuehreAus(new EntfernenVomEnde(editor, 5));

        assertEquals("Hallo", editor.text());
    }

    @Test
    void dreiSchritteRueckgaengig() {
        Editor editor = new Editor();
        editor.fuehreAus(new Schreiben(editor, "Hallo"));
        editor.fuehreAus(new Schreiben(editor, " Welt"));
        editor.fuehreAus(new EntfernenVomEnde(editor, 5));

        editor.rueckgaengig();
        assertEquals("Hallo Welt", editor.text());
        editor.rueckgaengig();
        assertEquals("Hallo", editor.text());
        editor.rueckgaengig();
        assertEquals("", editor.text());
    }

    @Test
    void entfernenMerktSichWasEsEntferntHat() {
        Editor editor = new Editor();
        editor.fuehreAus(new Schreiben(editor, "abcdef"));
        editor.fuehreAus(new EntfernenVomEnde(editor, 2));
        editor.fuehreAus(new Schreiben(editor, "XY"));

        editor.rueckgaengig();
        editor.rueckgaengig();

        assertEquals("abcdef", editor.text());
    }

    @Test
    void rueckgaengigOhneHistorieTutNichts() {
        Editor editor = new Editor();

        editor.rueckgaengig();

        assertEquals("", editor.text());
    }
}

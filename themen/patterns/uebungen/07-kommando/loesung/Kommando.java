package de.makno.lernen;

/** Eine Aktion als Objekt: kann ausgeführt, gemerkt und zurückgenommen werden. */
public interface Kommando {

    void ausfuehren();

    void rueckgaengig();
}

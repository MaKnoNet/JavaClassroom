package de.makno.lernen;

public class Schreiben implements Kommando {

    private final Editor editor;
    private final String zeichen;

    public Schreiben(Editor editor, String zeichen) {
        this.editor = editor;
        this.zeichen = zeichen;
    }

    @Override
    public void ausfuehren() {
        editor.schreibe(zeichen);
    }

    @Override
    public void rueckgaengig() {
        editor.entferneVomEnde(zeichen.length());
    }
}

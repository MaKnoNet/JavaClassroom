package de.makno.lernen;

/** Muss sich beim Ausführen merken, was es entfernt hat – sonst gibt es nichts zurückzunehmen. */
public class EntfernenVomEnde implements Kommando {

    private final Editor editor;
    private final int anzahl;
    private String entfernt = "";

    public EntfernenVomEnde(Editor editor, int anzahl) {
        this.editor = editor;
        this.anzahl = anzahl;
    }

    @Override
    public void ausfuehren() {
        String text = editor.text();
        int ab = Math.max(0, text.length() - anzahl);
        entfernt = text.substring(ab);
        editor.entferneVomEnde(anzahl);
    }

    @Override
    public void rueckgaengig() {
        editor.schreibe(entfernt);
    }
}

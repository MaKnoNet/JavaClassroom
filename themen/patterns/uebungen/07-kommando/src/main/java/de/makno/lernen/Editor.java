package de.makno.lernen;

/** Ein Texteditor, der seinen Text direkt ändert. Rückgängig? Dafür müsste man wissen, was war. */
public class Editor {

    private final StringBuilder text = new StringBuilder();

    public void schreibe(String zeichen) {
        text.append(zeichen);
    }

    public void entferneVomEnde(int anzahl) {
        int neueLaenge = Math.max(0, text.length() - anzahl);
        text.setLength(neueLaenge);
    }

    public String text() {
        return text.toString();
    }
}

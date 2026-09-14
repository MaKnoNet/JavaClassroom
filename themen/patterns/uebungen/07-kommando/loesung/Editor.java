package de.makno.lernen;

import java.util.ArrayDeque;
import java.util.Deque;

/** Der Editor führt Kommandos aus und merkt sie sich – das ist die Undo-Historie. */
public class Editor {

    private final StringBuilder text = new StringBuilder();
    private final Deque<Kommando> historie = new ArrayDeque<>();

    public void fuehreAus(Kommando kommando) {
        kommando.ausfuehren();
        historie.push(kommando);
    }

    public void rueckgaengig() {
        if (!historie.isEmpty()) {
            historie.pop().rueckgaengig();
        }
    }

    // Die eigentlichen Operationen bleiben – die Kommandos rufen sie.
    public void schreibe(String zeichen) {
        text.append(zeichen);
    }

    public void entferneVomEnde(int anzahl) {
        text.setLength(Math.max(0, text.length() - anzahl));
    }

    public String text() {
        return text.toString();
    }
}

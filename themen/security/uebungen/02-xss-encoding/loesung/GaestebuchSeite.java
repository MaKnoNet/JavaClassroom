package de.makno.lernen;

import java.util.List;
import org.owasp.encoder.Encode;

/**
 * Erzeugt das HTML der Gästebuch-Seite aus den Einträgen. Alles, was vom Nutzer kommt,
 * wird beim AUSGEBEN maskiert – passend zum Kontext, in dem es landet.
 */
public class GaestebuchSeite {

    public String html(List<Kommentar> kommentare) {
        StringBuilder html = new StringBuilder("<ul class=\"gaestebuch\">\n");
        for (Kommentar kommentar : kommentare) {
            html.append("  <li title=\"von ").append(Encode.forHtmlAttribute(kommentar.autor())).append("\">")
                    .append(Encode.forHtml(kommentar.text()))
                    .append("</li>\n");
        }
        return html.append("</ul>\n").toString();
    }
}

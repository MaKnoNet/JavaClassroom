package de.makno.lernen;

import java.util.List;

/** Erzeugt das HTML der Gästebuch-Seite aus den Einträgen. */
public class GaestebuchSeite {

    public String html(List<Kommentar> kommentare) {
        StringBuilder html = new StringBuilder("<ul class=\"gaestebuch\">\n");
        for (Kommentar kommentar : kommentare) {
            html.append("  <li title=\"von ").append(kommentar.autor()).append("\">")
                    .append(kommentar.text())
                    .append("</li>\n");
        }
        return html.append("</ul>\n").toString();
    }
}

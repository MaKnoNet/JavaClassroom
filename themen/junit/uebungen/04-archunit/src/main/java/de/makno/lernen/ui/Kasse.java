package de.makno.lernen.ui;

import de.makno.lernen.service.Preisrechner;

/** Oberfläche darf die Fachlogik benutzen – diese Richtung ist erlaubt. */
public class Kasse {

    private final Preisrechner preisrechner = new Preisrechner();

    public void bon(double netto) {
        Konsole.zeige("Zu zahlen: " + preisrechner.brutto(netto));
    }
}

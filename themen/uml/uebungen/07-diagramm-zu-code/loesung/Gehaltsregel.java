package de.makno.lernen;

/** Das Interface aus dem Diagramm – eine Methode, deshalb auch als Lambda einsetzbar. */
@FunctionalInterface
public interface Gehaltsregel {

    double berechne(double grundgehalt);
}

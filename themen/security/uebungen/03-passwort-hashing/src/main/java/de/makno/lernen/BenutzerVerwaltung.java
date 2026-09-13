package de.makno.lernen;

import java.util.HashMap;
import java.util.Map;

/**
 * Registrierung und Anmeldung. Die Map steht für die Benutzertabelle in der Datenbank –
 * also für das, was bei einem Einbruch oder einem verlorenen Backup in fremde Hände fällt.
 */
public class BenutzerVerwaltung {

    private final Map<String, String> gespeichert = new HashMap<>();

    public void registriere(String benutzername, String passwort) {
        gespeichert.put(benutzername, passwort);
    }

    public boolean anmelden(String benutzername, String passwort) {
        String abgelegt = gespeichert.get(benutzername);
        return abgelegt != null && abgelegt.equals(passwort);
    }

    /** Was in der „Datenbank" steht – für den Test, der hineinschaut wie ein Angreifer. */
    String gespeicherterWert(String benutzername) {
        return gespeichert.get(benutzername);
    }
}

package de.makno.lernen;

import java.util.HashMap;
import java.util.Map;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Registrierung und Anmeldung. Gespeichert wird nie das Passwort, sondern ein Hash mit
 * zufälligem Salt – wer die Tabelle stiehlt, kann damit nichts anfangen.
 */
public class BenutzerVerwaltung {

    // BCrypt: absichtlich langsam (Kostenfaktor), Salt eingebaut, Ergebnis "$2a$10$…".
    // Argon2PasswordEncoder wäre die modernere Wahl; braucht zusätzlich Bouncy Castle.
    private final PasswordEncoder encoder = new BCryptPasswordEncoder();

    private final Map<String, String> gespeichert = new HashMap<>();

    public void registriere(String benutzername, String passwort) {
        gespeichert.put(benutzername, encoder.encode(passwort));
    }

    public boolean anmelden(String benutzername, String passwort) {
        String hash = gespeichert.get(benutzername);
        // matches() hasht die Eingabe mit dem Salt aus dem gespeicherten Hash und vergleicht.
        return hash != null && encoder.matches(passwort, hash);
    }

    /** Was in der „Datenbank" steht – für den Test, der hineinschaut wie ein Angreifer. */
    String gespeicherterWert(String benutzername) {
        return gespeichert.get(benutzername);
    }
}

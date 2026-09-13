package de.makno.lernen;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.jetbrains.annotations.NotNull;

/** Spricht über JDBC mit einer H2-Datenbank im Speicher. Fertig – nicht ändern. */
public class Datenbank {

    private static final String URL = "jdbc:h2:mem:lernen";

    /** @return die Antwort der Datenbank auf `SELECT 'Hallo ' || ?`. */
    public @NotNull String begruesse(@NotNull String name) throws SQLException {
        try (Connection verbindung = DriverManager.getConnection(URL);
                Statement anweisung = verbindung.createStatement();
                ResultSet ergebnis = anweisung.executeQuery("SELECT 'Hallo ' || '" + name + "'")) {
            ergebnis.next();
            return ergebnis.getString(1);
        }
    }
}

package de.makno.lernen;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Sucht Kunden per JDBC nach ihrem Namen. Der Name kommt vom Nutzer – deshalb geht er
 * als Parameter in die Abfrage, nie als Teil des SQL-Textes. Nicht thread-safe; eine
 * Instanz pro Aufruf.
 */
public class KundenSuche {

    // Das SQL ist konstant; das ? ist ein Platzhalter, den der Treiber getrennt vom Text überträgt.
    private static final String SUCHE_NACH_NAME = "select id, name, rolle from kunde where name = ?";

    private final String jdbcUrl;

    public KundenSuche(String jdbcUrl) {
        this.jdbcUrl = jdbcUrl;
    }

    /** Liefert alle Kunden mit genau diesem Namen. */
    public List<Kunde> nachName(String name) throws SQLException {
        List<Kunde> treffer = new ArrayList<>();
        try (Connection verbindung = DriverManager.getConnection(jdbcUrl);
                PreparedStatement anweisung = verbindung.prepareStatement(SUCHE_NACH_NAME)) {
            anweisung.setString(1, name);
            try (ResultSet zeilen = anweisung.executeQuery()) {
                while (zeilen.next()) {
                    treffer.add(new Kunde(zeilen.getInt("id"), zeilen.getString("name"), zeilen.getString("rolle")));
                }
            }
        }
        return treffer;
    }
}

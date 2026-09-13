package de.makno.lernen;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Sucht Kunden per JDBC nach ihrem Namen – so, wie es in einem Suchfeld auf einer
 * Webseite ankommt. Der Name kommt vom Nutzer. Nicht thread-safe; eine Instanz pro Aufruf.
 */
public class KundenSuche {

    private final String jdbcUrl;

    public KundenSuche(String jdbcUrl) {
        this.jdbcUrl = jdbcUrl;
    }

    /** Liefert alle Kunden mit genau diesem Namen. */
    public List<Kunde> nachName(String name) throws SQLException {
        String sql = "select id, name, rolle from kunde where name = '" + name + "'";
        List<Kunde> treffer = new ArrayList<>();
        try (Connection verbindung = DriverManager.getConnection(jdbcUrl);
                Statement anweisung = verbindung.createStatement();
                ResultSet zeilen = anweisung.executeQuery(sql)) {
            while (zeilen.next()) {
                treffer.add(new Kunde(zeilen.getInt("id"), zeilen.getString("name"), zeilen.getString("rolle")));
            }
        }
        return treffer;
    }
}

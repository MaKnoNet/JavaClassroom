package de.makno.lernen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/** Kleine Kundenverwaltung: GET und POST unter /api/kunden. Fertig – hier wird nur verpackt. */
@SpringBootApplication
public class KundenApiAnwendung {

    public static void main(String[] args) {
        SpringApplication.run(KundenApiAnwendung.class, args);
    }
}

package de.makno.lernen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/** Startpunkt. Beim Start legt Hibernate die Tabellen in der H2-Datenbank an. */
@SpringBootApplication
public class LagerAnwendung {

    public static void main(String[] args) {
        SpringApplication.run(LagerAnwendung.class, args);
    }
}

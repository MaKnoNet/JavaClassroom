package de.makno.lernen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/** Startpunkt: Von hier aus sucht Spring alle Komponenten im Paket `de.makno.lernen`. */
@SpringBootApplication
public class AutomatAnwendung {

    public static void main(String[] args) {
        SpringApplication.run(AutomatAnwendung.class, args);
    }
}

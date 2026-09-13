package de.makno.lernen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/** Startpunkt. `./gradlew bootRun` startet den Server auf http://localhost:8080. */
@SpringBootApplication
public class WetterAnwendung {

    public static void main(String[] args) {
        SpringApplication.run(WetterAnwendung.class, args);
    }
}

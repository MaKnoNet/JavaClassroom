package de.makno.lernen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/** Startpunkt: in der IDE als Java-Application starten oder `./gradlew bootRun`. */
@SpringBootApplication
public class Anwendung {

    public static void main(String[] args) {
        SpringApplication.run(Anwendung.class, args);
    }
}

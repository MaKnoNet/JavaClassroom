package de.makno.lernen;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;

/** Gibt eine Begrüßung als JSON aus – mit Jackson, also mit einer fremden Bibliothek. */
public class App {

    public static void main(String[] args) throws Exception {
        String name = args.length > 0 ? args[0] : "Welt";
        String json = new ObjectMapper().writeValueAsString(Map.of("gruss", "Hallo " + name));
        System.out.println(json);
    }
}

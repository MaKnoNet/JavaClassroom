package de.makno.lernen;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class HalloTest {

    @Test
    void liefertText() {
        assertEquals("Hallo Gradle", new Hallo().text());
    }
}

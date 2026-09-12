package de.makno.lernen;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class SchaltjahrTest {

    @ParameterizedTest(name = "{0} ist Schaltjahr: {1}")
    @CsvSource({
        "2024, true",
        "2023, false",
        "1900, false",
        "2000, true"
    })
    void erkenntSchaltjahre(int jahr, boolean erwartet) {
        assertEquals(erwartet, Schaltjahr.istSchaltjahr(jahr));
    }
}

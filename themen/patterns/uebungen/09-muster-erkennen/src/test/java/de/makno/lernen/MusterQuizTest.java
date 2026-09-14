package de.makno.lernen;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import org.junit.jupiter.api.Test;

class MusterQuizTest {

    @Test
    void musterImJdkUndInSpring() {
        assertEquals(Muster.STRATEGIE, MusterQuiz.musterVon("liste.sort(Comparator.comparing(Kunde::name))"));
        assertEquals(Muster.DEKORIERER, MusterQuiz.musterVon("new BufferedReader(new InputStreamReader(System.in))"));
        assertEquals(Muster.BUILDER, MusterQuiz.musterVon("HttpRequest.newBuilder().uri(uri).GET().build()"));
        assertEquals(Muster.BEOBACHTER, MusterQuiz.musterVon("button.addClickListener(e -> speichern())"));
        assertEquals(Muster.SCHABLONENMETHODE, MusterQuiz.musterVon("jdbcTemplate.query(sql, (rs, zeile) -> new Kunde(rs.getString(1)))"));
        assertEquals(Muster.PROXY, MusterQuiz.musterVon("@Transactional public void ueberweise(...) auf einer Spring-Bean"));
        assertEquals(Muster.FABRIKMETHODE, MusterQuiz.musterVon("List.of(1, 2, 3)"));
        assertEquals(Muster.ITERATOR, MusterQuiz.musterVon("for (Kunde k : kunden) { ... }"));
    }

    @Test
    void begruessungOhneOverkill() {
        assertEquals("Hallo Anna!", new Begruessung().begruesse("Anna"));
        assertEquals("Hallo Anna!", new Anwendung().willkommen("Anna"));
    }

    @Test
    void anwendungKenntKeineFabrikMehr() {
        boolean nutztFabrik = Arrays.stream(Anwendung.class.getDeclaredFields())
                .anyMatch(f -> f.getType().getSimpleName().contains("Factory") || f.getType().getSimpleName().contains("Strategie"));

        assertTrue(!nutztFabrik, "Anwendung soll die Begruessung direkt benutzen");
    }
}

package de.makno.lernen;

import static com.github.mvysny.kaributesting.v10.LocatorJ._click;
import static com.github.mvysny.kaributesting.v10.LocatorJ._get;
import static com.github.mvysny.kaributesting.v10.LocatorJ._setValue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.github.mvysny.kaributesting.v10.MockVaadin;
import com.github.mvysny.kaributesting.v10.Routes;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PersonFormularViewTest {

    private static final Routes ROUTEN = new Routes().autoDiscoverViews("de.makno.lernen");

    private PersonFormularView view;

    @BeforeEach
    void vaadinStarten() {
        MockVaadin.setup(ROUTEN);
        view = UI.getCurrent().navigate(PersonFormularView.class).orElseThrow();
    }

    @AfterEach
    void vaadinStoppen() {
        MockVaadin.tearDown();
    }

    private void ausfuellen(String name, String email, Integer alter) {
        _setValue(_get(TextField.class, spec -> spec.withLabel("Name")), name);
        _setValue(_get(EmailField.class, spec -> spec.withLabel("E-Mail")), email);
        _setValue(_get(IntegerField.class, spec -> spec.withLabel("Alter")), alter);
    }

    private void speichern() {
        _click(_get(Button.class, spec -> spec.withText("Speichern")));
    }

    @Test
    void gueltigeEingabenWerdenGespeichert() {
        ausfuellen("Anna", "anna@example.org", 30);

        speichern();

        Person person = view.gespeicherte().orElseThrow();
        assertEquals("Anna", person.getName());
        assertEquals("anna@example.org", person.getEmail());
        assertEquals(30, person.getAlter());
    }

    @Test
    void leererNameWirdAbgelehnt() {
        ausfuellen("", "anna@example.org", 30);

        speichern();

        assertTrue(view.gespeicherte().isEmpty());
        assertTrue(_get(TextField.class, spec -> spec.withLabel("Name")).isInvalid());
    }

    @Test
    void ungueltigeEmailWirdAbgelehnt() {
        ausfuellen("Anna", "keine-adresse", 30);

        speichern();

        assertTrue(view.gespeicherte().isEmpty());
        assertTrue(_get(EmailField.class, spec -> spec.withLabel("E-Mail")).isInvalid());
    }

    @Test
    void alterMussZwischenNullUndHundertfuenfzigLiegen() {
        ausfuellen("Anna", "anna@example.org", 200);

        speichern();

        assertTrue(view.gespeicherte().isEmpty());
        assertTrue(_get(IntegerField.class, spec -> spec.withLabel("Alter")).isInvalid());
        assertFalse(_get(TextField.class, spec -> spec.withLabel("Name")).isInvalid());
    }
}

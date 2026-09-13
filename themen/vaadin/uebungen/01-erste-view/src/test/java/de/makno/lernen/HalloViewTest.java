package de.makno.lernen;

import static com.github.mvysny.kaributesting.v10.LocatorJ._click;
import static com.github.mvysny.kaributesting.v10.LocatorJ._get;
import static com.github.mvysny.kaributesting.v10.LocatorJ._setValue;
import static com.github.mvysny.kaributesting.v10.NotificationsKt.expectNotifications;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.github.mvysny.kaributesting.v10.MockVaadin;
import com.github.mvysny.kaributesting.v10.Routes;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.textfield.TextField;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/** Karibu-Test: die View läuft im Speicher, ohne Browser und ohne Spring-Kontext. */
class HalloViewTest {

    private static final Routes ROUTEN = new Routes().autoDiscoverViews("de.makno.lernen");

    @BeforeEach
    void vaadinStarten() {
        MockVaadin.setup(ROUTEN);
        UI.getCurrent().navigate(HalloView.class);
    }

    @AfterEach
    void vaadinStoppen() {
        MockVaadin.tearDown();
    }

    @Test
    void begruesstMitNamen() {
        _setValue(_get(TextField.class, spec -> spec.withLabel("Dein Name")), "Anna");

        _click(_get(Button.class, spec -> spec.withText("Grüßen")));

        assertEquals("Hallo, Anna!", _get(Paragraph.class, spec -> spec.withId("ausgabe")).getText());
    }

    @Test
    void begruesstUnbekanntOhneNamen() {
        _click(_get(Button.class, spec -> spec.withText("Grüßen")));

        assertEquals("Hallo, Unbekannt!", _get(Paragraph.class, spec -> spec.withId("ausgabe")).getText());
    }

    @Test
    void zeigtNotificationNachDemKlick() {
        _setValue(_get(TextField.class, spec -> spec.withLabel("Dein Name")), "Ben");

        _click(_get(Button.class, spec -> spec.withText("Grüßen")));

        expectNotifications("Begrüßt: Ben");
    }
}

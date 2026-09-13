package de.makno.lernen;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

/** Startseite: Name eingeben, Knopf drücken, Begrüßung sehen. */
@Route("")
@PageTitle("Hallo Vaadin")
public class HalloView extends VerticalLayout {

    private static final String UNBEKANNT = "Unbekannt";

    private final TextField nameFeld = new TextField("Dein Name");
    private final Button gruessen = new Button("Grüßen");
    private final Paragraph ausgabe = new Paragraph();

    public HalloView() {
        nameFeld.setPlaceholder("z. B. Anna");
        ausgabe.setId("ausgabe");
        gruessen.addClickListener(ereignis -> begruessen());
        gruessen.addClickShortcut(Key.ENTER);
        add(new H1("Hallo Vaadin"), new HorizontalLayout(nameFeld, gruessen), ausgabe);
    }

    private void begruessen() {
        String name = nameFeld.getValue().strip();
        String anrede = name.isEmpty() ? UNBEKANNT : name;
        ausgabe.setText("Hallo, " + anrede + "!");
        Notification.show("Begrüßt: " + anrede);
    }
}

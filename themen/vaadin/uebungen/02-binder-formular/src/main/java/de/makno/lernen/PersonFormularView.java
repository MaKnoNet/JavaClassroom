package de.makno.lernen;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import java.util.Optional;

/**
 * Formular ohne Binder: Die Felder sind nicht an das Bean gebunden, es wird nichts
 * geprüft, und „Speichern" speichert alles – auch Unsinn. Aufgabe: Binder einsetzen.
 */
@Route("person")
public class PersonFormularView extends VerticalLayout {

    private final TextField name = new TextField("Name");
    private final EmailField email = new EmailField("E-Mail");
    private final IntegerField alter = new IntegerField("Alter");
    private final Button speichern = new Button("Speichern");

    private Person gespeicherte;

    public PersonFormularView() {
        FormLayout formular = new FormLayout(name, email, alter);
        speichern.addClickListener(ereignis -> {
            Person person = new Person();
            person.setName(name.getValue());
            person.setEmail(email.getValue());
            person.setAlter(alter.getValue());
            gespeicherte = person;
        });
        add(new H1("Person anlegen"), formular, speichern);
    }

    /** Für Tests: die zuletzt erfolgreich gespeicherte Person. */
    public Optional<Person> gespeicherte() {
        return Optional.ofNullable(gespeicherte);
    }
}

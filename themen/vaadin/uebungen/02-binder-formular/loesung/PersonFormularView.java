package de.makno.lernen;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.validator.EmailValidator;
import com.vaadin.flow.data.validator.IntegerRangeValidator;
import com.vaadin.flow.router.Route;
import java.util.Optional;

/** Formular mit Binder: Bindung und Validierung an einer Stelle, Speichern nur bei gültigen Daten. */
@Route("person")
public class PersonFormularView extends VerticalLayout {

    private static final int MIN_ALTER = 0;
    private static final int MAX_ALTER = 150;

    private final TextField name = new TextField("Name");
    private final EmailField email = new EmailField("E-Mail");
    private final IntegerField alter = new IntegerField("Alter");
    private final Button speichern = new Button("Speichern");
    private final Binder<Person> binder = new Binder<>(Person.class);

    private Person gespeicherte;

    public PersonFormularView() {
        binder.forField(name).asRequired("Name fehlt").bind(Person::getName, Person::setName);
        binder.forField(email)
                .withValidator(new EmailValidator("Keine gültige E-Mail-Adresse"))
                .bind(Person::getEmail, Person::setEmail);
        binder.forField(alter)
                .asRequired("Alter fehlt")
                .withValidator(new IntegerRangeValidator("Alter muss zwischen 0 und 150 liegen", MIN_ALTER, MAX_ALTER))
                .bind(Person::getAlter, Person::setAlter);

        speichern.addClickListener(ereignis -> speichern());
        add(new H1("Person anlegen"), new FormLayout(name, email, alter), speichern);
    }

    private void speichern() {
        Person person = new Person();
        try {
            binder.writeBean(person);
            gespeicherte = person;
            Notification.show("Gespeichert: " + person.getName());
        } catch (ValidationException e) {
            Notification.show("Bitte die markierten Felder korrigieren");
        }
    }

    /** Für Tests: die zuletzt erfolgreich gespeicherte Person. */
    public Optional<Person> gespeicherte() {
        return Optional.ofNullable(gespeicherte);
    }
}

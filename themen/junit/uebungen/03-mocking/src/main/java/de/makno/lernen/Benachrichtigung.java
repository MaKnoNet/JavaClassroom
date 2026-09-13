package de.makno.lernen;

/** Schickt Nachrichten an Kunden – im echten System E-Mail oder SMS. */
public interface Benachrichtigung {

    void sende(String empfaenger, String text);
}

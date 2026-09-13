package de.makno.lernen;

/** Ein Gästebuch-Eintrag – beides kommt aus einem Formular, also vom Nutzer. */
public record Kommentar(String autor, String text) {}

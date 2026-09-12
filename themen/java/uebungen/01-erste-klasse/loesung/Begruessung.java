package de.makno.lernen;

public class Begruessung {

    private static final String UNBEKANNT = "Unbekannt";

    public String begruesse(String name) {
        String anrede = (name == null || name.isEmpty()) ? UNBEKANNT : name;
        return "Hallo, " + anrede + "!";
    }

    public static void main(String[] args) {
        System.out.println(new Begruessung().begruesse("Welt"));
    }
}

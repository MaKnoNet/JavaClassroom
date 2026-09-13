package de.makno.lernen;

/** Ein einfaches Bean für das Formular. Fertig. */
public class Person {

    private String name = "";
    private String email = "";
    private Integer alter;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getAlter() {
        return alter;
    }

    public void setAlter(Integer alter) {
        this.alter = alter;
    }
}

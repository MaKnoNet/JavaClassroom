package de.makno.lernen;

/**
 * Ein Zähler, den mehrere Threads gleichzeitig erhöhen – etwa Seitenaufrufe auf einem
 * Server. So wie er ist, verliert er Erhöhungen. Aufgabe: thread-sicher machen.
 */
public class Zaehler {

    private int wert = 0;

    public void erhoehe() {
        wert++;
    }

    public int wert() {
        return wert;
    }
}

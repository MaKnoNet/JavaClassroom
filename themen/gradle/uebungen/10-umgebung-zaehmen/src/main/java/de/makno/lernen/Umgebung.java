package de.makno.lernen;

/** Sagt, womit der Build gerade läuft – die erste Frage bei jedem "bei mir geht's nicht". */
public final class Umgebung {

    private Umgebung() {}

    public static String javaVersion() {
        return System.getProperty("java.version");
    }

    public static int javaHauptversion() {
        return Runtime.version().feature();
    }

    public static String javaHome() {
        return System.getProperty("java.home");
    }
}

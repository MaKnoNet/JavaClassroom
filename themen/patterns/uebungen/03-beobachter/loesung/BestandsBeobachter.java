package de.makno.lernen;

@FunctionalInterface
public interface BestandsBeobachter {

    void bestandGeaendert(BestandsEreignis ereignis);
}

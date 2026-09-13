package de.makno.lernen;

import org.springframework.stereotype.Service;

/** Liefert Wetter für eine Stadt – und soll das auch tun, wenn der Dienst dahinter wackelt. */
@Service
public class WetterService {

    private final WetterAbruf abruf;

    public WetterService(WetterAbruf abruf) {
        this.abruf = abruf;
    }

    public Wetter fuer(String stadt) {
        return abruf.hole(stadt);
    }
}

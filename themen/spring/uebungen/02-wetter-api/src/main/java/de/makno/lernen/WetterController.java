package de.makno.lernen;

/** Soll unter GET /api/wetter/{stadt} antworten – tut es aber noch nicht. */
public class WetterController {

    private final WetterService service;

    public WetterController(WetterService service) {
        this.service = service;
    }

    /** 200 mit dem Wetter als JSON, 404 bei unbekannter Stadt. */
    public Object wetterFuer(String stadt) {
        throw new UnsupportedOperationException("noch nicht umgesetzt");
    }
}

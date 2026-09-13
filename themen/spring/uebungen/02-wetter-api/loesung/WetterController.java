package de.makno.lernen;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/** Antwortet unter GET /api/wetter/{stadt} mit JSON. */
@RestController
public class WetterController {

    private final WetterService service;

    public WetterController(WetterService service) {
        this.service = service;
    }

    /** 200 mit dem Wetter als JSON, 404 bei unbekannter Stadt. */
    @GetMapping("/api/wetter/{stadt}")
    public ResponseEntity<Wetter> wetterFuer(@PathVariable String stadt) {
        return service.fuer(stadt)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}

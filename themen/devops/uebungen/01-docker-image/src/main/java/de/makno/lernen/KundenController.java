package de.makno.lernen;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/kunden")
public class KundenController {

    public record NeuerKunde(String name) {}

    private final KundenRepository kunden;

    public KundenController(KundenRepository kunden) {
        this.kunden = kunden;
    }

    @GetMapping
    public List<Kunde> alle() {
        return kunden.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Kunde anlegen(@RequestBody NeuerKunde neu) {
        return kunden.save(new Kunde(neu.name()));
    }
}

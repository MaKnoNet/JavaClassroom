package de.makno.lernen;

import java.util.Map;
import java.util.TreeMap;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** Fachlogik über Bestellungen. */
@Service
public class BestellService {

    private final BestellungRepository bestellungen;

    public BestellService(BestellungRepository bestellungen) {
        this.bestellungen = bestellungen;
    }

    /** Summe aller bestellten Stück je Kunde, über alle Bestellungen. */
    @Transactional(readOnly = true)
    public Map<String, Integer> stueckJeKunde() {
        Map<String, Integer> summen = new TreeMap<>();
        // Die Positionen sind schon da – kein Nachladen in der Schleife.
        for (Bestellung bestellung : bestellungen.findAlleMitPositionen()) {
            int stueck = bestellung.positionen().stream().mapToInt(Position::menge).sum();
            summen.merge(bestellung.kunde(), stueck, Integer::sum);
        }
        return summen;
    }
}

package de.makno.lernen;

/**
 * Unveränderliche Bestellung, erzeugt über {@link Builder}. Pflicht: Kunde, Lieferadresse.
 * Alles andere hat einen Standardwert; build() prüft, was zusammenpassen muss.
 */
public final class Bestellung {

    private final String kunde;
    private final String lieferadresse;
    private final String rechnungsadresse;
    private final boolean express;
    private final int rabattProzent;
    private final String gutscheincode;

    private Bestellung(Builder b) {
        this.kunde = b.kunde;
        this.lieferadresse = b.lieferadresse;
        this.rechnungsadresse = b.rechnungsadresse != null ? b.rechnungsadresse : b.lieferadresse;
        this.express = b.express;
        this.rabattProzent = b.rabattProzent;
        this.gutscheincode = b.gutscheincode;
    }

    /** Statische Fabrikmethode als Einstieg – liest sich wie ein Satz: Bestellung.fuer("Anna")… */
    public static Builder fuer(String kunde) {
        return new Builder(kunde);
    }

    public String kunde() { return kunde; }
    public String lieferadresse() { return lieferadresse; }
    public String rechnungsadresse() { return rechnungsadresse; }
    public boolean express() { return express; }
    public int rabattProzent() { return rabattProzent; }
    public String gutscheincode() { return gutscheincode; }

    public static final class Builder {

        private final String kunde;
        private String lieferadresse;
        private String rechnungsadresse;
        private boolean express;
        private int rabattProzent;
        private String gutscheincode;

        private Builder(String kunde) {
            this.kunde = kunde;
        }

        public Builder lieferadresse(String adresse) {
            this.lieferadresse = adresse;
            return this;
        }

        public Builder rechnungsadresse(String adresse) {
            this.rechnungsadresse = adresse;
            return this;
        }

        public Builder express() {
            this.express = true;
            return this;
        }

        public Builder rabatt(int prozent) {
            if (prozent < 0 || prozent > 100) {
                throw new IllegalArgumentException("Rabatt muss zwischen 0 und 100 liegen: " + prozent);
            }
            this.rabattProzent = prozent;
            return this;
        }

        public Builder gutschein(String code) {
            this.gutscheincode = code;
            return this;
        }

        /** Hier wird geprüft, was nur im Zusammenhang prüfbar ist – nicht in jedem Setter einzeln. */
        public Bestellung build() {
            if (lieferadresse == null || lieferadresse.isBlank()) {
                throw new IllegalStateException("Bestellung für " + kunde + " braucht eine Lieferadresse");
            }
            return new Bestellung(this);
        }
    }
}

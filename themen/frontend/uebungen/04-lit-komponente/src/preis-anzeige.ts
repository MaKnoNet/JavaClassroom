import { LitElement, html } from 'lit';

export type Waehrung = 'EUR' | 'USD';

/**
 * <preis-anzeige betrag="19.99" waehrung="EUR"> – zeigt einen Preis und lässt die
 * Währung umschalten. Noch ein Rumpf: rendert nichts, meldet nichts, hat kein CSS.
 */
export class PreisAnzeige extends LitElement {
  static properties = {
    betrag: { type: Number },
    waehrung: { type: String },
  };

  declare betrag: number;
  declare waehrung: Waehrung;

  constructor() {
    super();
    this.betrag = 0;
    this.waehrung = 'EUR';
  }

  render() {
    return html``;
  }
}

customElements.define('preis-anzeige', PreisAnzeige);

import { LitElement, css, html } from 'lit';

export type Waehrung = 'EUR' | 'USD';

/**
 * <preis-anzeige betrag="19.99" waehrung="EUR"> – zeigt einen Preis und lässt die
 * Währung umschalten. Daten rein über Properties/Attribute, Änderungen raus als Event.
 */
export class PreisAnzeige extends LitElement {
  static properties = {
    betrag: { type: Number },
    waehrung: { type: String },
  };

  // CSS lebt im Shadow DOM: trifft nur diese Komponente, und die Seite trifft nicht hinein.
  static styles = css`
    :host { display: inline-block; font-family: system-ui, sans-serif; }
    .preis { font-weight: bold; font-size: 1.4rem; margin-right: 0.5rem; }
    button { cursor: pointer; }
  `;

  declare betrag: number;
  declare waehrung: Waehrung;

  constructor() {
    super();
    this.betrag = 0;
    this.waehrung = 'EUR';
  }

  render() {
    const formatiert = new Intl.NumberFormat('de-DE', { style: 'currency', currency: this.waehrung }).format(this.betrag);
    return html`
      <span class="preis">${formatiert}</span>
      <button @click=${this.wechsle}>in ${this.waehrung === 'EUR' ? 'USD' : 'EUR'} anzeigen</button>
    `;
  }

  private wechsle() {
    this.waehrung = this.waehrung === 'EUR' ? 'USD' : 'EUR';
    this.dispatchEvent(new CustomEvent('waehrung-gewechselt', {
      detail: { waehrung: this.waehrung },
      bubbles: true,
      composed: true, // darf das Shadow DOM verlassen – sonst hört die Seite nichts
    }));
  }
}

customElements.define('preis-anzeige', PreisAnzeige);

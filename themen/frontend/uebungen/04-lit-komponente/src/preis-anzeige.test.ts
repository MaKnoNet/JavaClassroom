import { beforeEach, describe, expect, it } from 'vitest';
import './preis-anzeige';
import type { PreisAnzeige } from './preis-anzeige';

/** Intl setzt ein geschütztes Leerzeichen (U+00A0) vor das Währungszeichen – hier normalisiert. */
function text(element: PreisAnzeige): string {
  return element.shadowRoot!.textContent!.replace(/\s+/g, ' ');
}

async function baue(betrag: number, waehrung?: string): Promise<PreisAnzeige> {
  const element = document.createElement('preis-anzeige') as PreisAnzeige;
  element.setAttribute('betrag', String(betrag));
  if (waehrung) element.setAttribute('waehrung', waehrung);
  document.body.append(element);
  await element.updateComplete;
  return element;
}

describe('<preis-anzeige>', () => {
  beforeEach(() => {
    document.body.innerHTML = '';
  });

  it('zeigt den Betrag in Euro, deutsch formatiert', async () => {
    const element = await baue(19.99);

    expect(text(element)).toContain('19,99 €');
  });

  it('nimmt die Währung als Attribut', async () => {
    const element = await baue(1250, 'USD');

    expect(text(element)).toContain('1.250,00 $');
  });

  it('wechselt per Knopf die Währung und meldet das nach außen', async () => {
    const element = await baue(10);
    let gemeldet: string | undefined;
    element.addEventListener('waehrung-gewechselt', (ereignis) => {
      gemeldet = (ereignis as CustomEvent<{ waehrung: string }>).detail.waehrung;
    });

    element.shadowRoot!.querySelector('button')!.click();
    await element.updateComplete;

    expect(gemeldet).toBe('USD');
    expect(element.waehrung).toBe('USD');
    expect(text(element)).toContain('10,00 $');
  });

  it('kapselt Aufbau und CSS im Shadow DOM', async () => {
    const element = await baue(5);

    expect(element.shadowRoot).not.toBeNull();
    expect(element.shadowRoot!.querySelector('.preis')).not.toBeNull();
    expect(document.body.querySelector('.preis')).toBeNull();
    expect(element.querySelector('button')).toBeNull();
  });
});

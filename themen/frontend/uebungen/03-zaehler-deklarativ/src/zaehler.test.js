import { describe, expect, it } from 'vitest';
import { erzeugeZaehler, render } from './zaehler';

function klick(wurzel, klasse) {
  wurzel.querySelector('.' + klasse).click();
}

describe('render', () => {
  it('erzeugt die Ansicht allein aus dem Zustand', () => {
    const html = render({ wert: 3 });

    expect(html).toContain('class="wert">3<');
    expect(html).toContain('ungerade');
  });

  it('ist eine reine Funktion – gleicher Zustand, gleiche Ausgabe', () => {
    expect(render({ wert: 4 })).toBe(render({ wert: 4 }));
    expect(render({ wert: 4 })).toContain('>gerade<');
  });
});

describe('Zähler', () => {
  it('zählt hoch und runter', () => {
    const wurzel = document.createElement('div');
    erzeugeZaehler(wurzel);

    klick(wurzel, 'plus');
    klick(wurzel, 'plus');
    klick(wurzel, 'minus');

    expect(wurzel.querySelector('.wert').textContent).toBe('1');
    expect(wurzel.querySelector('.paritaet').textContent).toBe('ungerade');
  });

  it('zeigt nach dem Zurücksetzen einen stimmigen Zustand', () => {
    const wurzel = document.createElement('div');
    erzeugeZaehler(wurzel);

    klick(wurzel, 'plus');
    klick(wurzel, 'plus');
    klick(wurzel, 'plus');
    klick(wurzel, 'reset');

    expect(wurzel.querySelector('.wert').textContent).toBe('0');
    expect(wurzel.querySelector('.paritaet').textContent).toBe('gerade');
  });
});

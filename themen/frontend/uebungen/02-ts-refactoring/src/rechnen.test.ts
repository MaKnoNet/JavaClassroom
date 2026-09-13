import { describe, expect, it } from 'vitest';
import { groesste, nurGerade, summe } from './rechnen';

describe('rechnen', () => {
  it('summiert Zahlen', () => {
    expect(summe([1, 2, 3])).toBe(6);
    expect(summe([])).toBe(0);
  });

  it('filtert gerade Zahlen', () => {
    expect(nurGerade([1, 2, 3, 4])).toEqual([2, 4]);
  });

  it('liefert undefined für die größte Zahl eines leeren Arrays', () => {
    expect(groesste([3, 9, 4])).toBe(9);
    expect(groesste([])).toBeUndefined();
  });
});

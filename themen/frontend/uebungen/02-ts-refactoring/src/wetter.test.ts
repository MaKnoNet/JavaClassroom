import { describe, expect, it } from 'vitest';
import { formatiere, waermste } from './wetter';

describe('wetter', () => {
  it('formatiert eine Messung', () => {
    expect(formatiere({ stadt: 'Berlin', temperatur: 21.5 })).toBe('Berlin: 21.5 °C');
  });

  it('findet die wärmste Messung', () => {
    expect(waermste([{ stadt: 'Berlin', temperatur: 21.5 }, { stadt: 'Hamburg', temperatur: 18 }])).toBe('21.5');
  });

  it('liefert undefined ohne Messungen statt abzustürzen', () => {
    expect(waermste([])).toBeUndefined();
  });
});

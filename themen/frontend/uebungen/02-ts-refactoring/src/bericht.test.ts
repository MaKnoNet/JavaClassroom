import { describe, expect, it } from 'vitest';
import { niederschlagAusFormular } from './bericht';

describe('bericht', () => {
  it('addiert Formularwerte als Zahlen', () => {
    expect(niederschlagAusFormular([{ value: '3' }, { value: '4' }])).toBe(7);
  });
});

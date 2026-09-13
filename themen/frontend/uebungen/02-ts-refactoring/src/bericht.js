import { summe } from './rechnen';

// Eingaben kommen aus Formularfeldern: { value: '3' } – value ist immer ein String.
export function niederschlagAusFormular(eingaben) {
  return summe(eingaben.map(e => e.value));
}

import { summe } from './rechnen';

/** Ein Formularfeld liefert immer Text – auch wenn eine Zahl drinsteht. */
export interface Eingabe {
  value: string;
}

export function niederschlagAusFormular(eingaben: Eingabe[]): number {
  return summe(eingaben.map(e => Number(e.value)));
}

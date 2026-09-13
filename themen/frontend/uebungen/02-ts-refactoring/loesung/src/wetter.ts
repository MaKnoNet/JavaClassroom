import { groesste } from './rechnen';

/** So sieht eine Messung aus – der Compiler hält jeden daran, der sie benutzt. */
export interface Messung {
  stadt: string;
  temperatur: number;
}

export function formatiere(messung: Messung): string {
  return `${messung.stadt}: ${messung.temperatur} °C`;
}

/** Die höchste Temperatur, auf eine Nachkommastelle – oder undefined ohne Messungen. */
export function waermste(messungen: Messung[]): string | undefined {
  const hoechste = groesste(messungen.map(m => m.temperatur));
  return hoechste === undefined ? undefined : hoechste.toFixed(1);
}

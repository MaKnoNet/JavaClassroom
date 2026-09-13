import { groesste } from './rechnen';

// Eine Messung sieht so aus: { stadt: 'Berlin', temperatur: 21.5 }

export function formatiere(messung) {
  return `${messung.stadt}: ${messung.temp} °C`;
}

/** Die höchste Temperatur, auf eine Nachkommastelle – oder undefined ohne Messungen. */
export function waermste(messungen) {
  return groesste(messungen.map(m => m.temperatur)).toFixed(1);
}

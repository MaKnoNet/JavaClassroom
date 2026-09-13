// Aus JavaScript-Übung 01 – funktioniert, solange man Zahlen hineingibt.
export function summe(zahlen) {
  return zahlen.reduce((s, z) => s + z, 0);
}

export function nurGerade(zahlen) {
  return zahlen.filter(z => z % 2 === 0);
}

export function groesste(zahlen) {
  return zahlen.length ? Math.max(...zahlen) : undefined;
}

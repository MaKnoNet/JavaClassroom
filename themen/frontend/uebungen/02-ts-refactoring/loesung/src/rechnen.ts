export function summe(zahlen: number[]): number {
  return zahlen.reduce((s, z) => s + z, 0);
}

export function nurGerade(zahlen: number[]): number[] {
  return zahlen.filter(z => z % 2 === 0);
}

export function groesste(zahlen: number[]): number | undefined {
  return zahlen.length ? Math.max(...zahlen) : undefined;
}

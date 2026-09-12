function summe(zahlen) {
  return zahlen.reduce((bisher, zahl) => bisher + zahl, 0);
}

function nurGerade(zahlen) {
  return zahlen.filter(zahl => zahl % 2 === 0);
}

function groesste(zahlen) {
  if (zahlen.length === 0) {
    return undefined;
  }
  return Math.max(...zahlen);
}

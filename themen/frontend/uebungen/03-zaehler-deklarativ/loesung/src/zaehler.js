// Deklarativ: Der Zustand ist die einzige Wahrheit, die Ansicht wird daraus berechnet.

/** Reine Funktion: Zustand hinein, HTML heraus. Kein DOM, keine Nebenwirkung. */
export function render(zustand) {
  const paritaet = zustand.wert % 2 === 0 ? 'gerade' : 'ungerade';
  return `
    <p>Wert: <strong class="wert">${zustand.wert}</strong> (<span class="paritaet">${paritaet}</span>)</p>
    <button class="plus">+</button>
    <button class="minus">−</button>
    <button class="reset">Zurücksetzen</button>`;
}

export function erzeugeZaehler(wurzel) {
  let zustand = { wert: 0 };

  function setzeZustand(neuerZustand) {
    zustand = neuerZustand;
    wurzel.innerHTML = render(zustand);
  }

  // Ein Listener an der Wurzel statt einer pro Knopf: Die Knöpfe werden bei jedem
  // render neu erzeugt, der Listener an der Wurzel bleibt (Event Delegation).
  wurzel.addEventListener('click', (ereignis) => {
    const ziel = ereignis.target;
    if (!(ziel instanceof HTMLButtonElement)) return;
    if (ziel.classList.contains('plus')) setzeZustand({ wert: zustand.wert + 1 });
    if (ziel.classList.contains('minus')) setzeZustand({ wert: zustand.wert - 1 });
    if (ziel.classList.contains('reset')) setzeZustand({ wert: 0 });
  });

  setzeZustand(zustand);
}

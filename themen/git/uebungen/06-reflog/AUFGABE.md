# Übung 06: Verlorene Commits zurückholen

## Vorbereitung (führt Claude aus)

```bash
mkdir -p arbeit/git/06-reflog/tagebuch && cd arbeit/git/06-reflog/tagebuch
git init -q -b main
for tag in 1 2 3 4 5 6; do
  printf "Tag %s: Eintrag\n" "$tag" >> tagebuch.txt
  git add tagebuch.txt && git commit -q -m "Tag $tag eingetragen"
done
```

Danach `git log --oneline` zeigen: sechs Commits.

## Aufgabe

Arbeite in `arbeit/git/06-reflog/tagebuch`.

1. Führe **absichtlich** `git reset --hard HEAD~3` aus. Schau dir `git log --oneline` und
   `tagebuch.txt` an: Drei Commits sind weg, die Datei endet bei Tag 3.
2. Hole die drei Commits zurück – ohne sie neu zu schreiben.

## Abnahmekriterien

- `git log --oneline` zeigt wieder sechs Commits, `tagebuch.txt` endet mit `Tag 6`.
- `git reflog` zeigt den Reset **und** die Rettung – die Geschichte des Fehlers bleibt
  sichtbar.
- Kein Commit wurde neu erzeugt: Der oberste Hash ist derselbe wie vor dem Reset.

## Hinweise

1. `git reflog` listet, wo `HEAD` in den letzten Tagen überall stand – jede Zeile ein
   Schritt, `HEAD@{0}` ist jetzt, `HEAD@{1}` davor. Der Eintrag *vor* deinem `reset` zeigt
   auf den Commit, den du zurückwillst.
2. `git reset --hard HEAD@{1}` (oder der Hash aus der Zeile) setzt den Branch dorthin.
   Ja, derselbe Befehl, der das Problem verursacht hat – nur mit dem richtigen Ziel.
3. Das gilt für alles, was einmal committet war: auch nach einem missglückten Rebase
   oder einem gelöschten Branch (`git branch <name> <hash>` legt ihn wieder an). Nur
   nie Committetes – Änderungen, die nur im Arbeitsverzeichnis lagen – ist wirklich weg.
   Der Reflog ist lokal und läuft nach 90 Tagen ab; er ersetzt kein Backup.

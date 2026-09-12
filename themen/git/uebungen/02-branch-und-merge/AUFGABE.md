# Übung 02: Branch anlegen und mergen

## Vorbereitung (führt Claude aus)

```bash
mkdir -p arbeit/git/02-branch-und-merge/rezept && cd arbeit/git/02-branch-und-merge/rezept
git init -q
printf "Pfannkuchen\n\nZutaten:\n- Mehl\n- Milch\n" > rezept.txt
git add rezept.txt && git commit -q -m "Rezept angelegt"
printf -- "- Eier\n" >> rezept.txt
git add rezept.txt && git commit -q -m "Eier ergänzt"
```

Danach `git log --oneline` zeigen: zwei Commits auf `main` (bei älterem Git `master` –
dann `git branch -m main`).

## Aufgabe

Arbeite in `arbeit/git/02-branch-und-merge/rezept`.

1. Lege einen Branch `zutaten-ergaenzen` an und wechsle hinein.
2. Ergänze in `rezept.txt` die Zeile `- Prise Salz` und committe.
3. Wechsle zurück nach `main` und merge den Branch hinein.
4. Lösche den Branch `zutaten-ergaenzen`.

## Abnahmekriterien

- `git log --oneline --graph` zeigt drei Commits, alle auf `main` erreichbar.
- `git branch` zeigt nur `main`.
- `rezept.txt` enthält `- Prise Salz`.
- `git status` ist sauber.

## Hinweise

1. `git switch -c <name>` legt einen Branch an und wechselt in einem Schritt.
2. Merge immer *aus* dem Ziel heraus: erst nach `main` wechseln, dann `git merge <branch>`.
   Weil `main` sich nicht weiterbewegt hat, macht Git einen Fast-Forward – es gibt keinen
   eigenen Merge-Commit. Das ist richtig so.

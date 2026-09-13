# Übung 05: Historie vor dem Pull Request aufräumen

## Vorbereitung (führt Claude aus)

1. Prüfen: `git config --global core.editor`. Ist nichts gesetzt, öffnet Git `vim` –
   den Lernenden einen Editor eintragen lassen, z. B. `git config --global core.editor
   "code --wait"` (VS Code), `"notepad"` (Windows) oder `"nano"` (Linux/macOS).
2. Dann:

```bash
mkdir -p arbeit/git/05-historie-aufraeumen/login && cd arbeit/git/05-historie-aufraeumen/login
git init -q -b main
printf "# Projekt\n" > README.md
git add README.md && git commit -q -m "README angelegt"
git switch -q -c feature-login
printf "Formular: Name, Passwort\n" > login.txt
git add login.txt && git commit -q -m "Login-Formular angelegt"
printf "Formular: Name, Passwort, Knopf\n" > login.txt
git commit -q -am "wip"
printf "Formular: Name, Passwort, Knopf 'Anmelden'\n" > login.txt
git commit -q -am "fix typo"
printf "mindestens 8 Zeichen\n" > passwort.txt
git add passwort.txt && git commit -q -m "Passwort-Prüfung ergänzt"
printf "mindestens 8 Zeichen, eine Ziffer\n" > passwort.txt
git commit -q -am "asdf"
git tag vorher
```

Danach `git log --oneline main..feature-login` zeigen: fünf Commits, drei davon mit
Nachrichten, die niemand im Review sehen will.

## Aufgabe

Arbeite in `arbeit/git/05-historie-aufraeumen/login`, Branch `feature-login`. Fasse die
fünf Commits mit einem interaktiven Rebase zu **zwei** zusammen:

1. `Login-Formular angelegt` – enthält die ersten drei Änderungen.
2. `Passwort-Prüfung ergänzt` – enthält die letzten zwei.

Der Inhalt der Dateien darf sich dabei **nicht** ändern – nur die Historie.

## Abnahmekriterien

- `git log --oneline main..feature-login` zeigt genau zwei Commits mit den beiden
  Nachrichten oben.
- `git diff vorher feature-login` gibt nichts aus – die Dateien sind identisch mit dem
  Zustand vor dem Aufräumen.
- `git status` ist sauber, kein Rebase läuft mehr.

## Hinweise

1. `git rebase -i main` öffnet deinen Editor mit einer Liste der fünf Commits, ältester
   oben. Jede Zeile beginnt mit `pick`. Ändere das Wort: `squash` (oder `s`) hängt den
   Commit an den darüber und lässt dich die Nachricht bearbeiten; `fixup` (oder `f`)
   hängt ihn an und **verwirft** seine Nachricht – genau richtig für „wip" und „asdf";
   `reword` behält den Commit, ändert nur die Nachricht; `drop` löscht ihn samt Änderung.
2. Speichern und schließen – Git arbeitet die Liste ab. Bei `squash` öffnet sich der
   Editor noch einmal für die zusammengefasste Nachricht.
3. Schiefgelaufen? `git rebase --abort` bringt alles zurück. Und danach kommt Lektion 12:
   Selbst nach einem abgeschlossenen Rebase ist der alte Stand über `git reflog` erreichbar.
4. Der aufgeräumte Branch hat neue Hashes. War er schon gepusht, weigert sich der Server –
   dann `git push --force-with-lease`, **nie** `--force`: `--force-with-lease` bricht ab,
   falls inzwischen jemand anderes auf den Branch gepusht hat.

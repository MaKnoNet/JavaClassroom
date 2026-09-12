# Übung 01: Dein erstes Repository

## Vorbereitung (führt Claude aus)

1. Prüfen: `git config --global user.name` und `git config --global user.email` sind gesetzt.
   Wenn nicht, den Lernenden beide setzen lassen (eigener Name, dienstliche E-Mail).
2. `mkdir -p arbeit/git/01-erstes-repo/spielwiese` – der Ordner ist noch **kein** Repository.

## Aufgabe

Arbeite im Ordner `arbeit/git/01-erstes-repo/spielwiese`.

1. Mache den Ordner zu einem Git-Repository.
2. Lege eine Datei `notizen.txt` mit einer Zeile Text an und committe sie mit der Nachricht
   `Notizen angelegt`.
3. Hänge eine zweite Zeile an `notizen.txt` an und committe die Änderung mit einer eigenen,
   sprechenden Nachricht.
4. Lege eine Datei `README.md` mit einer Überschrift an und committe sie.

## Abnahmekriterien

- `git log --oneline` zeigt genau drei Commits.
- `git status` meldet `nothing to commit, working tree clean`.
- Jede Commit-Nachricht sagt, *was* geändert wurde (nicht „update" oder „test").

## Hinweise

1. Der Ablauf ist immer derselbe: ändern → `git add <datei>` → `git commit -m "…"`.
   `git status` sagt dir jederzeit, in welchem Bereich eine Datei gerade ist.
2. Wenn `git commit` einen Editor öffnet und du nicht weiterweißt: Der Editor wollte eine
   Nachricht. Mit `-m "Nachricht"` umgehst du ihn.

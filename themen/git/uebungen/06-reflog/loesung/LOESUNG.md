# Lösung 06

```bash
git reset --hard HEAD~3       # der Fehler
git log --oneline             # drei Commits
git reflog                    # HEAD@{0}: reset: moving to HEAD~3 · HEAD@{1}: commit: Tag 6 eingetragen
git reset --hard HEAD@{1}     # zurück auf den Stand vor dem Reset
git log --oneline             # sechs Commits
```

Warum so: `reset --hard` löscht keine Commits, es bewegt nur den Branch-Zeiger; die
Commit-Objekte bleiben im Repository, bis die Müllsammlung sie irgendwann aufräumt. Der
Reflog ist das Protokoll aller Bewegungen von `HEAD` – deshalb findet man dort den alten
Stand und kann den Zeiger zurückschieben. Das Sicherheitsnetz hat eine Maschenweite: nur
Committetes, nur lokal, nur für begrenzte Zeit.

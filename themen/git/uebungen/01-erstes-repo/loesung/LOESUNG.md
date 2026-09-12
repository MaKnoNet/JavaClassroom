# Lösung 01

```bash
cd arbeit/git/01-erstes-repo/spielwiese
git init
echo "Erste Notiz" > notizen.txt
git add notizen.txt
git commit -m "Notizen angelegt"
echo "Zweite Notiz" >> notizen.txt
git add notizen.txt
git commit -m "Zweite Notiz ergänzt"
echo "# Spielwiese" > README.md
git add README.md
git commit -m "README mit Überschrift angelegt"
git log --oneline
```

Warum so: Jeder Commit enthält genau eine abgeschlossene Änderung mit einer Nachricht, die
das *Was* benennt. So bleibt die Historie lesbar, wenn man sie in einem Jahr wieder braucht.

---
thema: git
titel: Git
voraussetzungen: []
zielgruppe: [azubi, student, kollege]
---

# Git

Git ist das Gedächtnis eines Projekts: Jede Änderung wird festgehalten, jede Version ist
wiederherstellbar, und mehrere Menschen können am selben Code arbeiten, ohne sich gegenseitig
zu überschreiben. Ohne Git gibt es keine Teamarbeit in der Softwareentwicklung.

Am Ende kann der Lernende ein Repository anlegen, Änderungen nachvollziehbar committen, mit
Branches arbeiten, Konflikte lösen und mit einem Remote (GitHub, GitLab, Firmenserver)
zusammenarbeiten.

## Lektionen

### 01 Warum Versionierung, erstes Repository
- **Ziele:** Verstehen, was ein Commit ist; `git init`, `git add`, `git commit`, `git log`, `git status` anwenden; Name und E-Mail konfigurieren
- **Übung:** uebungen/01-erstes-repo
- **Prüffrage:** Was ist der Unterschied zwischen dem Arbeitsverzeichnis, der Staging Area und dem Repository?

Leitfaden: Mit der Frage beginnen, wie der Lernende bisher Versionen gesichert hat
(`projekt_final_v2_neu`). Die drei Bereiche als Schreibtisch, Paketannahme und Archiv erklären.

### 02 Branches und Merge
- **Ziele:** Einen Branch anlegen, darauf committen, in `main` mergen; `git branch`, `git switch`, `git merge` anwenden; Fast-Forward von Merge-Commit unterscheiden
- **Übung:** uebungen/02-branch-und-merge
- **Prüffrage:** Warum arbeitet man an einem Feature in einem eigenen Branch und nicht direkt in `main`?

### 03 Änderungen ansehen und zurücknehmen
- **Ziele:** `git diff`, `git restore`, `git reset --soft`, `git revert` unterscheiden und sicher einsetzen
- **Prüffrage:** Wann nimmt man `revert` statt `reset`?

Leitfaden: Faustregel – alles, was schon geteilt ist, wird mit `revert` zurückgenommen.

### 04 Remote: clone, push, pull
- **Ziele:** Ein Repository klonen, Änderungen hochladen und holen; `origin`, `git fetch` vs. `git pull`
- **Prüffrage:** Was passiert bei `git pull` genau – aus welchen zwei Befehlen besteht es?

### 05 Konflikte lösen
- **Ziele:** Einen Merge-Konflikt erkennen, die Markierungen lesen, auflösen und den Merge abschließen
- **Prüffrage:** Woran erkennt Git, dass es einen Konflikt gibt, und woran erkennt man ihn in der Datei?

### 06 .gitignore und gute Commits
- **Ziele:** Build-Reste ausschließen; kleine, thematisch geschlossene Commits mit sprechender Nachricht schreiben
- **Prüffrage:** Warum gehört `build/` nicht ins Repository, `build.gradle` aber schon?

### 07 Rebase und Historie
- **Ziele:** `git rebase` verstehen, `git log --graph` lesen, den Unterschied zu Merge erklären; Regel „nie geteilte Historie umschreiben"
- **Prüffrage:** Was ändert Rebase an den Commits, was Merge nicht ändert?

### 08 Alltag im Team
- **Ziele:** Ablauf Branch → Push → Pull Request → Review → Merge; Branch-Namen, Commit-Konventionen (`feat:`, `fix:`)
- **Prüffrage:** Was prüft ein Reviewer, was die Tests nicht prüfen können?

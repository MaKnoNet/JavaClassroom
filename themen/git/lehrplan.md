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
- **Übersetzung:** en: Why version control, first repository | fr: Pourquoi versionner, premier dépôt

Leitfaden: Mit der Frage beginnen, wie der Lernende bisher Versionen gesichert hat
(`projekt_final_v2_neu`). Die drei Bereiche als Schreibtisch, Paketannahme und Archiv erklären.

### 02 Branches und Merge
- **Ziele:** Einen Branch anlegen, darauf committen, in `main` mergen; `git branch`, `git switch`, `git merge` anwenden; Fast-Forward von Merge-Commit unterscheiden
- **Übung:** uebungen/02-branch-und-merge
- **Prüffrage:** Warum arbeitet man an einem Feature in einem eigenen Branch und nicht direkt in `main`?
- **Übersetzung:** en: Branches and merge | fr: Branches et fusion

### 03 Änderungen ansehen und zurücknehmen
- **Ziele:** `git diff`, `git restore`, `git reset --soft`, `git revert` unterscheiden und sicher einsetzen
- **Prüffrage:** Wann nimmt man `revert` statt `reset`?
- **Übersetzung:** en: Inspecting and undoing changes | fr: Voir et annuler des modifications

Leitfaden: Faustregel – alles, was schon geteilt ist, wird mit `revert` zurückgenommen.

### 04 Remote: clone, push, pull
- **Ziele:** Ein Repository klonen, Änderungen hochladen und holen; `origin`, `git fetch` vs. `git pull`
- **Prüffrage:** Was passiert bei `git pull` genau – aus welchen zwei Befehlen besteht es?
- **Übersetzung:** en: Remote: clone, push, pull | fr: Dépôt distant : clone, push, pull

### 05 Konflikte lösen
- **Ziele:** Einen Merge-Konflikt erkennen, die Markierungen lesen, auflösen und den Merge abschließen
- **Prüffrage:** Woran erkennt Git, dass es einen Konflikt gibt, und woran erkennt man ihn in der Datei?
- **Übersetzung:** en: Resolving conflicts | fr: Résoudre les conflits

### 06 .gitignore und gute Commits
- **Ziele:** Build-Reste ausschließen; kleine, thematisch geschlossene Commits mit sprechender Nachricht schreiben
- **Prüffrage:** Warum gehört `build/` nicht ins Repository, `build.gradle` aber schon?
- **Übersetzung:** en: .gitignore and good commits | fr: .gitignore et bons commits

### 07 Rebase und Historie
- **Ziele:** `git rebase` verstehen, `git log --graph` lesen, den Unterschied zu Merge erklären; Regel „nie geteilte Historie umschreiben"
- **Prüffrage:** Was ändert Rebase an den Commits, was Merge nicht ändert?
- **Übersetzung:** en: Rebase and history | fr: Rebase et historique

### 08 Alltag im Team
- **Ziele:** Ablauf Branch → Push → Pull Request → Review → Merge; Branch-Namen, Commit-Konventionen (`feat:`, `fix:`)
- **Prüffrage:** Was prüft ein Reviewer, was die Tests nicht prüfen können?
- **Übersetzung:** en: Everyday teamwork | fr: Le quotidien en équipe

### 09 Worktrees: mehrere Branches gleichzeitig
- **Ziele:** `git worktree add`, `list`, `remove`; einen zweiten Branch in einem eigenen Ordner auschecken, ohne die laufende Arbeit zu stashen; wann Worktree, wann Stash, wann Clone
- **Prüffrage:** Du arbeitest an einem Feature und sollst schnell einen Hotfix auf `main` machen – warum ist ein Worktree hier besser als `git stash`?
- **Übersetzung:** en: Worktrees: several branches at once | fr: Worktrees : plusieurs branches en même temps

Leitfaden: Ein Repository, mehrere Arbeitsverzeichnisse – jedes mit eigenem Branch, alle
teilen dieselben Objekte und dieselbe Historie. `git worktree add ../hotfix main` legt
neben dem Projekt einen Ordner an, in dem `main` ausgecheckt ist; die IDE kann beide
gleichzeitig offen haben, Builds laufen getrennt. Typische Fehler zeigen: derselbe Branch
kann nicht in zwei Worktrees ausgecheckt sein; `remove` räumt den Ordner, `prune` die
Reste weg. Abgrenzung: Stash für „kurz weglegen", Worktree für „parallel arbeiten", Clone
nur, wenn es wirklich ein zweites Repository sein soll.

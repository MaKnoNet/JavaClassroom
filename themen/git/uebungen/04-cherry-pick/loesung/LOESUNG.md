# Lösung 04

```bash
git log --oneline alte-funktion          # Hash des Commits "Bugfix: …" ablesen
git cherry-pick <hash>                   # z. B. git cherry-pick $(git log --format=%h --grep=Bugfix alte-funktion)
git log --oneline --all --graph
```

Warum so: Ein Merge hätte alle drei Commits geholt – auch das halbfertige Experiment.
Cherry-pick nimmt nur die Änderung *eines* Commits und legt sie als neuen Commit auf den
aktuellen Branch; der Hash ist deshalb ein anderer, obwohl Nachricht und Diff gleich sind.
Der Feature-Branch bleibt unangetastet – wird er später gemergt, erkennt Git die schon
vorhandene Änderung meist ohne Konflikt.

---
name: thema-anlegen
description: Legt ein neues Unterrichtsthema an (Ordner, Lehrplan-Entwurf, nach Freigabe Übungen). Aufruf `/thema-anlegen <name>`, z. B. `/thema-anlegen spring`. Für Ausbilder.
---

# /thema-anlegen <name>

Du hilfst einem Ausbilder, ein Thema anzulegen. Formate und Konventionen: `AGENTS.md`.
`<name>` ist der Ordnername: Kleinbuchstaben, keine Leerzeichen, keine Umlaute.

## 1. Klären (eine Frage pro Nachricht)

1. Titel des Themas?
2. Zielgruppe als **Niveau**: anfaenger, fortgeschritten, erfahren – mehrere möglich?
   (Nicht die Rolle – Azubi, Student, Kollege sagt nichts über das Können. Sie muss zu den
   `Stufe:`-Werten der Lektionen passen.)
3. Voraussetzungen: welche vorhandenen Themen (`ls themen`) sollten vorher fertig sein?
4. Was soll am Ende sitzen? Zwei bis drei Sätze.
5. Quellmaterial: Für Allgemeinthemen keines. Für Firmenthemen: Pfad zu Code oder Doku
   (anderes Repo, relativer Pfad oder `FRAMEWORK_HOME`). Das Material lesen, bevor der
   Lehrplan entsteht – Lektionen werden daraus abgeleitet, nicht geraten. Firmencode wird
   **nicht** in dieses Repo kopiert; Übungen binden ihn als Abhängigkeit oder per Pfad ein.

## 2. Ordner und Lehrplan-Entwurf

1. Existiert `themen/<name>/` schon → abbrechen und sagen, dass das Thema existiert.
2. `themen/_schablone/lehrplan.md` nach `themen/<name>/lehrplan.md` kopieren, Frontmatter
   füllen, `mkdir themen/<name>/uebungen`.
3. Lehrplan-Entwurf mit 6–12 Lektionen schreiben, aufsteigend von Grundlagen zu Anwendung.
   Jede Lektion: Titel, `Ziele`, `Prüffrage`; `Übung` nur dort, wo eine Übung geplant ist,
   mit Pfad `uebungen/NN-name`. Unter jeder Lektion ein bis drei Sätze Leitfaden.
   Im Frontmatter `reihenfolge:` setzen (Position auf der Fortschrittsseite, siehe
   AGENTS.md – zwischen den Nachbarn im Lernpfad, z. B. 95 zwischen Datenbanken und Spring).
4. Format prüfen: `java tools/Fortschritt.java _beispiel` muss durchlaufen und das Thema
   in `arbeit/fortschritt.html` erscheinen.
5. Entwurf im Chat zeigen (Lektionsliste). **Warten auf Freigabe.** Änderungswünsche
   einarbeiten, erneut zeigen.

## 3. Übungen (erst nach Freigabe)

Für jede Lektion mit `Übung:`:

1. Gerüst kopieren: Java/Gradle/JUnit → `themen/_schablone/uebung-java/`;
   HTML/CSS/JS → `themen/_schablone/uebung-web/`; reines SQL → `themen/_schablone/uebung-sql/`
   (`start.sql`, `aufgabe.sql`, `pruefung.sql`, Prüfung in SQL selbst); Git → nur `AUFGABE.md`.
   Ziel: `themen/<name>/uebungen/NN-name/`. In `settings.gradle` `rootProject.name`
   auf `NN-name` setzen.
2. `AUFGABE.md` schreiben: Aufgabe, Abnahmekriterien (prüfbar!), Hinweise gestaffelt
   (Hinweis 1 allgemein, Hinweis 2 konkret). Bei Git-Übungen zusätzlich Abschnitt
   „Vorbereitung (führt Claude aus)" mit den Befehlen, die das Übungs-Repo aufbauen.
3. Startcode und Tests schreiben. Tests müssen mit dem Startcode **rot** sein.
4. `loesung/` anlegen: die Referenzlösung plus `LOESUNG.md` mit einem Satz, warum sie so
   aussieht.
5. Prüfen: Referenzlösung über den Startcode kopieren (in einen Temp-Ordner), Tests
   laufen lassen – müssen **grün** sein. Web-Übungen: `loesung/index.html` in der
   Browser-Ansicht öffnen und Abnahmekriterien nachsehen. Temp-Ordner löschen.
6. Eine Übung, deren Lösung nicht grün ist, wird nicht committet.

## 4. Abschluss

1. `java tools/Fortschritt.java _beispiel` erneut ausführen.
2. Commit-Vorschlag: `git add themen/<name>` und
   `git commit -m "feat(themen): <Titel> – Lehrplan und N Übungen"`. Commit nur nach
   Bestätigung des Ausbilders.

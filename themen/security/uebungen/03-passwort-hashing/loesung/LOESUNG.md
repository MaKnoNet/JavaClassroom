# Lösung 03: Passwort-Hashing

`BenutzerVerwaltung.java`: ein `BCryptPasswordEncoder`, `encode` beim Registrieren,
`matches` beim Anmelden. Die Map enthält danach Werte wie
`$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy`.

**Aufbau des Werts:** `$2a$` Algorithmus, `10` Kostenfaktor (2¹⁰ Runden), 22 Zeichen
Salt, 31 Zeichen Hash. `matches` zerlegt das, hasht die Eingabe mit demselben Salt und
Kostenfaktor und vergleicht – deshalb braucht man den Salt nicht separat zu speichern.

**Was das schützt und was nicht:** Es schützt die Passwörter der Nutzer, wenn die
Tabelle gestohlen wird – und damit auch ihre Konten bei anderen Diensten, weil Menschen
Passwörter wiederverwenden. Es schützt *nicht* vor Ausprobieren am Login-Formular; dafür
braucht es Ratenbegrenzung und Sperren (Spring-Lektion 07 kennt den Rate Limiter).

**Kostenfaktor im Betrieb:** 10 ist Standard und dauert rund 100 ms auf heutiger
Hardware. Alle paar Jahre erhöhen; alte Hashes werden beim nächsten erfolgreichen Login
neu gehasht (`upgradeEncoding`).

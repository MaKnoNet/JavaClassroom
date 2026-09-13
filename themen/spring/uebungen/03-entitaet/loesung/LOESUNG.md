Zwei Dateien ersetzen ihre Gegenstücke in `src/main/java/de/makno/lernen/`.

Warum so: `@Entity` macht aus der Klasse eine Tabelle, `@Id` + `@GeneratedValue(IDENTITY)`
lässt die Datenbank die Nummer vergeben – deshalb ist `id` vor dem Speichern `null` und
danach gesetzt, ohne dass unser Code sie anfasst. Der `protected`-Konstruktor ohne
Parameter ist der Preis des ORM: Hibernate erzeugt das Objekt erst leer und schreibt die
Spaltenwerte per Reflection hinein; `protected` statt `public`, damit ihn niemand sonst
benutzt. Genau deshalb kann ein Record keine Entity sein – er hat weder einen leeren
Konstruktor noch veränderliche Felder. `@Column(nullable = false)` macht aus der
Java-Absicht „Name ist Pflicht" eine Datenbankregel, die auch dann gilt, wenn jemand an
Java vorbei in die Tabelle schreibt. `precision`/`scale` beim Preis, weil Geld in
Datenbanken `DECIMAL` ist, nie `DOUBLE`. `@ManyToOne` legt die Fremdschlüsselspalte auf
die Produkt-Seite – dort, wo in der Tabelle auch die Zahl steht.

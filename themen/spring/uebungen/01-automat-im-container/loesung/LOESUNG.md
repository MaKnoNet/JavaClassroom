Zwei Dateien ersetzen ihre Gegenstücke in `src/main/java/de/makno/lernen/`.

Warum so: `@Service` macht beide Klassen zu Beans – Spring findet sie beim Start über das
Komponenten-Scanning ab dem Paket von `AutomatAnwendung`. Der Automat hat genau einen
Konstruktor mit einem `BezahlService`-Parameter; Spring sucht die Bean, die dieses
Interface implementiert, und reicht sie herein – ohne `@Autowired`, das ist bei einem
einzigen Konstruktor überflüssig. Im Test ersetzt `@MockitoBean` genau diese Bean durch
einen Mock; der Automat merkt davon nichts, weil er nur das Interface kennt. Das Feld
bleibt `final`: Eine Abhängigkeit, die nach dem Bau nie wechselt, soll das auch im Code
sagen – deshalb Konstruktor- statt Feld-Injection.

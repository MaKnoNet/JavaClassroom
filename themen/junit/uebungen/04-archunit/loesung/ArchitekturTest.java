package de.makno.lernen;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.jupiter.api.Test;

class ArchitekturTest {

    private static final String BASIS = "de.makno.lernen";

    @Test
    void fachlogikKenntDieOberflaecheNicht() {
        JavaClasses klassen = new ClassFileImporter().importPackages(BASIS);

        ArchRule regel = noClasses().that().resideInAPackage("..service..")
                .should().dependOnClassesThat().resideInAPackage("..ui..")
                .because("die Oberflaeche haengt von der Fachlogik ab, nie umgekehrt");

        regel.check(klassen);
    }
}

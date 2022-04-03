package app.uml;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Třída (její instance) reprezentuje model třídy z jazyka UML.
 * Rozšiřuje třídu UMLClassifier. Obsahuje seznam atributů
 * a operací (metod). Třída může být abstraktní.
 *
 * @author Lucie Svobodová
 */
public class UMLClass extends UMLClassifier {
    private List<UMLAttribute> attributes;
    private List<UMLMethod> methods;
    private boolean isAbstract;

    // Test, zda objekt reprezentuje model abstraktní třídy.
    public boolean isAbstract() {
        return this.isAbstract;
    }

    // Změní informaci objektu, zda reprezentuje abstraktní třídu.
    public void setAbstract(boolean isAbstract) {
        this.isAbstract = !this.isAbstract;
    }

    // Vloží atribut do modelu UML třídy. Atribut se vloží
    // na konec seznamu (poslední položka). Pokud již třída
    // obsahuje atribut stejného jména, nedělá nic.
    public boolean addAttribute(UMLAttribute attr) {
        if (this.attributes.contains(attr))
            return true;
        try {
            this.attributes.add(attr);
        } catch (UnsupportedOperationException uoe) {
            return false;
        }
        return true;
    }

    // Blah nova metoda TODO
    public boolean addMethod(UMLMethod attr) {
        if (this.methods.contains(attr))
            return true;
        try {
            this.methods.add(attr);
        } catch (UnsupportedOperationException uoe) {
            return false;
        }
        return true;
    }

    // Vrací pozici atributu v seznamu atributů. Pozice
    // se indexuje od hodnoty 0. Pokud třída daný atribut
    // neobsahuje, vrací -1.
    public int getAttrPosition(UMLAttribute attr) {
        if (!attributes.contains(attr))
            return -1;
        return attributes.indexOf(attr);
    }

    // Přesune pozici atributu na nově zadanou. Pozice se indexuje
    // od hodnoty 0. Pokud třída daný atribut neobsahuje, nic
    // neprovádí a vrací -1. Při přesunu na pozici pos se všechny
    // stávající položky (atributy) od pozice pos (včetně) posunou
    // o jednu pozici doprava.
    public int moveAttrAtPosition(UMLAttribute attr, int pos) {
        if (!this.attributes.contains(attr))
            return -1;

        int indexFrom = this.attributes.indexOf(attr);
        if (indexFrom <= pos)
            Collections.rotate(this.attributes.subList(indexFrom, pos + 1),-1);
        else
            Collections.rotate(this.attributes.subList(pos, indexFrom + 1),1);
        return 0;
    }

    // Vrací nemodifikovatelný seznam atributů. Lze využít
    // pro zobrazení atributů třídy.
    public List<UMLAttribute> getAttributes() {
        return Collections.unmodifiableList(this.attributes);
    }

    // Konstruktor - vytvoří instanci reprezentující model
    // třídy z jazyka UML. Třída není abstraktní.
    public UMLClass(String name) {
        super(name);
        this.isAbstract = false;
        this.attributes = new ArrayList<>();
        this.methods = new ArrayList<>();
    }
}


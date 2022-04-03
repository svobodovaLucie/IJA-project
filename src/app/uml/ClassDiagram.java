package app.uml;

import java.util.List;
import java.util.ArrayList;

/**
 * Třída reprezentuje diagram tříd. Je odvozen od třídy Element (má název).
 * Obsahuje seznam tříd (instance třídy UMLClass) příp. klasifikátorů
 * pro uživatelsky nedefinované typy (instance třídy UMLClassifier).
 *
 * @author Lucie Svobodová
 */
public class ClassDiagram extends Element {
    private List<UMLClass> classes;
    private List<UMLClassifier> classifiers;

    // Vytvoří instanci UML třídy a vloží ji do diagramu.
    // Pokud v diagramu již existuje třída stejného názvu, vrací null.
    public UMLClass createClass(String name) {
        for (UMLClass cl : this.classes) {
            if (cl.getName().equals(name)) {
                return null;
            }
        }
        UMLClass newClass = new UMLClass(name);
        this.classes.add(newClass);
        this.classifiers.add(newClass);
        return newClass;
    }

    // Vyhledá v diagramu klasifikátor podle názvu. Pokud neexistuje,
    // vytvoří instanci třídy Classifier reprezentující klasifikátor,
    // který není v diagramu zachycen (viz UMLClassifier.forName(String));
    // využito např. pro modelování typu proměnné, který v diagramu není.
    // Tato instance je zařazena do struktur diagramu, tzn. že při dalším
    // pokusu o vyhledání se použije tato již vytvořená instance.
    public UMLClassifier classifierForName(String name) {
        for (UMLClassifier classifier : this.classifiers) {
            if (classifier.getName().equals(name))
                return classifier;
        }
        UMLClassifier newClassifier = new UMLClassifier(name, false);
        this.classifiers.add(newClassifier);
        return newClassifier;
    }

    // Vyhledá v diagramu klasifikátor podle názvu.
    // Pokud v diagramu neexistuje klasifikátor daného jména, vrací null.
    public UMLClassifier findClassifier(String name) {
        for (UMLClassifier classifier : this.classifiers) {
            if (classifier.getName().equals(name))
                return classifier;
        }
        return null;
    }

    // Konstruktor pro vytvoření instance diagramu. Každý diagram má svůj název.
    public ClassDiagram(String name) {
        super(name);
        this.classes = new ArrayList<>();
        this.classifiers = new ArrayList<>();
    }
}

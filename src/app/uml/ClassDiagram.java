/*
 * File:         ClassDiagram.java
 * Institution:  FIT BUT 2021/2022
 * Course:       IJA - Java Programming Language
 * Authors:      Lucie Svobodová, xsvobo1x@stud.fit.vutbr.cz
 *               Jakub Kuzník, xkuzni04@stud.fit.vutbr.cz
 *
 * File contains implementation of the UML class diagram.
 */
package app.uml;

import java.util.List;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Class represents the UML class diagram. It is inherited from Element
 * class, it has its name, list of UML classes and UML classifiers
 * (e.g. interfaces, classes and types that are user defined).
 */
public class ClassDiagram extends Element {
    private List<UMLClass> classes;
    private List<UMLClassifier> classifiers;
    private List<UMLRelation> relations;
    private List<UMLInterface> interfaces;

    /**
     * ClassDiagram constructor.
     *
     * @param name name of the class diagram
     */
    public ClassDiagram(String name) {
        super(name);
        this.classes = new ArrayList<>();
        this.classifiers = new ArrayList<>();
        this.relations = new ArrayList<>();
    }

    // TODO osetrit, aby se nemohla stejna trida pridat vickrat

    /**
     * Method adds new class to the class diagram.
     *
     * @param umlClass class to be added
     */
    public void addClass(UMLClass umlClass) {
        this.classes.add(umlClass);
    }

    // TODO
    public List <UMLClass> getClasses () {
        return this.classes;
    }
    /**
     * Method creates an instance of UMLClass and inserts it to the
     * list of classes in the UML class diagram. If the class already
     * exists, the method returns null.
     *
     * @param name name of the UML class to be added
     * @return new UMLClass if successful,
     *         null if the class already exists
     */
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

    /**
     * Method finds the UML classifier by its name. If it doesn't exist
     * in the diagram, it creates an instance of UMLClassifier that is not
     * user defined. The classifier is added to the classifiers list.
     *
     * @param name name of the UML classifier
     * @return new UMLClassifier if added to the diagram,
     *         reference to the UMLClassifier if found in the list
     */
    public UMLClassifier classifierForName(String name) {
        for (UMLClassifier classifier : this.classifiers) {
            if (classifier.getName().equals(name))
                return classifier;
        }
        UMLClassifier newClassifier = new UMLClassifier(name);
        this.classifiers.add(newClassifier);
        return newClassifier;
    }

    /**
     * Method find the UML classifier in the diagram. If not present,
     * returns null.
     *
     * @param name name of the UML classifier to be found
     * @return UMLClassifier if found, null if not
     */
    public UMLClassifier findClassifier(String name) {
        for (UMLClassifier classifier : this.classifiers) {
            if (classifier.getName().equals(name))
                return classifier;
        }
        return null;
    }

    public UMLClass findClass(String name) {
        // find class
        for (UMLClass cls : this.getClasses()) {
            if (Objects.equals(cls.getName(), name)) {
                return cls;
            }
        }

        // not found -> null
        return null;
    }

    public void addRelation(UMLRelation umlRelation) {
        if (umlRelation == null) {
            System.out.println("Null UMLRelation");
        } else {
            System.out.println("NotNull UMLRelation");
        }
        this.relations.add(umlRelation);
    }

    public List<UMLRelation> getRelations() {
        return this.relations;
    }

    public void addInterface(UMLInterface umlInterface) {
        this.interfaces.add(umlInterface);
    }
}

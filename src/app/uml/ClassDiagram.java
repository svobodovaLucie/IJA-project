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

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
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
    private List<UMLRelation> relations;
    private List<UMLClass> interfaces;

    private PropertyChangeSupport support;

    /**
     * ClassDiagram constructor.
     *
     * @param name name of the class diagram
     */
    public ClassDiagram(String name) {
        super(name);
        this.classes = new ArrayList<>();
        this.relations = new ArrayList<>();
        this.interfaces = new ArrayList<>();
        this.support = new PropertyChangeSupport(this);
    }

    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl);
    }

    public void removePropertyChangeListener(PropertyChangeListener pcl) {
        support.removePropertyChangeListener(pcl);
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
        for (UMLClass cl : this.interfaces) {
            if (cl.getName().equals(name)) {
                return null;
            }
        }
        UMLClass newClass = new UMLClass(name, false, this);
        this.classes.add(newClass);
        return newClass;
    }

    public UMLClass createInterface(String name) {
        for (UMLClass cl : this.interfaces) {
            if (cl.getName().equals(name)) {
                return null;
            }
        }
        for (UMLClass cl : this.classes) {
            if (cl.getName().equals(name)) {
                return null;
            }
        }
        UMLClass newClass = new UMLClass(name, true, this);
        this.interfaces.add(newClass);
        return newClass;
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

    public UMLClass findInterface(String name) {
        // find class
        for (UMLClass cls : this.getInterfaces()) {
            if (Objects.equals(cls.getName(), name)) {
                return cls;
            }
        }
        // not found -> null
        return null;
    }

    public List<UMLClass> getInterfaces() {
        return this.interfaces;
    }

    public void removeClass(String name) {
        UMLClass toRemove = findClass(name);
        try {
            this.classes.remove(toRemove);
        } catch (Exception ignored) {
        }
        // observer
        support.firePropertyChange("removeClass", toRemove, null);
    }

    public void removeInterface(String name) {
        UMLClass toRemove = findInterface(name);
        try {
            this.interfaces.remove(toRemove);
        } catch (Exception ignored) {
        }
        // observer
        support.firePropertyChange("removeInterface", toRemove, null);
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

    public void addInterface(UMLClass umlInterface) {
        this.interfaces.add(umlInterface);
    }
}
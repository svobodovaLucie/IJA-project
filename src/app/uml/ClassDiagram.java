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

    /**
     * Create interface.
     * @param name name of interface
     * @return new interface
     */
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

    /**
     * Finds class in diagram by name.
     * @param name name of class diagram that we are looking for.
     * @return class diagram or null
     */
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

    /**
     * Finds interface in diagram by name.
     * @param name name of interface that we are looking for.
     * @return interface represented by UMLClass or null
     */
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

    /**
     * Try to find class. if null then try to find interface.
     * @param name (class, interface) we are looking for
     * @return class or interface or null
     */
    public UMLClass findClassInterface(String name) {
        UMLClass result = findClass(name);
        if (result == null) {
            result = findInterface(name);
        }
        return result;
    }

    public void removeRelation(String classFrom, String classTo, String type) {
        UMLRelation toRemove = findRelation(classFrom, classTo, type);
        // observer
        support.firePropertyChange("removeRelationship", toRemove, null);
        try {
            this.getRelations().remove(toRemove);
        } catch (Exception ignored) {
            System.out.println("EXC removeRelation");
        }
    }

    private UMLRelation findRelation(String classFrom, String classTo, String type) {
        for (UMLRelation rel : this.getRelations()) {
            if (Objects.equals(rel.getClassFrom().getName(), classFrom) &&
                    Objects.equals(rel.getClassTo().getName(), classTo) &&
                    Objects.equals(rel.getRelationType(), type)) {
                return rel;
            }
        }
        return null;
    }

    public List<UMLClass> getInterfaces() {
        return this.interfaces;
    }

    /**
     * Remove given class from diagram.
     * @param name class name
     */
    public void removeClass(String name) {
        UMLClass toRemove = findClass(name);
        try {
            this.classes.remove(toRemove);
        } catch (Exception ignored) {
        }
        // observer
        support.firePropertyChange("removeClass", toRemove, null);
    }

    /**
     * Remove given interface from diagram.
     * @param name interface name.
     */
    public void removeInterface(String name) {
        UMLClass toRemove = findInterface(name);
        try {
            this.interfaces.remove(toRemove);
        } catch (Exception ignored) {
        }
        // observer
        support.firePropertyChange("removeInterface", toRemove, null);
    }

    /**
     * Add new relations to diagram.
     * @param umlRelation relation that 'll be added
     */
    public void addRelation(UMLRelation umlRelation) {
        if (umlRelation == null) {
            System.out.println("Null UMLRelation");
        } else {
            System.out.println("NotNull UMLRelation");
        }
        this.relations.add(umlRelation);
    }

    public UMLRelation createRelation(ClassDiagram classDiagram, String classFrom, String classTo, String type) {
        UMLRelation umlRelation = new UMLRelation(classDiagram, classFrom, classTo, type);
        this.relations.add(umlRelation);
        return umlRelation;
    }

    /**
     * @return List of all the relations
     */
    public List<UMLRelation> getRelations() {
        return this.relations;
    }

    /**
     * Add interface to diagram.
     * @param umlInterface interface that 'll be added.
     */
    public void addInterface(UMLClass umlInterface) {
        this.interfaces.add(umlInterface);
    }

    public List<UMLClass> getClassesInterfaces() {
        List<UMLClass> result = new ArrayList<>(this.classes);
        result.addAll(this.interfaces);
        return result;
    }
}
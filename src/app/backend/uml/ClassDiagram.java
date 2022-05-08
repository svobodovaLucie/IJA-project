/*
 * File:         ClassDiagram.java
 * Institution:  FIT BUT 2021/2022
 * Course:       IJA - Java Programming Language
 * Authors:      Lucie Svobodová, xsvobo1x@stud.fit.vutbr.cz
 *               Jakub Kuzník, xkuzni04@stud.fit.vutbr.cz
 *
 * File contains implementation of the UML class diagram.
 */
package app.backend.uml;

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

    // class diagram is observable
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

    /**
     * Method adds property change listener (observer).
     *
     * @param pcl observer
     */
    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl);
    }

    /**
     * Method adds new class to the class diagram.
     *
     * @param umlClass class to be added
     */
    public void addClass(UMLClass umlClass) {
        this.classes.add(umlClass);
    }

    /**
     * Method adds interface to class diagram.
     *
     * @param umlInterface interface to be added.
     */
    public void addInterface(UMLClass umlInterface) {
        this.interfaces.add(umlInterface);
    }

    /**
     * Method adds new relationship to the class diagram.
     *
     * @param umlRelation relationship to be added
     */
    public void addRelation(UMLRelation umlRelation) {
        if (umlRelation == null)
            return;
        this.relations.add(umlRelation);
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
     * Method creates new interface (an instance of UMLClass) and
     * inserts it to the list of interfaces in the UML class diagram.
     * If the interface already exists, the method returns null.
     *
     * @param name name of the interface to be created
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
     * Method creates new relationship in the class diagram.
     *
     * @param classDiagram relationship will be added to this class diagram
     * @param classFrom class in that the relationship starts
     * @param classTo class in that the relationship ends
     * @param type type of the relationship
     * @return new relationship
     */
    public UMLRelation createRelation(ClassDiagram classDiagram, String classFrom, String classTo, String type) {
        UMLRelation umlRelation = new UMLRelation(classDiagram, classFrom, classTo, type);
        this.relations.add(umlRelation);
        return umlRelation;
    }

    /**
     * Method removes given class from the class diagram.
     *
     * @param name class name
     */
    public void removeClass(String name) {
        UMLClass toRemove = findClass(name);
        try {
            this.classes.remove(toRemove);
        } catch (Exception ignored) {}
        // observer
        support.firePropertyChange("removeClass", toRemove, null);
    }

    /**
     * Method removes given interface from the class diagram.
     *
     * @param name interface name
     */
    public void removeInterface(String name) {
        UMLClass toRemove = findInterface(name);
        try {
            this.interfaces.remove(toRemove);
        } catch (Exception ignored) {}
        // observer
        support.firePropertyChange("removeInterface", toRemove, null);
    }

    /**
     * Method removes a relationship.
     *
     * @param classFrom class in that the relationship starts
     * @param classTo class in that the relationship ends
     * @param type type of the relationship
     */
    public void removeRelation(String classFrom, String classTo, String type) {
        UMLRelation toRemove = findRelation(classFrom, classTo, type);
        // observer
        support.firePropertyChange("removeRelationship", toRemove, null);
        try {
            this.getRelations().remove(toRemove);
        } catch (Exception ignored) {}
    }

    /**
     * Finds class in the class diagram by name.
     *
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
     *
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
     * Method find a class or interface specified by its name.
     *
     * @param name (class, interface) we are looking for
     * @return class or interface if found
     *         null if not found
     */
    public UMLClass findClassInterface(String name) {
        UMLClass result = findClass(name);
        if (result == null) {
            result = findInterface(name);
        }
        return result;
    }

    /**
     * Method finds a relation. If the relation wasn't found, returns null.
     *
     * @param classFrom class in that the relationship starts
     * @param classTo class in that the relationship ends
     * @param type type of the relationship
     * @return relationship if found,
     *         null if not
     */
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

    /**
     * Method returns list of all classes in the class diagram.
     *
     * @return list of classes
     */
    public List <UMLClass> getClasses () {
        return this.classes;
    }

    /**
     * Method returns the list of all interfaces.
     *
     * @return list of interfaces
     */
    public List<UMLClass> getInterfaces() {
        return this.interfaces;
    }

    /**
     * Method returns a list of all classes and interfaces.
     *
     * @return list of all classes and interfaces
     */
    public List<UMLClass> getClassesInterfaces() {
        List<UMLClass> result = new ArrayList<>(this.classes);
        result.addAll(this.interfaces);
        return result;
    }

    /**
     * Method returns a list of all relationships in the class diagram.
     *
     * @return list of all relationships
     */
    public List<UMLRelation> getRelations() {
        return this.relations;
    }
}
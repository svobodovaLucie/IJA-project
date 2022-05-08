/*
 * File:         UMLClass.java
 * Institution:  FIT BUT 2021/2022
 * Course:       IJA - Java Programming Language
 * Authors:      Lucie Svobodová, xsvobo1x@stud.fit.vutbr.cz
 *               Jakub Kuzník, xkuzni04@stud.fit.vutbr.cz
 *
 * File contains implementation od UMLClass class that represents
 * the UML class.
 */
package app.backend.uml;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class represents a UML class. It is inherited from UMLClassifier.
 * It contains a list of its UML attributes and a list of its UML
 * methods. The UML class may be abstract.
 */
public class UMLClass extends UMLClassifier {
    private List<UMLAttribute> attributes;
    private List<UMLMethod> methods;
    private boolean isInterface;
    private ClassDiagram owner;

    // UMLClass is observable
    private PropertyChangeSupport support;

    /**
     * UMLClass constructor.
     *
     * @param name name of the UML class
     */
    public UMLClass(String name, boolean isInterface, ClassDiagram classDiagram) {
        super(name);
        this.isInterface = isInterface;
        this.attributes = new ArrayList<>();
        this.methods = new ArrayList<>();
        this.support = new PropertyChangeSupport(this);
        this.owner = classDiagram;
    }
    /**
     * Method returns true if the class is interface, false if not.
     *
     * @return true if the UML class is interface, false if not
     */
    public boolean isInterface() {
        return this.isInterface;
    }

    /**
     * Method inserts a UML argument to the UML class. The argument is
     * inserted at the end of the arguments list. If there is an argument
     * with the same name as the one to be added, new argument is not added.
     *
     * @param attr UML argument to be inserted to the list
     *
     * @return true if the argument was successfully added, false if not
     */
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

    /**
     * Method insert a UML method to the UML class. The amethod is inserted
     * at the end of the methods list. If there is already a method with the
     * same name as the one to be added, the method is not added.
     *
     * @param method UML method to be inserted to the list
     *
     * @return true if the method was successfully added, false if not
     */
    public boolean addMethod(UMLMethod method) {
        if (this.methods.contains(method))
            return true;
        try {
            this.methods.add(method);
        } catch (UnsupportedOperationException uoe) {
            return false;
        }
        return true;
    }

    /**
     * Method finds a UMLMethod in the list of methods.
     *
     * @param name name of the method to be found
     * @return UMLMethod if found,
     *         null if not
     */
    public UMLMethod findMethod(String name){
        // find class
        for (UMLMethod mtd : this.getMethods()) {
            if (mtd.getName().equals(name)) {
                return mtd;
            }
        }
        // not found -> null
        return null;
    }

    /**
     * Method returns a list of UML class' arguments.
     *
     * @return list of arguments of the UML class
     */
    public List<UMLAttribute> getAttributes() {
        return this.attributes;
    }

    /**
     * Method returns a list of UML class' methods.
     *
     * @return list of methods of the UML class
     */
    public List<UMLMethod> getMethods() {
        return this.methods;
    }

    /**
     * Method sets name of the class to the newName.
     *
     * @param newName new name to be set
     */
    public void setName(String newName) {
        // fire message to all observers
        this.support.firePropertyChange("newClassName", this.name, newName);
        this.name = newName;
    }

    /**
     * Method removes UMLMethod from the class diagram.
     *
     * @param umlMethod method to be removed
     */
    public void removeMethod(UMLMethod umlMethod) {
        this.methods.removeIf(method -> (umlMethod == method));
    }

    /**
     * Method removes UMLAttribute from the list of attributes.
     *
     * @param umlAttribute attribute to be removed
     */
    public void removeAttribute(UMLAttribute umlAttribute) {
        this.attributes.removeIf(attribute -> (umlAttribute == attribute));
    }

    /**
     * Method adds new observer to the list of observers.
     *
     * @param pcl observer to be added
     */
    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl);
    }
}


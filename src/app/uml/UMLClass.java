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
package app.uml;

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

    // TODO udelat z tohoto list


    private boolean isAbstract;

    private PropertyChangeSupport support;

    /**
     * UMLClass constructor. The UML class is not abstract.
     *
     * @param name name of the UML class
     */
    public UMLClass(String name) {
        super(name);
        this.isAbstract = false;
        this.attributes = new ArrayList<>();
        this.methods = new ArrayList<>();

        this.support = new PropertyChangeSupport(this);
    }

    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl);
    }

    public void removePropertyChangeListener(PropertyChangeListener pcl) {
        support.removePropertyChangeListener(pcl);
    }

    /**
     * Method returns true if the class is abstract, false if not.
     *
     * @return true if the UML class is abstract, false if not
     */
    public boolean isAbstract() {
        return this.isAbstract;
    }

    /**
     * Method sets the UML class abstract if the argument isAbstract
     * is true, else the UML class is set not to be abstract.
     *
     * @param isAbstract boolean that represents if the UML class
     *                   will be abstract or not
     */
    public void setAbstract(boolean isAbstract) {
        this.isAbstract = isAbstract;
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
     * Method returns the position af an UML argument in the list
     * of UML class' arguments. The position starts at 0. If the
     * UML argument is not in the arguments list, -1 is returned.
     *
     * @param attr UML argument to be found in the list
     * @return index of the UML argument in the arguments list,
     *         -1 if not found
     */
    public int getAttrPosition(UMLAttribute attr) {
        if (!attributes.contains(attr))
            return -1;
        return attributes.indexOf(attr);
    }

    /**
     * Method moves the UML argument specified by attr to the position
     * pos (counting from 0). If the argument is not present in the
     * UML class, -1 is returned. When moving to the position pos
     * all arguments starting at pos will be moved to the right.
     *
     * @param attr UML argument to be moved
     * @param pos position on thatt the UML argument should be moved
     * @return -1 if the UML argument is not in the list, 0 if moved
     */
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

    /**
     * Method returns unmodifiable list of UML class' arguments.
     *
     * @return list of arguments of the UML class
     */
    public List<UMLAttribute> getAttributes() {
        return Collections.unmodifiableList(this.attributes);
    }

    /**
     * Method returns unmodifiable list of UML class' methods.
     *
     * @return list of methods of the UML class
     */
    public List<UMLMethod> getMethods() {
        return Collections.unmodifiableList(this.methods);
    }

    // method change the name
    public void setName(String newName) {
        this.support.firePropertyChange("name", this.name, newName);
        this.name = newName;
    }
}


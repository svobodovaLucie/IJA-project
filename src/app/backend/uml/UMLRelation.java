/*
 * File:         UMLRelation.java
 * Institution:  FIT BUT 2021/2022
 * Course:       IJA - Java Programming Language
 * Authors:      Lucie Svobodová, xsvobo1x@stud.fit.vutbr.cz
 *               Jakub Kuzník, xkuzni04@stud.fit.vutbr.cz
 *
 * File contains implementation od UMLRelation class that represents
 * the UML relationship.
 */
package app.backend.uml;

/**
 * Class represents a relationship, that has class in that the relationship
 * starts, class in that the relationship ends and the relationship's type.
 */
public class UMLRelation {
    UMLClass from;
    UMLClass to;
    String type;

    /**
     * UMLRelation constructor.
     *
     * @param classDiagram main class diagram
     * @param classFrom class in that the relationship starts
     * @param classTo class in that the relationship ends
     * @param type type of the relationship
     */
    public UMLRelation(ClassDiagram classDiagram, String classFrom, String classTo, String type) {
        assert classDiagram != null;
        // TODO handle null
        this.from = classDiagram.findClassInterface(classFrom);
        this.to = classDiagram.findClassInterface(classTo);
        assert this.from != null;
        assert this.to != null;
        this.type = type;
    }

    /**
     * Method returns the UMLClass in that the relationship starts.
     *
     * @return UMLClass in that the relationship starts
     */
    public UMLClass getClassFrom() {
        return this.from;
    }

    /**
     * Method returns the UMLClass in that the relationship ends.
     *
     * @return UMLClass in that the relationship ends
     */
    public UMLClass getClassTo() {
        return this.to;
    }

    /**
     * Method returns the type of the relationship.
     *
     * @return type of the relationship
     */
    public String getRelationType() {
        return this.type;
    }
}

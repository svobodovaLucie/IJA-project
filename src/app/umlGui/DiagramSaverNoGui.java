/*
 * File:         DiagramSaver.java
 * Institution:  FIT BUT 2021/2022
 * Course:       IJA - Java Programming Language
 * Authors:      Lucie Svobodová, xsvobo1x@stud.fit.vutbr.cz
 *               Jakub Kuzník, xkuzni04@stud.fit.vutbr.cz
 *
 * Main backend file that is used for saving the diagram to the JSON file.
 */
package app.umlGui;

import app.backend.Diagrams;
import app.uml.*;
import javafx.event.ActionEvent;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Class saves the diagram to the JSON file.
 */
public class DiagramSaverNoGui {

    /**
     * Method saves the diagrams in a JSON format to a file.
     * @param event
     * @param diagrams
     */
    public static void saveJSON(ActionEvent event, Diagrams diagrams, String filename) {
        // create JSON array
        JSONObject diagram = new JSONObject();

        // check null
        JSONArray classes = saveClasses(diagrams.getClassDiagram());
        diagram.put("classes", classes);

        JSONArray interfaces = saveInterfaces(diagrams.getClassDiagram());
        diagram.put("interfaces", interfaces);

        JSONArray relationships = saveRelationships(diagrams.getClassDiagram());
        diagram.put("relationships", relationships);

        //saveClassDiagram(diagrams.getClassDiagram());
        // Write JSON file
        try (FileWriter file = new FileWriter(filename)) { // saves the file to dest/
            file.write(diagram.toJSONString());
            file.flush();
            System.out.println("Diagram saved in DiagramSaverNoGui.\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static JSONArray saveRelationships(ClassDiagram classDiagram) {
        JSONArray relationships = new JSONArray();
        for (UMLRelation relation : classDiagram.getRelations()) {
            relationships.add(saveRelation(relation));
        }
        return relationships;
    }

    /**
     * Method saves the UML class diagram in a JSON format to a file.
     *
     * @param classDiagram contains the class diagram
     */
    public static JSONArray saveClasses(ClassDiagram classDiagram) {

        if (classDiagram == null) { // TODO
            System.out.println("Can't save the diagram\n");
            return null; // TODO catch exception
        }

        // save classes
        JSONArray classes = new JSONArray();
        for (UMLClass cls : classDiagram.getClasses()) {
            classes.add(saveClass(cls));
        }
        // save relations

        return classes;
    }

    public static JSONArray saveInterfaces(ClassDiagram classDiagram) {
        // save interfaces
        JSONArray interfaces = new JSONArray();
        for (UMLClass intf : classDiagram.getInterfaces()) {
            interfaces.add(saveClass(intf));
        }

        return interfaces;
    }

    /**
     * Method saves a class in JSON format.
     *
     * @param umlClass class to be saved
     * @return class represented as JSONObject
     */
    private static JSONObject saveClass(UMLClass umlClass) {
        JSONObject oneClass = new JSONObject();
        oneClass.put("name", umlClass.getName());

        JSONArray attributes = saveAttributes(umlClass);
        oneClass.put("attributes", attributes);

        JSONArray methods = saveMethods(umlClass);
        oneClass.put("methods", methods);
        return oneClass;
    }

    public static JSONObject saveRelation(UMLRelation umlRelation) {
        JSONObject oneRelationship = new JSONObject();
        oneRelationship.put("from", umlRelation.getClassFrom().getName());
        oneRelationship.put("to", umlRelation.getClassTo().getName());
        oneRelationship.put("type", umlRelation.getRelationType());
        return oneRelationship;
    }

    /**
     * Method saves the attributes of a class in JSON format.
     *
     * @param umlClass class containing the attributes to be saved
     * @return JSONArray containing information about the attributes
     */
    private static JSONArray saveAttributes(UMLClass umlClass) {
        // class attributes
        JSONArray attributes = new JSONArray();

        // save all attributes
        for (UMLAttribute attr : umlClass.getAttributes()) {
            JSONObject oneAttr = new JSONObject();
            List<String> nameTypeAccess = attr.getNameTypeAccess();
            oneAttr.put("name", nameTypeAccess.get(0));
            oneAttr.put("type", nameTypeAccess.get(1));
            oneAttr.put("access", nameTypeAccess.get(2));

            attributes.add(oneAttr);
        }
        return attributes;
    }

    /**
     * Method saves the attributes of a method from text field representing
     * the method to the JSON format.
     *
     * @param umlMethod method to be saved
     * @return attributes of a method represented in JSONArray
     */
    private static JSONArray saveMethodAttributes(UMLMethod umlMethod) {
        // class method attributes
        JSONArray methodAttributes = new JSONArray();

        // save all method attributes
        for (UMLAttribute attr : umlMethod.getAttributes()) {
            JSONObject oneMethAttr = new JSONObject();
            oneMethAttr.put("type", attr.getType());
            methodAttributes.add(oneMethAttr);
        }
        return methodAttributes;
    }

    /**
     * Method saves the methods in JSONArray.
     *
     * @param umlClass class that contains the methods to be saved
     * @return methods represented as JSONArray
     */
    private static JSONArray saveMethods(UMLClass umlClass) {
        // class methods
        JSONArray methods = new JSONArray();

        // save all methods
        for (UMLMethod meth : umlClass.getMethods()) {
            JSONObject oneMeth = new JSONObject();
            List<String> name_type_access = meth.getNameTypeAccess();
            oneMeth.put("name", name_type_access.get(0));
            oneMeth.put("type", name_type_access.get(1));
            oneMeth.put("access", name_type_access.get(2));
            // save method's attributes
            JSONArray methodAttributes = saveMethodAttributes(meth);
            oneMeth.put("attributes", methodAttributes);
            methods.add(oneMeth);
        }
        return methods;
    }
}

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

import javafx.event.ActionEvent;
import javafx.scene.Group;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Class saves the diagram to the JSON file.
 */
public class DiagramSaver {

    /**
     * Method saves the UML class diagram in a JSON format to a file.
     *
     * @param event
     * @param root root containing the diagram
     */
    public static void saveJSON(ActionEvent event, Group root) {
        if (root == null) { // TODO
            System.out.println("Can't save the diagram\n");
            return;
        }

        // save classes, interfaces, relationships
        JSONArray classes = new JSONArray();
        for (int i = 0; i < root.getChildrenUnmodifiable().size(); i++) {
            // TODO later -> all classes to UMLClassDiagramGui (need to have class diagram and sequence diagrams later)
            if (root.getChildrenUnmodifiable().get(i) instanceof UMLClassGui) {
                // save classes
                classes.add(saveClass((UMLClassGui) root.getChildrenUnmodifiable().get(i)));    // TODO fix the cast
            }
            // save interfaces
            // save relationships
        }

        JSONObject classes_interfaces_relationships = new JSONObject();
        classes_interfaces_relationships.put("classes", classes);

        // Write JSON file
        try (FileWriter file = new FileWriter("savedDiagram.json")) { // saves the file to dest/
                file.write(classes_interfaces_relationships.toJSONString());
                file.flush();
                System.out.println("Diagram saved.\n");
        } catch (IOException e) {
                e.printStackTrace();
        }
    }

    /**
     * Method saves the attributes of a class in JSON format.
     *
     * @param umlClass class containing the attributes to be saved
     * @return JSONArray containing information about the attributes
     */
    private static JSONArray saveAttributes(UMLClassGui umlClass) {
        // class attributes
        JSONArray attributes = new JSONArray();

        // save all attributes
        for (UMLAttributeGui attr : umlClass.getAttributes()) {
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
    private static JSONArray saveMethodAttributes(UMLMethodGui umlMethod) {
        // class method attributes
        JSONArray methodAttributes = new JSONArray();

        // save all method attributes
        for (String type : umlMethod.getMethodAttributesTypes()) {
            System.out.println("methodAttribute: " + type);
            JSONObject oneMethAttr = new JSONObject();
            oneMethAttr.put("type", type);
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
    private static JSONArray saveMethods(UMLClassGui umlClass) {
        // class methods
        JSONArray methods = new JSONArray();

        // save all methods
        for (UMLMethodGui meth : umlClass.getMethods()) {
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

    /**
     * Method saves a class in JSON format.
     *
     * @param umlClass class to be saved
     * @return class represented as JSONObject
     */
    private static JSONObject saveClass(UMLClassGui umlClass) {
        JSONObject oneClass = new JSONObject();
        oneClass.put("name", umlClass.getName());

        JSONArray attributes = saveAttributes(umlClass);
        oneClass.put("attributes", attributes);

        JSONArray methods = saveMethods(umlClass);
        oneClass.put("methods", methods);
        return oneClass;
    }
}

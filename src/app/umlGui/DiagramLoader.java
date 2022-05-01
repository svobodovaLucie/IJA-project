/*
 * File:         DiagramLoader.java
 * Institution:  FIT BUT 2021/2022
 * Course:       IJA - Java Programming Language
 * Authors:      Lucie Svobodová, xsvobo1x@stud.fit.vutbr.cz
 *               Jakub Kuzník, xkuzni04@stud.fit.vutbr.cz
 *
 * Main backend file that is used for loading the diagram file to the application
 * and doing all the things.
 */
package app.umlGui;

import javafx.scene.Group;
import javafx.scene.layout.VBox;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;


/**
 * Class that loads the diagram from its JSON representation.
 */
public class DiagramLoader {

    /**
     * Method returns Root (type Group), that 'll contain all the seq diagrams.
     *
     *             root
     *        /   |     \     \
     *     seq1  seq2   ...  seqn
     *
     * where seq1 is one seq diagram
     *
     * @param filename name of the file that contains the diagram in JSON format
     * @throws IOException
     * @throws ParseException
     *
     */
    public Group loadSeqDiagramsGui(String filename) throws IOException, ParseException{
        Group root = new Group();
        return null;
    }

    /**
     * Method loads the UML class diagram from JSON diagram file into the application.
     *
     * @param filename name of the file that contains the diagram in JSON format
     * @throws IOException
     * @throws ParseException
     */
    public Group loadClassDiagramGui(String filename) throws IOException, ParseException {
        // create a Group
        Group root = new Group();

        // load the JSON file
        JSONParser jsonParser = new JSONParser();

        // load everything to diagram object
        Object input = jsonParser.parse(new FileReader(filename));
        JSONObject diagram = (JSONObject)input;

        // save all parameters loaded from the input file
        saveClassesGui((JSONArray)diagram.get("classes"), root);

        // return the Group
        return root;
    }

    /**
     * Method loads the UML classes from the UML class diagram file.
     *
     * @param classes array of UML classes in JSON
     * @param root root element that contains the GUI objects
     */
    private void saveClassesGui(JSONArray classes, Group root) {
        // load every class from the JSON file
        for (Object aClass : classes) {
            JSONObject classObj = (JSONObject) aClass;

            // create UMLClassGui object and add it to the root
            String className = (String)classObj.get("name");
            UMLClassGui umlClass = new UMLClassGui(className);
            root.getChildren().add(umlClass);

            // load attributes and methods
            saveAttributesGui(umlClass, (JSONArray)classObj.get("attributes"));
            saveMethodsGui(umlClass, (JSONArray)classObj.get("methods"));
        }
    }

    /**
     * Method loads the UML attributes of umlClass from diagram file.
     *
     * @param umlClass UML class that contains UML attributes to be loaded
     * @param attributes array of UML attributes in JSON
     */
    private void saveAttributesGui(UMLClassGui umlClass, JSONArray attributes) {
        // load all attributes of a umlClass
        for (Object attribute : attributes) {
            JSONObject attr = (JSONObject) attribute;

            // save the name, type and access
            String attrName = (String) attr.get("name");
            String attrType = (String) attr.get("type");
            String attrAccess = (String) attr.get("access");

            // create UMLAttributeGui object and add it to the root
            UMLClassifierGui umlType = new UMLClassifierGui(attrType);
            UMLAttributeGui umlAttr = new UMLAttributeGui(attrName, umlType, attrAccess);
            umlClass.addAttributeGui(umlAttr);
        }
    }

    /**
     * Method loads the UML class diagram methods from the diagram text file.
     *
     * @param umlClass UML class that contains UML methods to be loaded
     * @param methods array of UML methods in JSON
     */
    private void saveMethodsGui (UMLClassGui umlClass, JSONArray methods) {
        // load all methods of a umlClass
        for (Object methodObj : methods) {
            JSONObject method = (JSONObject) methodObj;

            // save the name, type and access
            String methodName = (String) method.get("name");
            String methodType = (String) method.get("type");
            String methodAccess = (String) method.get("access");

            // create UMLAttributeGui object and add it to the root
            UMLClassifierGui umlType = new UMLClassifierGui(methodType);
            UMLMethodGui umlMethod = new UMLMethodGui(methodName, umlType, methodAccess);
            umlClass.addMethodGui(umlMethod);

            // get method attributes
            saveMethodAttributesGui(umlMethod, (JSONArray)method.get("attributes"));

            // display the method with its attributes in the umlClass
            umlMethod.setText(umlMethod.toStringMethAttr());
        }
    }

    /**
     * Method loads the UML method attributes from the diagram file.
     *
     * @param umlMethod UML method that contains the UML attributes to be loaded
     * @param methodAttributes array of UML method attributes in JSON
     */
    private void saveMethodAttributesGui (UMLMethodGui umlMethod, JSONArray methodAttributes) {
        // load all attributes of a umlMethod
        for (Object methodAttribute : methodAttributes) {
            JSONObject methodAttr = (JSONObject) methodAttribute;

            // save the type
            String methodAttrType = (String) methodAttr.get("type");

            // create the method attribute object and add it to the root
            UMLClassifierGui umlType = new UMLClassifierGui(methodAttrType);
            UMLAttributeGui umlAttr = new UMLAttributeGui("", umlType, "");
            umlMethod.addArgumentGui(umlAttr);
        }
    }
}

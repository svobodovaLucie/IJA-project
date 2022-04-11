/*
 * File:         MainApplication.java
 * Institution:  FIT BUT 2021/2022
 * Course:       IJA - Java Programming Language
 * Authors:      Lucie Svobodová, xsvobo1x@stud.fit.vutbr.cz
 *               Jakub Kuzník, xkuzni04@stud.fit.vutbr.cz
 *
 * Main backend file that is used for loading the diagram file to the application
 * and doing all the things.
 */
package app.backend;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import app.uml.*;

import java.io.FileReader;
import java.io.IOException;

/**
 * Main backend class that stores the UML class diagram.
 */
public class MainApplication {
    private ClassDiagram classDiagram;

    // private array of sequence diagrams

    /**
     * Main method for running the backend.
     *
     * @param filename name of the file with diagram in text form
     * @throws IOException
     * @throws ParseException
     */
    public void main(String filename) throws IOException, ParseException {
        //ClassDiagram classDiagram = new ClassDiagram();
        // saves the diagram to ClassDiagram
        System.out.println("Inside backend main\n");
        loadClassDiagram(filename);
    }

    /**
     * MainApplication constructor.
     */
    public MainApplication() {
        this.classDiagram = new ClassDiagram("ClassDiagram");
    }

    /**
     * Method loads the UML method attributes from the diagram file.
     *
     * @param umlMethod UML method that contains the UML attributes to be loaded
     * @param methodAttributes array of UML method attributes in JSON
     */
    private void saveMethodAttributes (UMLMethod umlMethod, JSONArray methodAttributes) {
        for (Object methodAttribute : methodAttributes) {
            JSONObject methodAttr = (JSONObject) methodAttribute;

            String methodAttrType = (String) methodAttr.get("type");
            System.out.println("Method attribute type: " + methodAttrType);

            // save the method attribute to the application database
            UMLClassifier umlType = new UMLClassifier(methodAttrType);
            UMLAttribute umlAttr = new UMLAttribute("", umlType);
            umlMethod.addArgument(umlAttr);
        }
    }

    /**
     * Method that load the UML class diagram methods from the diagram text file.
     *
     * @param umlClass UML class that contains UML methods to be loaded
     * @param methods array of UML methods in JSON
     */
    private void saveMethods (UMLClass umlClass, JSONArray methods) {
        for (Object methodObj : methods) {
            JSONObject method = (JSONObject) methodObj;

            String methodName = (String) method.get("name");
            System.out.println("Method name: " + methodName);

            String methodType = (String) method.get("type");
            System.out.println("Method type: " + methodType);

            String methodAccess = (String) method.get("access");
            System.out.println("Method access: " + methodAccess);

            // save the method to the application database
            UMLClassifier umlType = new UMLClassifier(methodType);
            UMLMethod umlMethod = new UMLMethod(methodName, umlType);
            umlClass.addMethod(umlMethod);
            // get method attributes
            saveMethodAttributes(umlMethod, (JSONArray)method.get("attributes"));
        }
    }

    /**
     * Method loads the UML attributes of umlClass from diagram file.
     *
     * @param umlClass UML class that contains UML attributes to be loaded
     * @param attributes array of UML attributes in JSON
     */
    private void saveAttributes(UMLClass umlClass, JSONArray attributes) {
        for (Object attribute : attributes) {
            JSONObject attr = (JSONObject) attribute;

            String attrName = (String) attr.get("name");
            System.out.println("Attribute name: " + attrName);

            String attrType = (String) attr.get("type");
            System.out.println("Attribute type: " + attrType);

            String attrAccess = (String) attr.get("access");
            System.out.println("Attribute access: " + attrAccess);

            // save the attribute to the application database
            UMLClassifier umlType = new UMLClassifier(attrType);
            UMLAttribute umlAttr = new UMLAttribute(attrName, umlType);
            umlClass.addAttribute(umlAttr);
        }
    }

    /**
     * Method loads the UML classes from the UML class diagram file.
     *
     * @param classes array of UML classes in JSON
     */
    private void saveClasses(JSONArray classes) {
        for (Object aClass : classes) {
            JSONObject classObj = (JSONObject) aClass;

            String className = (String)classObj.get("name");
            System.out.println("Class name: " + className);
            UMLClass umlClass = this.classDiagram.createClass(className);

            saveAttributes(umlClass, (JSONArray)classObj.get("attributes"));
            saveMethods(umlClass, (JSONArray)classObj.get("methods"));
        }
    }

    /**
     * Method loads the UML class diagram from diagram file into the application.
     *
     * @param filename name of the file that contains the diagram in JSON format
     * @throws IOException
     * @throws ParseException
     */
    private void loadClassDiagram(String filename) throws IOException, ParseException {
        // load the JSON file
        JSONParser jsonParser = new JSONParser();

        // load everything to diagram object
        Object input = jsonParser.parse(new FileReader(filename));
        JSONObject diagram = (JSONObject)input;

        // save all parameters loaded from the input file
        saveClasses((JSONArray)diagram.get("classes"));
    }
}

package app.backend;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import app.uml.*;
import java.io.FileReader;
import java.io.IOException;

public class MainApplication {
    private ClassDiagram classDiagram;
    // private array of sequence diagrams

    // main of the backend
    public void main(String filename) throws IOException, ParseException {
        //ClassDiagram classDiagram = new ClassDiagram();
        // saves the diagram to ClassDiagram
        loadClassDiagram(filename);
    }

    // constructor
    public MainApplication() {
        // maybe z cmd line args[1] - nazev diagramu
        this.classDiagram = new ClassDiagram("ClassDiagram");
    }

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

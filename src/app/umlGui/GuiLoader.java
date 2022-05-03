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

import app.backend.Diagrams;
import app.uml.ClassDiagram;
import app.uml.UMLAttribute;
import app.uml.UMLClass;
import app.uml.UMLMethod;
import javafx.scene.Group;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

/**
 * Class that loads the diagram from its JSON representation.
 */
public class GuiLoader {

    /**
     * Method loads the UML class diagram from JSON diagram file into the application.
     *
     * @param classDiagram class diagram to be loaded
     * @throws IOException
     * @throws ParseException
     */
    public Group loadClassDiagramGui(ClassDiagram classDiagram) throws IOException, ParseException {
        // create a Group
        Group root = new Group();

        // load classes from classDiagram
        saveClassesGui(classDiagram.getClasses(), root);

        // return the Group
        return root;
    }

    /**
     * Method loads the UML classes from the UML class diagram file.
     *
     * @param classes array of UML classes in JSON
     * @param root root element that contains the GUI objects
     */
    private void saveClassesGui(List<UMLClass> classes, Group root) {
        for (UMLClass umlClass : classes) {
            // create GUI element for the class
            UMLClassGui umlClassGui = new UMLClassGui(umlClass);
            root.getChildren().add(umlClassGui);

            // load attributes and methods
            saveAttributesGui(umlClass.getAttributes(), umlClassGui);
            saveMethodsGui(umlClass.getMethods(), umlClassGui);
        }
        /*
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

         */
    }

    /**
     * Method loads the UML attributes of umlClass from diagram file.
     *
     * @param umlClassGui UML class that contains UML attributes to be loaded
     * @param attributes array of UML attributes in JSON
     */
    private void saveAttributesGui(List <UMLAttribute> attributes, UMLClassGui umlClassGui) {
        for (UMLAttribute attr : attributes) {
            // get name, type, access
            List <String> nameTypeAccess = attr.getNameTypeAccess();
            // create UMLClassifierGui
            //UMLClassifierGui typeGui = new UMLClassifierGui(nameTypeAccess.get(1));
            // create GUI attribute
            UMLAttributeGui attrGui = new UMLAttributeGui(attr);

            // add attribute to the GUI
            umlClassGui.addAttributeGui(attrGui);
        }
    }

    /**
     * Method loads the UML class diagram methods from the diagram text file.
     *
     * @param umlClassGui UML class that contains UML methods to be loaded
     * @param methods array of UML methods in JSON
     */
    private void saveMethodsGui (List <UMLMethod> methods, UMLClassGui umlClassGui) {
        // load all methods of a umlClass
        for (UMLMethod method : methods) {
            // get the name, type and access
            List <String> nameTypeAccess = method.getNameTypeAccess();

            // create UMLMethodGui object and add it to the root
            UMLMethodGui umlMethodGui = new UMLMethodGui(method);
            umlClassGui.addMethodGui(umlMethodGui);

            // get method attributes
            saveMethodAttributesGui(method.getAttributes(), umlMethodGui);

            // display the method with its attributes in the umlClass
            umlMethodGui.setText(umlMethodGui.toStringMethAttr());
        }
    }

    /**
     * Method loads the UML method attributes from the diagram file.
     *
     * @param umlMethodGui UML method that contains the UML attributes to be loaded
     * @param methodAttributes array of UML method attributes in JSON
     */
    private void saveMethodAttributesGui(List <UMLAttribute> methodAttributes, UMLMethodGui umlMethodGui) {
        // load all attributes of a umlMethod
        for (UMLAttribute methodAttribute : methodAttributes) {
            // create the method attribute object and add it to the root
            UMLAttributeGui umlAttrGui = new UMLAttributeGui(methodAttribute);
            umlMethodGui.addArgumentGui(umlAttrGui);
        }
    }
}

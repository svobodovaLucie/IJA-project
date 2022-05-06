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

import app.uml.*;
import javafx.scene.Group;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that loads the diagram from its JSON representation.
 */
public class GuiLoader {

    public List <Group> loadSeqDiagramGui(List <SeqDiagram> seqDiagrams) {
        //Group root = new Group();
        List <Group> root = new ArrayList<>();

        for (SeqDiagram seqDig : seqDiagrams) {
            // group for one diagram
            Group rootSeq = new Group();

            //TODO TADY FIX
            // load the diagram
            UMLSeqDiaGui seqDiaGui = new UMLSeqDiaGui(seqDig);
            rootSeq.getChildren().add(seqDiaGui);

            // add to list of Groups
            root.add(rootSeq);
        }

        return root;
    }
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

        // create ClassDiagramGui
        UMLClassDiagramGui umlClassDiagramGui = new UMLClassDiagramGui(classDiagram);

        // observer pattern
        classDiagram.addPropertyChangeListener(umlClassDiagramGui);

        // load classes from classDiagram
        saveClassesGui(classDiagram.getClasses(), umlClassDiagramGui);

        // load interfaces from classDiagram
        saveInterfacesGui(classDiagram.getInterfaces(), umlClassDiagramGui);

        // load relations from classDiagram
        saveRelationsGui(classDiagram.getRelations(), umlClassDiagramGui);

        // add umlClassDiagramGui to root
        root.getChildren().add(umlClassDiagramGui);

        // return the root Group
        return root;
    }

    private void saveRelationsGui(List<UMLRelation> relations, UMLClassDiagramGui umlClassDiagramGui) {
        int i = 0;
        for (UMLRelation relation : relations) {
            // create new relation GUI and add it to the class diagram GUI
            System.out.println("i: " + i++);
            UMLRelationGui umlRelationGui = new UMLRelationGui(relation, umlClassDiagramGui);
            System.out.println("j");

            // add relation to the GUI
            umlClassDiagramGui.getChildren().add(umlRelationGui.getRelationArrow());
        }
    }
    /**
     * Method loads the UML classes from the UML class diagram file.
     *
     * @param classes array of UML classes in JSON
     * @param umlClassDiagramGui root element that contains the GUI objects
     */
    private void saveClassesGui(List<UMLClass> classes, UMLClassDiagramGui umlClassDiagramGui) {
        for (UMLClass umlClass : classes) {
            // create GUI element for the class
            UMLClassGui umlClassGui = new UMLClassGui(umlClass, umlClassDiagramGui);
            umlClassDiagramGui.getChildren().add(umlClassGui);

            // load attributes and methods
            saveAttributesGui(umlClass.getAttributes(), umlClassGui);
            saveMethodsGui(umlClass.getMethods(), umlClassGui);
        }
    }

    private void saveInterfacesGui(List<UMLClass> interfaces, UMLClassDiagramGui umlClassDiagramGui) {
        for (UMLClass umlInterface : interfaces) {
            // create GUI element for the class
            UMLClassGui umlInterfaceGui = new UMLClassGui(umlInterface, umlClassDiagramGui);
            umlClassDiagramGui.getChildren().add(umlInterfaceGui);

            // load attributes and methods
            saveAttributesGui(umlInterface.getAttributes(), umlInterfaceGui);
            saveMethodsGui(umlInterface.getMethods(), umlInterfaceGui);
        }
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

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
import app.gui.GuiMain;
import app.uml.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// TODO check and update comments!!!

/**
 * Class that loads the diagram from its JSON representation.
 */
public class DiagramLoaderNoGui {

    // TODO loads all diagrams
    /**
     * Method loads the UML class diagram from JSON diagram file into the application.
     *
     * @param filename name of the file that contains the diagram in JSON format
     * @throws IOException
     * @throws ParseException
     */
    public Diagrams loadDiagrams(String filename) throws IOException, ParseException {
        // create a Group
        //Group root = new Group();
        Diagrams diagrams = new Diagrams();


        //ClassDiagram classDiagram = new ClassDiagram("Class Diagram");

        // load the JSON file
        JSONParser jsonParser = new JSONParser();

        // load everything to diagram object
        Object input = jsonParser.parse(new FileReader(filename));
        JSONObject diagram = (JSONObject)input;

        // load class diagram
        // save all parameters loaded from the input file
        //saveClassesGui((JSONArray)diagram.get("classes"), root);
        ClassDiagram classDiagram = new ClassDiagram("Class Diagram");
        saveClasses((JSONArray)diagram.get("classes"), classDiagram);
        saveInterfaces((JSONArray)diagram.get("interfaces"), classDiagram);
        saveRelations((JSONArray)diagram.get("relationships"), classDiagram);
        diagrams.addClassDiagram(classDiagram);

        // load seq diagrams
        saveSeqDiagrams((JSONArray)diagram.get("Sequence_diagrams"), diagrams);

        // return the Diagrams
        return diagrams;
    }

    public void saveSeqDiagrams(JSONArray seqDiagrams, Diagrams diagrams) {
        // TODO
        if (seqDiagrams == null)
            return;

        // for each object in sequence diagrams
        int i = 0; // indexing groups
        for (Object seqDig : seqDiagrams) {

            JSONObject oneDiag = (JSONObject) seqDig;

            String diagName = (String) oneDiag.get("name");

            // Actors array (name, class)
            List<String> actorsList = this.getSeqActors((JSONArray) oneDiag.get("Actors"));
            List<String> messageList = this.getSeqMethods((JSONArray) oneDiag.get("Messages"));

            System.out.println("......");
            System.out.println(diagName);
            System.out.println(actorsList);
            System.out.println(messageList);
            System.out.println("......");

            // create new diagram
            SeqDiagram seqDiagram = new SeqDiagram(diagName);

            // add all actors and messages set consistency flags
            seqDiagram.addAllActors(actorsList, diagrams);
            seqDiagram.addAllMessages(messageList, diagrams);

            diagrams.addSeqDiagram(seqDiagram);

        }
    }

    /**
     * actorList = name_met1 -> class2 -> true -> name_met2 -> class2 -> false ...
     * @param actors JSONArray that has all the sequence diagram actors
     * @return  List of sequence diagram actors
     */
    private List<String> getSeqActors(JSONArray actors){
        // actorList = name_met1 -> class2 -> name_met2 -> class2
        List<String> actorsList = new ArrayList<String>();
        for (Object ac : actors){
            JSONObject oneActor         = (JSONObject) ac;
            String actorName            = (String) oneActor.get("actorName");
            String nameClass            = (String) oneActor.get("class");
            String createdByMessage     = (String) oneActor.get("createdByMessage");

            actorsList.add(actorName);
            actorsList.add(nameClass);
            actorsList.add(createdByMessage);
        }
        return  actorsList;
    }

    /**
     *  // todo actualize comment
     *  messageList = from1 -> to1 -> type1 -> methodName1 ->
     *     -> from2 -> to2 -> type2 -> methodName2
     * @param messages JSONArray that has all the sequence diagram messages
     * @return List of sequence diagram messages
     */
    private List<String> getSeqMethods(JSONArray messages){

        // messageList = from1 -> to1 -> type1 -> methodName1 ->
        //    -> from2 -> to2 -> type2 -> methodName2
        List<String> messageList = new ArrayList<String>();
        for (Object me : messages){
            JSONObject oneMessage = (JSONObject) me;
            String classFrom         = (String) oneMessage.get("classFrom");
            String classTo           = (String) oneMessage.get("classTo");
            String from              = (String) oneMessage.get("from");
            String to                = (String) oneMessage.get("to");
            String type              = (String) oneMessage.get("type");
            String methodName        = (String) oneMessage.get("methodName");

            messageList.add(classFrom);
            messageList.add(classTo);
            messageList.add(from);
            messageList.add(to);
            messageList.add(type);
            messageList.add(methodName);
        }
        return messageList;
    }

    /**
     * Method loads the UML classes from the UML class diagram file.
     *
     * @param classes array of UML classes in JSON
     * @param classDiagram root element that contains the GUI objects
     */
    private void saveClasses(JSONArray classes, ClassDiagram classDiagram) {
        if (classes == null) {
            return;
        }
        // load every class from the JSON file
        for (Object aClass : classes) {
            JSONObject classObj = (JSONObject) aClass;

            // create UMLClassGui object and add it to the root
            String className = (String)classObj.get("name");
            UMLClass umlClass = new UMLClass(className, false, classDiagram);
            //root.getChildren().add(umlClass);

            // load attributes and methods
            saveAttributes(umlClass, (JSONArray)classObj.get("attributes"));
            saveMethods(umlClass, (JSONArray)classObj.get("methods"));

            classDiagram.addClass(umlClass);
        }
    }

    public void saveRelations(JSONArray relations, ClassDiagram classDiagram) {
        if (relations == null) {
            return;
        }
        for (Object relation : relations) {
            JSONObject relationObj = (JSONObject) relation;

            // create UMLRelation object
            // from
            String classNameFrom = (String)relationObj.get("from");

            // to
            String classNameTo = (String)relationObj.get("to");

            // type
            String type = (String)relationObj.get("type");

            System.out.println("----------");
            System.out.println(classNameFrom);
            System.out.println(classNameTo);
            System.out.println(type);
            System.out.println("----------");

            // create UMLRelation
            UMLRelation umlRelation = new UMLRelation(classDiagram, classNameFrom, classNameTo, type);

            // add to class diagram (add to class from and class to)
            classDiagram.addRelation(umlRelation);
        }
    }

    public void saveInterfaces(JSONArray interfaces, ClassDiagram classDiagram) {
        if (interfaces == null) {
            return;
        }
        // load each interface from the JSON file
        for (Object interfc : interfaces) {
            JSONObject interfaceObj = (JSONObject) interfc;

            // create UMLClassGui object and add it to the root
            String interfaceName = (String)interfaceObj.get("name");
            UMLClass umlInterface = new UMLClass(interfaceName, true, classDiagram);

            // load attributes and methods
            saveAttributes(umlInterface, (JSONArray)interfaceObj.get("attributes"));
            saveMethods(umlInterface, (JSONArray)interfaceObj.get("methods"));

            classDiagram.addInterface(umlInterface);
        }
    }

    /**
     * Method loads the UML attributes of umlClass from diagram file.
     *
     * @param umlClass UML class that contains UML attributes to be loaded
     * @param attributes array of UML attributes in JSON
     */
    private void saveAttributes(UMLClass umlClass, JSONArray attributes) {
        // load all attributes of a umlClass
        for (Object attribute : attributes) {
            JSONObject attr = (JSONObject) attribute;

            // save the name, type and access
            String attrName = (String) attr.get("name");
            String attrType = (String) attr.get("type");
            String attrAccess = (String) attr.get("access");

            // create UMLAttributeGui object and add it to the root
            UMLClassifier umlType = new UMLClassifier(attrType);
            UMLAttribute umlAttr = new UMLAttribute(attrName, umlType, attrAccess);
            umlClass.addAttribute(umlAttr);
        }
    }

    /**
     * Method loads the UML class diagram methods from the diagram text file.
     *
     * @param umlClass UML class that contains UML methods to be loaded
     * @param methods array of UML methods in JSON
     */
    private void saveMethods(UMLClass umlClass, JSONArray methods) {
        // load all methods of a umlClass
        for (Object methodObj : methods) {
            JSONObject method = (JSONObject) methodObj;

            // save the name, type and access
            String methodName = (String) method.get("name");
            String methodType = (String) method.get("type");
            String methodAccess = (String) method.get("access");

            // create UMLAttributeGui object and add it to the root
            UMLClassifier umlType = new UMLClassifier(methodType);
            UMLMethod umlMethod = new UMLMethod(methodName, umlType, methodAccess);
            umlClass.addMethod(umlMethod);

            // get method attributes
            saveMethodAttributes(umlMethod, (JSONArray)method.get("attributes"));

            // display the method with its attributes in the umlClass
            //umlMethod.setText(umlMethod.toStringMethAttr());
        }
    }

    /**
     * Method loads the UML method attributes from the diagram file.
     *
     * @param umlMethod UML method that contains the UML attributes to be loaded
     * @param methodAttributes array of UML method attributes in JSON
     */
    private void saveMethodAttributes(UMLMethod umlMethod, JSONArray methodAttributes) {
        // load all attributes of a umlMethod
        for (Object methodAttribute : methodAttributes) {
            JSONObject methodAttr = (JSONObject) methodAttribute;

            // save the type
            String methodAttrType = (String) methodAttr.get("type");

            // create the method attribute object and add it to the root
            UMLClassifier umlType = new UMLClassifier(methodAttrType);
            UMLAttribute umlAttr = new UMLAttribute("", umlType, "");
            umlMethod.addArgument(umlAttr);
        }
    }
}

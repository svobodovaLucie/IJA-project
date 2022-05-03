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
import java.util.ArrayList;
import java.util.List;


/**
 * Class that loads the diagram from its JSON representation.
 */
public class DiagramLoader {

    /**
     * where seq1 is one seq diagram
     *
     *
     *              sequence_diagram_name
     *              |                    |
     *            ACTORS                Messages
     *           |      |               |      |
     *          name1   name2         type1    type2
     *           |        |             |       |
     *          class    class        from     from
     *                                  |        |
     *                                 to        to
     *
     * @param filename name of the file that contains the diagram in JSON format
     * @throws IOException
     * @throws ParseException
     * @return List of Group - each node is one seq diagram
     *
     */
    public List<Group> loadSeqDiagramsGui(String filename) throws IOException, ParseException{
        // create array of groups
        List<Group> root_list = new ArrayList<Group>();

        // load the JSON file
        JSONParser jsonParser = new JSONParser();

        // load everything to diagram object
        Object input = jsonParser.parse(new FileReader(filename));
        JSONObject diagram = (JSONObject)input;

        JSONArray seqDigs = (JSONArray) diagram.get("Sequence_diagrams");

        int i = 0; // indexing groups
        for (Object seqDig : seqDigs) {
            JSONObject oneDiag = (JSONObject) seqDig;

            String diagName    = (String) oneDiag.get("name_seq");
            // Actors array (name, class)
            List<String> actorsList = this.getSeqActors((JSONArray) oneDiag.get("Actors"));
            List<String> messageList = this.getSeqMethods((JSONArray) oneDiag.get("Messages"));

            // Strings are converted to group data type here
            Group root = seqDiagramGroup(diagName, actorsList, messageList);

            // add group to list //store one seq diagram
            root_list.add(root);
        }
        return root_list;
    }

    /**
     * Method converse strings to Group that represent one sequence diagram
     *
     * @param diagName Diagram name
     * @param actorsList Actors list can be obtained by getSeqActors()
     * @param messageList Messages list can be obtained by getSeqMethods()
     * @return group that represent one sequence diagram
     */
    private Group seqDiagramGroup(String diagName, List<String> actorsList,
                                                   List<String> messageList){
        Group root = new Group();

        // todo set good true or false param
        UMLSeqDiaGui seqDia = new UMLSeqDiaGui(actorsList, messageList, diagName, false);
        root.getChildren().add(seqDia);

        return root;
    }

    /**
     * actorList = name_met1 -> class2 -> name_met2 -> class2 ...
     * @param actors JSONArray that has all the sequence diagram actors
     * @return  List of sequence diagram actors
     */
    private List<String> getSeqActors(JSONArray actors){
        // actorList = name_met1 -> class2 -> name_met2 -> class2
        List<String> actorsList = new ArrayList<String>();
        for (Object ac : actors){
            JSONObject oneActor = (JSONObject) ac;
            String nameMet      = (String) oneActor.get("actorName");
            String nameClass    = (String) oneActor.get("class");
            actorsList.add(nameMet);
            actorsList.add(nameClass);
        }
        return  actorsList;
    }

    /**
     * messageList = name_met1 -> class2 -> name_met2 -> class2 ...
     * @param messages JSONArray that has all the sequence diagram messages
     * @return List of sequence diagram messages
     */
    private List<String> getSeqMethods(JSONArray messages){

        // messageList = from1 -> to1 -> type1 -> from2 -> to2 -> type2
        List<String> messageList = new ArrayList<String>();
        for (Object me : messages){
            JSONObject oneMessage = (JSONObject) me;
            String from         = (String) oneMessage.get("from");
            String to           = (String) oneMessage.get("to");
            String type         = (String) oneMessage.get("type");
            messageList.add(from);
            messageList.add(to);
            messageList.add(type);
        }
        return messageList;
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

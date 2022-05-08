/*
 * File:         DiagramSaver.java
 * Institution:  FIT BUT 2021/2022
 * Course:       IJA - Java Programming Language
 * Authors:      Lucie Svobodová, xsvobo1x@stud.fit.vutbr.cz
 *               Jakub Kuzník, xkuzni04@stud.fit.vutbr.cz
 *
 * Main backend file that is used for saving the diagram to the JSON file.
 */
package app.backend;

import app.backend.uml.*;
import app.gui.umlGui.UMLActorGui;
import app.gui.umlGui.UMLMessageGui;
import app.gui.umlGui.UMLSeqDiaGui;
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
     * Method saves the diagrams in a JSON format to a file.
     *
     * @param diagrams backend diagrams
     * @param seqDiagramsGui sequence diagrams loaded in GUI
     * @param filename name of the file in that the diagram will be saved to
     */
    public static void saveJSON(Diagrams diagrams, List<UMLSeqDiaGui> seqDiagramsGui, String filename) {
        // create JSON array
        JSONObject diagram = new JSONObject();

        // check null
        JSONArray classes = saveClasses(diagrams.getClassDiagram());
        diagram.put("classes", classes);

        JSONArray interfaces = saveInterfaces(diagrams.getClassDiagram());
        diagram.put("interfaces", interfaces);

        JSONArray relationships = saveRelationships(diagrams.getClassDiagram());
        diagram.put("relationships", relationships);

        JSONArray sequenceDiagrams = saveSeqDia(seqDiagramsGui);
        diagram.put("sequenceDiagrams", sequenceDiagrams);

        // Write JSON file
        try (FileWriter file = new FileWriter(filename)) { // saves the file to dest/
            file.write(diagram.toJSONString());
            file.flush();
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
     * Save sequence diagram
     * @param seq list with all the sequence diagrams
     */
    public static JSONArray saveSeqDia(List<UMLSeqDiaGui> seq){
        JSONArray seqDiagrams = new JSONArray();
        // for each sequence diagram
        for (UMLSeqDiaGui seqDiaGui : seq){
            JSONObject oneDiagram = new JSONObject();
            // save name of the sequence diagram
            oneDiagram.put("name", seqDiaGui.getName());
            // save actors
            oneDiagram.put("actors", saveActors(seqDiaGui));
            // save messages
            oneDiagram.put("messages", saveMessages(seqDiaGui));

            // add one diagram to the array of sequence diagrams
            seqDiagrams.add(oneDiagram);
        }
        return seqDiagrams;
    }

    /**
     *
     * @param seqDiaGui sequence diagram
     * @return JSON array
     */
    public static JSONArray saveMessages(UMLSeqDiaGui seqDiaGui){
        JSONArray messages = new JSONArray();

        for (UMLMessageGui mes : seqDiaGui.getMessageGui()){
            JSONObject oneMessage = new JSONObject();
            /*
                    "classFrom" : "ClassDiagram",
                    "classTo" : "UMLClass",
                    "from" : "Main",
                    "to" : "ClassDiagram",
                    "type" : "synch",
                    "methodName" : "createClass"

             */
            oneMessage.put("classFrom", mes.getMessage().getFromClass().getName());
            oneMessage.put("classTo", mes.getMessage().getToClass().getName());
            oneMessage.put("from", mes.getMessage().getFromActor());
            oneMessage.put("to", mes.getMessage().getToActor());
            oneMessage.put("type", mes.getMessage().getType());

            // methodName
            String methodName;
            if (mes.getMessage().getMethod() == null){
                methodName = "";
            }
            else {
                methodName = mes.getMessage().getMethod().getName();
            }
            oneMessage.put("methodName", methodName);

            // add one message to the array of all messages
            messages.add(oneMessage);
        }
        return messages;
    }


    /**
     * Method saves the actors from one sequence diagram.
     * @param seqDiaGui sequence diagram
     * @return JSON array
     */
    public static JSONArray saveActors(UMLSeqDiaGui seqDiaGui){
        JSONArray actors = new JSONArray();

        int i = 0;
        List<Boolean> createdByMessage =  seqDiaGui.getSeqDiagram().getActorsCreatedByMessage();
        for (UMLActorGui act : seqDiaGui.getActorsGui()){
            JSONObject oneActor = new JSONObject();

            // TODO check!!!
            boolean oneCreated;
            String oneCreatedStr;
            if (createdByMessage.size() > i){
                oneCreated = createdByMessage.get(i);
                oneCreatedStr = String.valueOf(oneCreated);

            }
            else{
                oneCreatedStr = "false";
            }

            /*
            {
              "actorName" : "c1",
              "class" : "UMLClass",
              "createdByMessage": "true"
            }
             */
            oneActor.put("actorName", act.getActorName());
            oneActor.put("class", act.getClassName());
            oneActor.put("createdByMessage", oneCreatedStr);
            i++;

            // add one actor to the array of actors
            actors.add(oneActor);
        }
        return actors;
    }



    /**
     * Method saves the UML class diagram in a JSON format to a file.
     *
     * @param classDiagram contains the class diagram
     */
    public static JSONArray saveClasses(ClassDiagram classDiagram) {
        if (classDiagram == null) {
            return null;
        }
        // save classes
        JSONArray classes = new JSONArray();
        for (UMLClass cls : classDiagram.getClasses()) {
            classes.add(saveClass(cls));
        }
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

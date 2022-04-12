package app.umlGui;

import javafx.event.ActionEvent;
import javafx.scene.Group;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class DiagramSaver {

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

            JSONArray methodAttributes = saveMethodAttributes(meth);
            oneMeth.put("attributes", methodAttributes);

            methods.add(oneMeth);
        }

        return methods;
    }

    private static JSONObject saveClass(UMLClassGui umlClass) {
        System.out.println("SAVING CLASS");
        JSONObject oneClass = new JSONObject();
        oneClass.put("name", umlClass.getName());

        JSONArray attributes = saveAttributes(umlClass);
        oneClass.put("attributes", attributes);

        JSONArray methods = saveMethods(umlClass);
        oneClass.put("methods", methods);
        return oneClass;
    }

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
        try (FileWriter file = new FileWriter("/home/lucos/fit/ija/IJA-project/savedDiagram.json")) {
        //try (FileWriter file = new FileWriter("savedDiagram.json")) { // saves to dest/
            file.write(classes_interfaces_relationships.toJSONString());
            file.flush();
            System.out.println("Diagram saved.\n");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

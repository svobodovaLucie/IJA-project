

package app.helpers;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;

public class JsonLoader {
    public static void saveMethodAttributes (JSONArray methodAttributes) {
        for (Object methodAttribute : methodAttributes) {
            JSONObject methodAttr = (JSONObject) methodAttribute;

            String methodAttrType = (String) methodAttr.get("type");
            System.out.println("Method attribute type: " + methodAttrType);
        }
    }

    public static void saveMethods (JSONArray methods) {
        for (Object methodObj : methods) {
            JSONObject method = (JSONObject) methodObj;

            String methodName = (String) method.get("name");
            System.out.println("Method name: " + methodName);

            String methodType = (String) method.get("type");
            System.out.println("Method type: " + methodType);

            String methodAccess = (String) method.get("access");
            System.out.println("Method access: " + methodAccess);

            // get method attributes
            saveMethodAttributes((JSONArray)method.get("attributes"));
        }
    }

    public static void saveAttributes(JSONArray attributes) {
        for (Object attribute : attributes) {
            JSONObject attr = (JSONObject) attribute;

            String attrName = (String) attr.get("name");
            System.out.println("Attribute name: " + attrName);

            String attrType = (String) attr.get("type");
            System.out.println("Attribute type: " + attrType);

            String attrAccess = (String) attr.get("access");
            System.out.println("Attribute access: " + attrAccess);
        }
    }

    public static void saveClasses(JSONArray classes) {
        for (Object aClass : classes) {
            JSONObject classObj = (JSONObject) aClass;

            String className = (String)classObj.get("name");
            System.out.println("Class name: " + className);

            saveAttributes((JSONArray)classObj.get("attributes"));
            saveMethods((JSONArray)classObj.get("methods"));
        }
    }

    public static void loadJson(String filename) throws IOException, ParseException {
        // load the JSON file
        JSONParser jsonParser = new JSONParser();

        // load everything to diagram object
        Object input = jsonParser.parse(new FileReader(filename));
        JSONObject diagram = (JSONObject)input;

        // save all parameters loaded from the input file
        saveClasses((JSONArray)diagram.get("classes"));
    }
}

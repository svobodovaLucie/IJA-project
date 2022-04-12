/*
 * File:         Controller.java
 * Institution:  FIT BUT 2021/2022
 * Course:       IJA - Java Programming Language
 * Authors:      Lucie Svobodová, xsvobo1x@stud.fit.vutbr.cz
 *               Jakub Kuzník, xkuzni04@stud.fit.vutbr.cz
 *
 * GUI main scene controller.
 */
package app.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;

/**
 * Class represents the GUI main scene controller.
 */
public class Controller {
    @FXML
    TextField nameTextField;

    @FXML
    private VBox class_vbox;

    private static Stage stage;
    private static Scene scene;
    private static Parent root;

    DraggableObject draggableObject = new DraggableObject();

    /**
     * Method is run on event and it changes the scene to scene1
     * and displays a name given in the GUI textField.
     *
     * @param event event on that the scene is changed
     * @throws IOException
     */
    public void login(ActionEvent event) throws IOException {
        // get the string from the GUI textField
        String username = nameTextField.getText();

        // set the new scene - scene1
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Scene1.fxml"));
        root = loader.load();

        Scene1Controller scene1Controller = loader.getController();
        scene1Controller.displayName(username);

        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void makeDraggable(ActionEvent event) {
        draggableObject.makeDraggable(class_vbox);
    }

    public static void addClass(ActionEvent event, Stage stage) {

        //stage = (Stage) ((Node)event.getSource()).getScene().getWindow();

        //root.getChildrenUnmodifiable().add(new Text("ahoj"));
        scene = new Scene(root, 500, 500, Color.LIGHTCYAN);
        stage.setScene(scene);
        stage.show();
    }

    public static void saveJSON(ActionEvent event) {
        // class attribute
        JSONObject oneAttr = new JSONObject();
        oneAttr.put("name", "pocatek");
        oneAttr.put("type", "Point");
        oneAttr.put("access", "private");

        JSONArray attributes = new JSONArray();
        attributes.add(oneAttr);
        // add second attribute etc.

        // class method attributes
        JSONObject oneMethAttr = new JSONObject();
        oneMethAttr.put("type", "int");

        JSONArray methodAttributes = new JSONArray();
        methodAttributes.add(oneMethAttr);

        // class method
        JSONObject oneMeth = new JSONObject();
        oneMeth.put("name", "kreslit");
        oneMeth.put("type", "void");
        oneMeth.put("access", "public");
        oneMeth.put("attributes", methodAttributes);

        JSONArray methods = new JSONArray();
        methods.add(oneMeth);
        // add second attribute etc.

        JSONObject oneClass = new JSONObject();
        oneClass.put("name", "Tvar");
        oneClass.put("attributes", attributes);
        oneClass.put("methods", methods);

        JSONArray classes = new JSONArray();
        classes.add(oneClass);

        JSONObject classes_interfaces_relationships = new JSONObject();
        classes_interfaces_relationships.put("classes", classes);

        //Write JSON file
        try (FileWriter file = new FileWriter("/home/lucos/fit/ija/IJA-project/savedDiagram.json")) {
            //We can write any JSONArray or JSONObject instance to the file
            //file.write(employeeList.toJSONString());
            System.out.println("Diagram saved.\n");
            file.write(classes_interfaces_relationships.toJSONString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }

        /*
        JSONObject employeeObject = new JSONObject();
        employeeObject.put("employee", employeeDetails);

        //Second Employee
        JSONObject employeeDetails2 = new JSONObject();
        employeeDetails2.put("firstName", "Brian");
        employeeDetails2.put("lastName", "Schultz");
        employeeDetails2.put("website", "example.com");

        JSONObject employeeObject2 = new JSONObject();
        employeeObject2.put("employee", employeeDetails2);

        //Add employees to list
        JSONArray employeeList = new JSONArray();
        employeeList.add(employeeObject);
        employeeList.add(employeeObject2);

        //Write JSON file
        try (FileWriter file = new FileWriter("employees.json")) {
            //We can write any JSONArray or JSONObject instance to the file
            file.write(employeeList.toJSONString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
        */
    }
}

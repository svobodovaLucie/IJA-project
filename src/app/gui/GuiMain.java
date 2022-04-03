/*
 * IJA 2021/22: Demonstrační příklad pro seminář IJA.
 * Ukázka základního GUI v JavaFX včetně zpracování událostí a filtrů.
 * (C) Radek Kočí
 */
package app.gui;

import app.backend.MainApplication;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.scene.Scene;
import org.json.simple.parser.ParseException;

import java.io.IOException;

/**
 * Ukázka základního GUI v JavaFX včetně zpracování událostí a filtrů.
 * @author Radek Kočí
 */
public class GuiMain extends Application {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, ParseException {
        System.out.println(args[0]);
        // read data and save
        // new Diagrams = blah nacist diagramy
        // new Class Diagram = nacist class diagram


        //JsonLoader.loadJson(args[0]);

        // run the backend
        MainApplication backend = new MainApplication();
        backend.main(args[0]);
        
        System.out.println("POHODKA BACKEND ROZJETY");

        // run GUI
        launch(args);
    }

    // JavaFX Application Thread
    @Override
    public void start(Stage primaryStage) throws Exception {
        // set FXML loader
        FXMLLoader loader = new FXMLLoader();
        System.out.println("UNO");

        loader.setLocation(getClass().getResource("/AppGuiMain.fxml"));
        Parent root = loader.load();

        // set the scene
        Scene scene = new Scene(root);
        System.out.println("DEUX");
        scene.getStylesheets().add("stylesheet.css");

        // set and show the stage
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}

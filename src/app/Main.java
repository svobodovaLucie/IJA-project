/*
 * File:         Main.java
 * Institution:  FIT BUT 2021/2022
 * Course:       IJA - Java Programming Language
 * Authors:      Lucie Svobodová, xsvobo1x@stud.fit.vutbr.cz
 *               Jakub Kuzník, xkuzni04@stud.fit.vutbr.cz
 *
 * Main project file.
 */
package app;

import app.backend.Diagrams;
import app.gui.GuiMain;
import app.uml.ClassDiagram;
import app.umlGui.DiagramLoader;
import app.umlGui.DiagramLoaderNoGui;
import javafx.scene.Group;
import org.json.simple.parser.ParseException;
import java.io.IOException;

/**
 * Main class for the application run.
 */
public class Main {
    public static Diagrams diagrams;

    /**
     * Main method.
     *
     * @param args
     * @throws IOException
     * @throws ParseException
     */
    public static void main(String[] args) throws IOException, ParseException {
        // load JSON to BE
        DiagramLoaderNoGui jsonLoader = new DiagramLoaderNoGui();
        diagrams = jsonLoader.loadDiagrams(args[0]);   // TODO fix if we don't want to load a diagram from a file

        // load GUI from BE
        GuiMain.main(args, diagrams);
    }
}

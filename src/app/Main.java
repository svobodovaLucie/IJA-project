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
import app.backend.DiagramLoader;
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
     * @param args command line arguments
     * @throws IOException
     * @throws ParseException
     */
    public static void main(String[] args) throws IOException, ParseException {
        // load JSON to BE
        DiagramLoader jsonLoader = new DiagramLoader();
        diagrams = jsonLoader.loadDiagrams(args[0]);

        // load GUI from BE
        GuiMain.main(args, diagrams);
    }
}

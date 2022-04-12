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

import app.gui.GuiMain;
import org.json.simple.parser.ParseException;
import java.io.IOException;

/**
 * Main class for the application run.
 */
public class Main {
    /**
     * Main method.
     *
     * @param args
     * @throws IOException
     * @throws ParseException
     */
    public static void main(String[] args) throws IOException, ParseException {
        GuiMain.main(args);
    }
}

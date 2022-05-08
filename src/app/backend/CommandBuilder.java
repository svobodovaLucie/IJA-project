/*
 * File:         CommandBuilder.java
 * Institution:  FIT BUT 2021/2022
 * Course:       IJA - Java Programming Language
 * Authors:      Lucie Svobodová, xsvobo1x@stud.fit.vutbr.cz
 *               Jakub Kuzník, xkuzni04@stud.fit.vutbr.cz
 *
 * File contains the implementation of CommandBuilder (design
 * pattern: Command). Undo operation can be realized with this pattern.
 */

package app.backend;

import java.util.ArrayList;
import java.util.List;

/**
 * Class represents the design pattern Command.
 * @author Radek Kočí. Ph.D., koci@fit.vut.cz
 */
public class CommandBuilder {
    public static interface Command {
        public void execute();
        public void undo();
    }

    public static class Invoker {
        List<Command> commands = new ArrayList<>();

        /**
         * Method executes a command.
         *
         * @param cmd command to be executed
         */
        public void execute(Command cmd) {
            commands.add(0, cmd);
            cmd.execute();
        }

        /**
         * Method realizes undo command.
         */
        public void undo() {
            if (commands.size() > 0) {
                Command cmd = commands.remove(0);
                cmd.undo();
            }
        }
    }
}
/*
 * File:         CommandBuilder.java
 * Institution:  FIT BUT 2021/2022
 * Course:       IJA - Java Programming Language
 * Authors:      Lucie Svobodová, xsvobo1x@stud.fit.vutbr.cz
 *               Jakub Kuzník, xkuzni04@stud.fit.vutbr.cz
 *
 * TODO
 */

package app.backend;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author koci
 */
public class CommandBuilder {
    public static interface Command {
        public void execute();
        public void undo();
    }

    public static class Invoker {
        List<Command> commands = new ArrayList<>();

        public void execute(Command cmd) {
            commands.add(0, cmd);
            cmd.execute();
        }

        public void undo() {
            if (commands.size() > 0) {
                Command cmd = commands.remove(0);
                cmd.undo();
            }
        }
    }
}
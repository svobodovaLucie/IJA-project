// TODO xplagiat

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
package org.isec.pa.ecossistema.model.command;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.Deque;

public class CommandManager implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private Deque<ICommand> history; //private Stack<ICommand> history;
    private Deque<ICommand> redoCmds; //private Stack<ICommand> redoCmds;

    public CommandManager() {
        history = new ArrayDeque<>(); //history = new Stack<>();
        redoCmds = new ArrayDeque<>(); //redoCmds = new Stack<>();
    }

    public boolean invokeCommand(ICommand cmd) {
        if (!redoCmds.isEmpty())
            redoCmds.clear();
        if (cmd.execute()) {
            history.push(cmd);
            return true;
        }
        if (!history.isEmpty())
            history.clear();
        return false;
    }

    public boolean undo() {
        if (history.isEmpty())
            return false;
        ICommand cmd = history.pop();
        cmd.undo();
        redoCmds.push(cmd);
        return true;
    }

    public boolean redo() {
        if (redoCmds.isEmpty())
            return false;
        ICommand cmd = redoCmds.pop();
        cmd.execute();
        history.push(cmd);
        return true;
    }

    public boolean hasUndo() {
        return history.size() > 0;
    }

    public boolean hasRedo() {
        return redoCmds.size() > 0;
    }
}

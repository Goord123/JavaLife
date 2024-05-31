package org.isec.pa.ecossistema.model.command;

public interface ICommand {
    boolean execute();
    boolean undo();
}

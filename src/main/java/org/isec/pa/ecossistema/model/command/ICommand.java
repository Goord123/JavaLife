package org.isec.pa.ecossistema.model.command;

import java.io.Serializable;

public interface ICommand extends Serializable {
    boolean execute();

    boolean undo();
}

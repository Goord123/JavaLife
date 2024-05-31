package org.isec.pa.ecossistema.model.command;

import org.isec.pa.ecossistema.model.data.Ecossistema;

abstract class AbstractCommand implements ICommand{
    protected Ecossistema ecossistema;
    protected AbstractCommand(Ecossistema ecossistema) {
        this.ecossistema = ecossistema;
    }
}

package org.isec.pa.ecossistema.model.data;

public sealed interface IElementoComForca permits Fauna, Flora {

    double getForca();
    void setForca(double forca);
    void evolve();
}

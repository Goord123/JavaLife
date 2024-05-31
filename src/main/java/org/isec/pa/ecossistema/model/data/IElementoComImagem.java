package org.isec.pa.ecossistema.model.data;

public sealed interface IElementoComImagem permits Fauna, Flora {
    String getImagem(); // path da imagem

    void setImagem(String imagem);
}

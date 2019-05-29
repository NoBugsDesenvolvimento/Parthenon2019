package com.nobugs.parthenon.model.Atividades;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class AtividadeCategoria extends RealmObject {

    @PrimaryKey
    private String nome;
    private String cor;
    //private Imagem imagem;

}

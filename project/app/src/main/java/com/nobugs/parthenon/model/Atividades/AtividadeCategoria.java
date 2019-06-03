package com.nobugs.parthenon.model.Atividades;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class AtividadeCategoria extends RealmObject {

    @PrimaryKey
    private String nome;
    private String cor;
    private String icone;

    /* Constructors */
    public AtividadeCategoria(){ }

    public AtividadeCategoria(String nome, String cor, String icone) {
        this.nome = nome;
        this.cor = cor;
        this.icone = icone;
    }

    /* Getters */
    public String getNome() {
        return nome;
    }

    public String getCor() {
        return cor;
    }

    public String getIcone() {
        return icone;
    }

    /* Setters */
    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public void setIcone(String icone) {
        this.icone = icone;
    }

}

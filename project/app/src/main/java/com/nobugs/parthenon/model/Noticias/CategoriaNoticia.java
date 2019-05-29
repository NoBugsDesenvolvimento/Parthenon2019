package com.nobugs.parthenon.model.Noticias;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class CategoriaNoticia extends RealmObject {

    @PrimaryKey
    private String nome;
    @Required
    private String cor;
    @Required
    private String icone;

    /* Constructor */
    public CategoriaNoticia(){ }

    public CategoriaNoticia(String nome, String cor, String icone) {
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

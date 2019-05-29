package com.nobugs.parthenon.model.Perguntas;


import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class PerguntaGeral extends RealmObject {

    @PrimaryKey
    private int id;
    /* Tem que gerar o id auto incremente socorro */
    private Pergunta perg;
    private int utilidade;

    public PerguntaGeral(){

    }

    public PerguntaGeral(String pergunta, String hora_p, String data_p) {
        perg = new Pergunta(pergunta, hora_p, data_p);
    }

    public PerguntaGeral(String pergunta, Date hora_perg, Date data_perg) {
        perg = new Pergunta(pergunta, hora_perg, data_perg);
    }

    public int getUtilidade() {
        return utilidade;
    }

    public void setUtilidade(int utilidade) {
        this.utilidade = utilidade;
    }
}
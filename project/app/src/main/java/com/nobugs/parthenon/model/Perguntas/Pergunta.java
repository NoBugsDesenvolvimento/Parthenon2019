package com.nobugs.parthenon.model.Perguntas;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class Pergunta extends RealmObject {

    @PrimaryKey
    private int id;
    @Required
    private String pergunta;
    private String resposta;
    @Required
    private Date hora_perg;
    private Date hora_resp;
    @Required
    private Date data_perg;
    private Date data_resp;

    /* Constructor */
    public Pergunta() {
    }

    public Pergunta(String pergunta, String hora_p, String data_p) {
        this.pergunta = pergunta;

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        try {
            this.data_perg = formatter.parse(data_p);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        formatter = new SimpleDateFormat("hh:mm");
        try {
            this.hora_perg = formatter.parse(hora_p);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public Pergunta(String pergunta, Date hora_perg, Date data_perg) {
        this.pergunta = pergunta;
        this.hora_perg = hora_perg;
        this.data_perg = data_perg;
    }

    /* Getters */
    public String getPergunta() {
        return pergunta;
    }

    public String getResposta() {
        return resposta;
    }

    public Date getHoraP() {
        return hora_perg;
    }

    public Date getHoraR() {
        return hora_resp;
    }

    public Date getDataP() {
        return data_perg;
    }

    public Date getDataR() {
        return data_resp;
    }

    /* Setters */
    public void setPergunta(String pergunta) {
        this.pergunta = pergunta;
    }

    public void setResposta(String resposta) {
        this.resposta = resposta;
    }

    public void setHoraP(Date hora_p) {
        this.hora_perg = hora_p;
    }

    public void setHoraP(String hora_p) {
        SimpleDateFormat formatter = new SimpleDateFormat("hh:mm");
        try {
            this.hora_perg = formatter.parse(hora_p);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void setHoraR(Date hora_r) {
        this.hora_resp = hora_r;
    }

    public void setHoraR(String hora_r) {
        SimpleDateFormat formatter = new SimpleDateFormat("hh:mm");
        try {
            this.hora_resp = formatter.parse(hora_r);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void setDataP(Date data_p) {
        this.data_perg = data_p;
    }

    public void setDataP(String data_p) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        try {
            this.data_perg = formatter.parse(data_p);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void setDataR(Date data_r) {
        this.data_resp = data_r;
    }

    public void setDataR(String data_r) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        try {
            this.data_resp = formatter.parse(data_r);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}

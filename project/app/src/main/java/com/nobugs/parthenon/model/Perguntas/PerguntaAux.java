package com.nobugs.parthenon.model.Perguntas;

import com.google.firebase.database.DatabaseReference;
import com.nobugs.parthenon.helper.ConfiguracaoFirebase;

public class PerguntaAux {

    private String pergunta;
    private String resposta;
    private String email;
    private String titulo;
    private String respondida;



    public String getRespondida() {
        return respondida;
    }

    public void setRespondida(String respondida) {
        this.respondida = respondida;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String CPF) {
        this.email = CPF;
    }

    private String hora_perg;
    private String hora_resp;

    private String data_perg;
    private String data_resp;

    /* Constructor */

    public PerguntaAux() {
    }

    /* Getters */

    public String getPergunta() {
        return pergunta;
    }

    public String getResposta() {
        return resposta;
    }

    public String getHora_perg() {
        return hora_perg;
    }

    public String getHora_resp() {
        return hora_resp;
    }

    public String getData_perg() {
        return data_perg;
    }

    public String getData_resp() {
        return data_resp;
    }

    /* Setters */

    public void setPergunta(String pergunta) {
        this.pergunta = pergunta;
    }

    public void setResposta(String resposta) {
        this.resposta = resposta;
    }

    public void setHora_perg(String hora_perg) {
        this.hora_perg = hora_perg;
    }

    public void setHora_resp(String hora_resp) {
        this.hora_resp = hora_resp;
    }

    public void setData_perg(String data_perg) {
        this.data_perg = data_perg;
    }

    public void setData_resp(String data_resp) {
        this.data_resp = data_resp;
    }
}

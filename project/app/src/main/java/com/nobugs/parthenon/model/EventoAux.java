package com.nobugs.parthenon.model;

public class EventoAux {

    private String nome;
    private String organizacao;
    private String data;
    private String local;
    private String info;

    public EventoAux(){
    }

    public EventoAux(String nome, String organizacao, String data, String local, String info) {
        this.nome = nome;
        this.organizacao = organizacao;
        this.data = data;
        this.local = local;
        this.info = info;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getOrganizacao() {
        return organizacao;
    }

    public void setOrganizacao(String organizacao) {
        this.organizacao = organizacao;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}

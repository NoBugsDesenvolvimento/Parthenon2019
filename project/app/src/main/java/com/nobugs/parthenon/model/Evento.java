package com.nobugs.parthenon.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Evento extends RealmObject {

    @PrimaryKey
    private String key;
    private String nome;
    private String organizacao;
    private String data;
    private String local;
    private String info;

    public Evento(){
    }

    public Evento(EventoAux evt, String key) {
        this.key = key;
        this.nome = evt.getNome();
        this.organizacao = evt.getOrganizacao();
        this.data = evt.getData();
        this.local = evt.getLocal();
        this.info = evt.getInfo();
    }

    public Evento(String nome, String organizacao, String data, String local, String info, String key) {
        this.key = key;
        this.nome = nome;
        this.organizacao = organizacao;
        this.data = data;
        this.local = local;
        this.info = info;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
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

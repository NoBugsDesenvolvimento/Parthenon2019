package com.nobugs.parthenon.model.Atividades;

import com.google.android.gms.maps.model.LatLng;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class Atividade extends RealmObject {

    /* Attributes */
    @PrimaryKey
    private String key;

    private String titulo;
    private String sumario;

    private int tipo;
    private String local;
    private String latlng;
    private String data;
    private String hora_inicial;
    private String hora_final;

    private int vagas;
    private String categoria;

    /* Constructor */

    public Atividade(AtividadesAux atv, String key){
        this.titulo = atv.getTitulo();
        this.sumario = atv.getSumario();
        this.tipo = atv.getTipo();
        this.local = atv.getLocal();
        this.latlng = atv.getLatlng();
        this.data = atv.getData();
        this.hora_inicial = atv.getHora_inicial();
        this.hora_final = atv.getHora_final();
        this.vagas = atv.getVagas();
        this.categoria = atv.getCategoria();
        this.key = key;
    }

    public Atividade(){ }

    public Atividade(String titulo, String sumario, int tipo, String local, String data, String hora_inicial, String hora_final, int vagas, String categoria, String latlng) {

        this.titulo = titulo;
        this.sumario = sumario;
        this.tipo = tipo;
        this.latlng = latlng;

        this.data = data;

        if (local != null)
            this.local = local;

        this.hora_inicial = hora_inicial;
        this.hora_final = hora_final;

        this.vagas = vagas;

        this.categoria = categoria;
    }


    /* Getters */
    public String getTitulo() {
        return titulo;
    }

    public String getSumario() {
        return sumario;
    }

    public int getTipo() {
        return tipo;
    }

    public String getLocal() {
        return local;
    }

    public String getData() {
        return data;
    }

    public String getHora_inicial() {
        return hora_inicial;
    }

    public String getHora_final() {
        return hora_final;
    }

    public int getVagas() {
        return vagas;
    }

    public String getLatlng() {
        return latlng;
    }

    public String getCategoria() {
        return categoria;
    }

    public String getKey() {
        return key;
    }

    /* Setters */
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setSumario(String sumario) {
        this.sumario = sumario;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setHora_inicial(String hora_inicial) {
        this.hora_inicial = hora_inicial;
    }

    public void setHora_final(String hora_final) {
        this.hora_final = hora_final;
    }

    public void setVagas(int vagas) {
        this.vagas = vagas;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public void setLatlng(String latlng) {
        this.latlng = latlng;
    }

    public void setKey(String key) {
        this.key = key;
    }
}

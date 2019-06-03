package com.nobugs.parthenon.model.Atividades;

import com.google.android.gms.maps.model.LatLng;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class Atividade extends RealmObject {

    /* Attributes */
    @PrimaryKey
    private String titulo;
    @Required
    private String sumario;

    private int tipo;
    private String local;
    private String latlng;
    @Required
    private Date data;
    @Required
    private Date hora_inicial;
    private Date hora_final;

    private int vagas;
    private String categoria;
    private AtividadeCategoria categoria2;

    /* Constructor */
    public Atividade(){ }

    public Atividade(String titulo, String sumario, int tipo, String local, String data, String hora_inicial, String hora_final, int vagas, String categoria, String latlng) {

        this.titulo = titulo;
        this.sumario = sumario;
        this.tipo = tipo;
        this.latlng = latlng;
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        try {
            this.data = formatter.parse(data);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (local != null)
            this.local = local;

        formatter = new SimpleDateFormat("hh:mm");
        try {
            this.hora_inicial = formatter.parse(hora_inicial);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (hora_final != null) {
            try {
                this.hora_final = formatter.parse(hora_final);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        this.vagas = vagas;

        this.categoria = categoria;
        /*tenho que procurar a categoria aqui, kk jota*/
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

    public Date getData() {
        return data;
    }

    public Date getHoraI() {
        return hora_inicial;
    }

    public Date getHoraF() {
        return hora_final;
    }

    public int getVagas() {
        return vagas;
    }

    public AtividadeCategoria getCategoria() {
        return categoria2;
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

    public void setData(Date data) {
        this.data = data;
    }

    public void setData(String data) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        try {
            setData(formatter.parse(data));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void setHoraI(Date hora_i) {
        this.hora_inicial = hora_i;
    }

    public void setHoraI(String hora_i) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        try {
            setHoraI(formatter.parse(hora_i));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void setHoraF(Date hora_f) {
        this.hora_final = hora_f;
    }

    public void setHoraF(String hora_f) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        try {
            setHoraF(formatter.parse(hora_f));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void setVagas(int vagas) {
        this.vagas = vagas;
    }

    public void setCategoria(AtividadeCategoria categoria) {
        this.categoria2 = categoria;
        /*tenho que procurar a categoria aqui, kk jota*/
    }
}

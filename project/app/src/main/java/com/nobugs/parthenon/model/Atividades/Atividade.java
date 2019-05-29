package com.nobugs.parthenon.model.Atividades;

import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class Atividade extends RealmObject {

    @PrimaryKey
    private String titulo;

    public Atividade(String titulo, String sumario, int tipo, String local, String data, String hora_i, String hora_f, int vagas) {

        this.titulo = titulo;
        this.sumario = sumario;
        this.tipo = tipo;

        SimpleDateFormat formatter =new SimpleDateFormat("dd/MM/yy");

        this.local = local;
        /*this.data = formatter.parse(data);
        this.hora_inicial = hora_i;
        this.hora_final = hora_f;*/
        this.vagas = vagas;
    }

    @Required
    private String sumario;
    @Required
    private int tipo;

    private String local;
    //private PointMarker location;

    @Required
    private Date data;
    @Required
    private Time hora_inicial;
    private Time hora_final;

    @Required
    private int vagas;
}

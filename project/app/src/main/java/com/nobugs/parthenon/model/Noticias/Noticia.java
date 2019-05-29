package com.nobugs.parthenon.model.Noticias;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class Noticia extends RealmObject {

    @PrimaryKey
    private String id;
    @Required
    private String titulo;
    @Required
    private String conteudo;
    @Required
    private Date data;
    @Required
    private Date hora;
    private CategoriaNoticia categoria;

    /* Constructor */
    public Noticia() {
    }

    public Noticia(String id, String titulo, String conteudo, String data, String hora, String categoria) {
        this.id = id;
        this.titulo = titulo;
        this.conteudo = conteudo;

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        try {
            this.data = formatter.parse(data);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        formatter = new SimpleDateFormat("hh:mm");
        try {
            this.hora = formatter.parse(hora);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        /*tenho que procurar a categoria aqui, kk jota*/
    }

    /* Getter */
    public String getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getConteudo() {
        return conteudo;
    }

    public CategoriaNoticia getCategoria() {
        return categoria;
    }

    public Date getData() {
        return data;
    }

    public Date getHora() {
        return hora;
    }

    /* Setter */
    public void setId(String id) {
        this.id = id;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public void setCategoria(CategoriaNoticia categoria) {
        this.categoria = categoria;
        /*tenho que procurar a categoria aqui, kk jota*/
    }

    public void setData(Date data) {
        this.data = data;
    }

    public void setHora(Date hora) {
        this.hora = hora;
    }

    public void setData(String data) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        try {
            setData(formatter.parse(data));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void setHora(String hora) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        try {
            setHora(formatter.parse(hora));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}

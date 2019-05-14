package com.nobugs.parthenon;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Event extends RealmObject {


    @PrimaryKey
    private int id;

    private String name;
    private String date;
    private String time;
    private int type;

    private String summary;

    private Presenter author;

    public Event(){
        id = 1;
        name = "um";
        date = getDate();
        time = "00:00";
        type = 1;
        summary = "nome";
    }
    public Event(int id, String name, String date, String time, int type, String summary, Presenter author) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = "00:00";
        this.type = type;
        this.summary = summary;
        //this.author = author;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Presenter getAuthor() {
        return author;
    }

    public void setAuthor(Presenter author) {
        this.author = author;
    }

}

package com.nobugs.parthenon.model;

import android.provider.ContactsContract;

import androidx.core.util.Pair;

import java.util.ArrayList;

import io.realm.RealmObject;

public class Palestrante extends RealmObject {

    private String name;
    private String email;
    // private ArrayList<Pair<String, String>> studies;
    //private ArrayList<Pair<String, String>> jobs;

    public Palestrante() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}

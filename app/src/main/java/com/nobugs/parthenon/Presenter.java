package com.nobugs.parthenon;

import android.provider.ContactsContract;

import androidx.core.util.Pair;

import java.util.ArrayList;

import io.realm.RealmObject;

public class Presenter extends RealmObject {

    private String name;
    private String email;
    private ArrayList<Pair<String, String>> studies;
    private ArrayList<Pair<String, String>> jobs;

    public Presenter() {
    }

    public Presenter(String name, String email,
                     ArrayList<Pair<String, String>> studies, ArrayList<Pair<String, String>> jobs) {
        this.name = name;
        this.email = email;
        this.studies = studies;
        this.jobs = jobs;
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

    public ArrayList<Pair<String, String>> getStudies() {
        return studies;
    }

    public void setStudies(ArrayList<Pair<String, String>> studies) {
        this.studies = studies;
    }

    public ArrayList<Pair<String, String>> getJobs() {
        return jobs;
    }

    public void setJobs(ArrayList<Pair<String, String>> jobs) {
        this.jobs = jobs;
    }
}

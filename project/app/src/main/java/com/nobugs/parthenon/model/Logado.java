package com.nobugs.parthenon.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Logado extends RealmObject {

    @PrimaryKey
    private String unique;
    private String key;
    private boolean admin;

    public Logado() {
        this.unique = "id";
    }

    public Logado(String key) {
        this.unique = "id";
        this.key = key;
        this.admin = false;
    }

    public String getUnique() {
        return unique;
    }

    public void setUnique(String unique) {
        this.unique = unique;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }
}

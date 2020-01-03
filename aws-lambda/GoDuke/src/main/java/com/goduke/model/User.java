package com.goduke.model;


import java.io.Serializable;

public class User implements Serializable {
    private String mail;
    private String id;
    public User() {
    }

    public User(String mail, String id) {
        this.mail = mail;
        this.id = id;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}


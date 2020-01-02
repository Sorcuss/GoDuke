package com.goduke.model;


import java.io.Serializable;

public class User implements Serializable {
    private String mail;

    public Boolean getVerified() {
        return verified;
    }

    public void setVerified(Boolean verified) {
        this.verified = verified;
    }

    private Boolean verified;

    public User(String username, Boolean verified) {
        this.mail = username;
        this.verified = verified;
    }

    public User() {
    }

    public String getUsername() {
        return mail;
    }

    public void setUsername(String username) {
        this.mail = username;
    }
}


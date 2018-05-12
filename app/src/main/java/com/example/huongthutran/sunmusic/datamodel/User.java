package com.example.huongthutran.sunmusic.datamodel;

public class User {
    String uid;
    String name;
    String password;
    String image;
    String email;
    int jurisdiction;

    public User(){

    }
    public User(String uid, String name, String password, String image, String email, int jurisdiction) {
        this.uid = uid;
        this.name = name;
        this.password = password;
        this.image = image;
        this.email = email;
        this.jurisdiction = jurisdiction;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getJurisdiction() {
        return jurisdiction;
    }

    public void setJurisdiction(int jurisdiction) {
        this.jurisdiction = jurisdiction;
    }
}

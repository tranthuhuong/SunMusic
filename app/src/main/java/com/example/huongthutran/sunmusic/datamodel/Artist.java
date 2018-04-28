package com.example.huongthutran.sunmusic.datamodel;

/**
 * Created by Huong Thu Tran on 4/26/2018.
 */

public class Artist {
    private int id;
    private String image;
    private String name;
    public Artist() {
    }

    public Artist(int id, String name, String image) {
        this.id = id;
        this.image = image;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

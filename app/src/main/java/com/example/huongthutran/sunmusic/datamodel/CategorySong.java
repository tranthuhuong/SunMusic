package com.example.huongthutran.sunmusic.datamodel;

public class CategorySong {
    private int category_id;
    private String name;
    private String image;
    private String[] song_ids;

    public CategorySong(){

    }
    public CategorySong( int category_id,String image,String name,String[] song_ids ){
        this.category_id=category_id;
        this.name=name;
        this.image=image;
        this.song_ids=song_ids;
    }
    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String[] getSong_ids() {
        return song_ids;
    }

    public void setSong_ids(String[] song_ids) {
        this.song_ids = song_ids;
    }
}

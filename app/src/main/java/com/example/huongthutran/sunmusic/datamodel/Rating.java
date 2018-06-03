package com.example.huongthutran.sunmusic.datamodel;

public class Rating {
    private String uid;
    private int start;
    private String comment;
    private String song_id;
    public Rating(){

    }

    public Rating(String uid, int start, String comment, String song_id) {
        this.uid = uid;
        this.start = start;
        this.comment = comment;
        this.song_id = song_id;
    }

    public String getSong_id() {
        return song_id;
    }

    public void setSong_id(String song_id) {
        this.song_id = song_id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}

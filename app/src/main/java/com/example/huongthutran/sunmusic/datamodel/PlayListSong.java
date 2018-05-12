package com.example.huongthutran.sunmusic.datamodel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class PlayListSong implements Serializable{
    private int playlist_id;
    private String uid;
    private String image;
    private String name_playlist;
    private List<Song> songs;

    public PlayListSong(){
        songs=new ArrayList<>();
    }
    public PlayListSong(int playlist_id,String uid,String name_playlist,String image, List<Song>songs){
        this.image=image;
        this.name_playlist=name_playlist;
        this.playlist_id=playlist_id;
        this.songs=songs;
        this.uid=uid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public int getPlaylist_id() {
        return playlist_id;
    }

    public void setPlaylist_id(int playlist_id) {
        this.playlist_id = playlist_id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName_playlist() {
        return name_playlist;
    }

    public void setName_playlist(String name_playlist) {
        this.name_playlist = name_playlist;
    }

    public List<Song> getSongs() {
        return songs;
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }
}

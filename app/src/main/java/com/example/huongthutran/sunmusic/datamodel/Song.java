package com.example.huongthutran.sunmusic.datamodel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Huong Thu Tran on 4/26/2018.
 */

public class Song {
    private String song_id;
    private String song_name;
    private String link;
    private String image;
    private String timeCreate;
    private int authorId;
    private String authorName;
    private int singgerId;
    private String singgerName;
    private String singgerImage;
    private int countReviews;
    private int amount_view;
    private float scoreReviews;
    private int countReview;
    private int countAddPlayList;
    private List<Rating> ratings;
    public Song(){
        this.countReview=0;
        this.countAddPlayList=0;
        this.scoreReviews=0;
        this.amount_view=0;
        this.ratings=new ArrayList<>();
    }
    public Song(String id, String name, String link, String image, String timeCreate, int authorId, String authorName, String singgerImage, int countReviews, float scoreReviews,int singgerId,String singgerName,int countReview,List<Rating> r) {
        this.song_id = id;
        this.song_name = name;
        this.link = link;
        this.ratings=r;
        this.image = image;
        this.timeCreate = timeCreate ;
        this.authorId = authorId;
        this.authorName = authorName;
        this.singgerId = singgerId;
        this.singgerName = authorName;
        this.singgerImage = singgerImage;
        this.countReviews = countReviews;
        this.scoreReviews = scoreReviews;
        this.countReview=countReview;

    }

    public List<Rating> getRatings() {
        return ratings;
    }

    public void setRatings(List<Rating> ratings) {
        this.ratings = ratings;
    }

    public int getAmount_view() {
        return amount_view;
    }

    public void setAmount_view(int amount_view) {
        this.amount_view = amount_view;
    }

    public int getCountAddPlayList() {
        return countAddPlayList;
    }

    public void setCountAddPlayList(int countAddPlayList) {
        this.countAddPlayList = countAddPlayList;
    }

    public int getCountReview() {
        return countReview;
    }

    public void setCountReview(int countReview) {
        this.countReview = countReview;
    }

    public String getSong_id() {
        return song_id;
    }

    public void setSong_id(String song_id) {
        this.song_id = song_id;
    }

    public String getSong_name() {
        return song_name;
    }

    public void setSong_name(String song_name) {
        this.song_name = song_name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTimeCreate() {
        return timeCreate;
    }

    public void setTimeCreate(String timeCreate) {
        this.timeCreate = timeCreate;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public int getSinggerId() {
        return singgerId;
    }

    public void setSinggerId(int singgerId) {
        this.singgerId = singgerId;
    }

    public String getSinggerName() {
        return singgerName;
    }

    public void setSinggerName(String singgerName) {
        this.singgerName = singgerName;
    }

    public String getSinggerImage() {
        return singgerImage;
    }

    public void setSinggerImage(String singgerImage) {
        this.singgerImage = singgerImage;
    }

    public int getCountReviews() {
        return countReviews;
    }

    public void setCountReviews(int countReviews) {
        this.countReviews = countReviews;
    }

    public float getScoreReviews() {
        return scoreReviews;
    }

    public void setScoreReviews(float scoreReviews) {
        this.scoreReviews = scoreReviews;
    }
}

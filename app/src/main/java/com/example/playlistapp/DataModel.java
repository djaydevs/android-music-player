package com.example.playlistapp;

public class DataModel {
    String title, artist, time;
    public DataModel (String title, String artist, String time) {
        this.title = title;
        this.artist = artist;
        this.time = time;
    }

    public String getTitle() {
        return title;
    }
    public String getArtist() {
        return artist;
    }
    public String getTime() {
        return time;
    }
}

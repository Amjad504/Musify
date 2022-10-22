package com.AmjadArshadi190504;

public class SongModel {

    String Id;
    String title;
    String  uri;
    String artist;
    String Genre;
    String album;
    String track;


    public SongModel(String Id, String title, String uri, String artist, String genre, String album, String track) {
        this.title = title;
        this.uri = uri;
        this.artist = artist;
        this.Genre = genre;
        this.album = album;
        this.track = track;
        this.Id = Id;

    }

    public SongModel() {

    }


    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getUri() {
        return uri;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getGenre() {
        return Genre;
    }

    public void setGenre(String genre) {
        Genre = genre;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getTrack() {
        return track;
    }

    public void setTrack(String track) {
        this.track = track;
    }


    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }



}

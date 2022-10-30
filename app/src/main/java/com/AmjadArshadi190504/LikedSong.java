package com.AmjadArshadi190504;

import androidx.fragment.app.FragmentActivity;

import java.util.List;

public class LikedSong {
    String Id;
    String title;
    String  uri;
    String artist;

    public LikedSong(String title, String uri, String artist,String Id) {
        this.title = title;
        this.uri = uri;
        this.artist = artist;
        this.Id = Id;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public LikedSong(List ls, FragmentActivity activity) {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }
}

package com.AmjadArshadi190504;

public class SongTrack {
    private String title;
    private String singer;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public SongTrack(String title, String singer) {
        this.title = title;
        this.singer = singer;
    }
}

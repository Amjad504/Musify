package com.AmjadArshadi190504;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;


public class MyApplication extends Application {

    List<LikedSong>  LS;


    List<SongModel> ls;

    public List<LikedSong> getLS() {
        return LS;
    }

    public void setLS(List<LikedSong> LS) {
        this.LS = LS;
    }

    public MyApplication()
    {
        ls  = new ArrayList<SongModel>();
        LS = new ArrayList<LikedSong>();
    }



    public List<SongModel> getLs() {
        return ls;
    }

    public void setLs(List<SongModel> ls) {
        this.ls = ls;
    }
}

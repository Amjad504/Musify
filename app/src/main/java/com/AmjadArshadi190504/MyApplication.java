package com.AmjadArshadi190504;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;


public class MyApplication extends Application {
    
    List<SongModel> ls;
    public MyApplication()
    {
        ls  = new ArrayList<SongModel>();
    }



    public List<SongModel> getLs() {
        return ls;
    }

    public void setLs(List<SongModel> ls) {
        this.ls = ls;
    }
}

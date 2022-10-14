package com.AmjadArshadi190504;

import android.widget.ImageView;

public class Profiledata {

    String  dp;
    String name;
    String mail;

    public Profiledata(String dp, String name, String mail) {
        this.dp = dp;
        this.name = name;
        this.mail = mail;

    }



    public Profiledata() {

    }

    public String  getDp() {
        return dp;
    }

    public void setDp(String dp) {
        this.dp = dp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }



}

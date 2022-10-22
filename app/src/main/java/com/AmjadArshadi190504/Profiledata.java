package com.AmjadArshadi190504;

import android.widget.ImageView;

public class Profiledata {

    String  dp;
    String name;
    String mail;
    String number;
    String pass;

    public Profiledata(String dp, String name, String mail,String number,String pass) {
        this.dp = dp;
        this.name = name;
        this.mail = mail;
        this.number = number;
        this.pass = pass;

    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
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

package com.AmjadArshadi190504;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

@SuppressLint("CustomSplashScreen")
public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        getSupportActionBar().hide();
        Thread thread = new Thread()
        {
            public void run()
            {
                try{
                sleep(1000);
                }
                catch(Exception e){
                    e.printStackTrace();

            }
                finally{
                    Intent intent = new Intent(SplashScreen.this , loginMail.class);
                    startActivity(intent);


            }
            }

        };thread.start();

    }
}
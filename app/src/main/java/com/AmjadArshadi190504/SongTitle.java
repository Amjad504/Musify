package com.AmjadArshadi190504;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class SongTitle extends AppCompatActivity {
    ImageView resume;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_title);
        getSupportActionBar().hide();
        resume =  (ImageView) findViewById(R.id.resume);
        resume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SongTitle.this,Song_title2.class);
                startActivity(intent);

            }
        });
    }
}
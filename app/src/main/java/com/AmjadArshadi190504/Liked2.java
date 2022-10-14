package com.AmjadArshadi190504;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Liked2 extends AppCompatActivity {
    LinearLayout add;
    TextView playlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liked2);
        getSupportActionBar().hide();
        add = (LinearLayout) findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Liked2.this,AddPlaylist.class);
                startActivity(intent);
            }
        });
        playlist = (TextView) findViewById(R.id.music);
        playlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Liked2.this,Liked.class);
                startActivity(intent);
            }
        });
    }
}
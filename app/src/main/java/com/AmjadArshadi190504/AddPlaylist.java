package com.AmjadArshadi190504;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AddPlaylist extends AppCompatActivity {

    TextView add_playlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_playlist);
        getSupportActionBar().hide();
        add_playlist = (TextView) findViewById(R.id.Add_Playlist);
        add_playlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddPlaylist.this,UploadMusic.class);
                startActivity(intent);
            }
        });
    }
}
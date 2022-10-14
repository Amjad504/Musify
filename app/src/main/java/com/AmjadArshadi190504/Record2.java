package com.AmjadArshadi190504;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class Record2 extends AppCompatActivity {

    ImageView start2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record2);
        getSupportActionBar().hide();
        start2 =  (ImageView) findViewById(R.id.start2);
        start2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Record2.this,Record3.class);
                startActivity(intent);

            }
        });
    }
}
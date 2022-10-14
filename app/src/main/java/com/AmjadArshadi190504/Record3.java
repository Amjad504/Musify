package com.AmjadArshadi190504;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class Record3 extends AppCompatActivity {
    ImageView start3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record3);
        getSupportActionBar().hide();
        start3 =  (ImageView) findViewById(R.id.start3);
        start3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Record3.this,Record4.class);
                startActivity(intent);

            }
        });
    }
}
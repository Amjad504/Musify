package com.AmjadArshadi190504;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    Home home = new Home();
    like_songs like_songs  = new like_songs();
    Add add = new Add();
    Searching searching  = new Searching();
    Listen listen  = new Listen();
    FloatingActionButton Add_Button;
    EditText Number;

    private DatabaseReference mDatabase;
// ...





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.bottom);

        Number = (EditText) findViewById(R.id.number);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        FirebaseAuth mAuth= FirebaseAuth.getInstance();

        DatabaseReference mDatabase= FirebaseDatabase.getInstance().getReference().child(mAuth.getCurrentUser().getUid());

        DatabaseReference currentuser= mDatabase.push();

        currentuser.child("name").getKey();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String sessionId = extras.getString("key");
            if (sessionId != null) {
                Number.setText(sessionId);

                System.out.println(Number);
            }
        }



        Add_Button = (FloatingActionButton) findViewById(R.id.fab);
        getSupportFragmentManager().beginTransaction().replace(R.id.container,home).commit();

        Add_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Add add = new Add();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.container,add);
                transaction.commit();
            }
        });

        bottomNavigationView.setBackground(null);
        bottomNavigationView.getMenu().getItem(2).setEnabled(false);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {


                switch (item.getItemId())
                {
                    case R.id.home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,home).commit();
                        return true;
                    case R.id.search:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,searching).commit();
                        return true;
                    case R.id.listen:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,listen).commit();
                        return true;
                    case R.id.like_:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,like_songs).commit();
                        return true;
                }


                return false;
            }
        });




    }
}
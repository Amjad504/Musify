package com.AmjadArshadi190504;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Home extends Fragment {

    ImageView imageView;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    DatabaseReference databaseReference;
    FirebaseAuth mAuth;
    String Name;
    int position;
    String uid;
    Uri uri;
    SwipeRefreshLayout swiper,swiper2;
    RecyclerView rv;

    AdapterSong adapter;

    FirebaseDatabase firebaseDatabase;
    private static final String USERS = "users";
    TextView name;
    CircleImageView profile;
    List ls;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        ls = ((MyApplication) getActivity().getApplication()).getLs();


        imageView = view.findViewById(R.id.imageView);
        drawerLayout = view.findViewById(R.id.drawer);
        navigationView = view.findViewById(R.id.nav);
        swiper2 = view.findViewById(R.id.swiper2);
        swiper = view.findViewById(R.id.swiper);
        name = navigationView.getHeaderView(0).findViewById(R.id.profilename);
        profile = navigationView.getHeaderView(0).findViewById(R.id.profilepic);


        Bundle bundle = this.getArguments();
        if (bundle != null) {
            position = bundle.getInt("pos", 0);
        }


        swiper.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                adapter.notifyDataSetChanged();
                adapter.notifyItemRemoved(position);
                swiper.setRefreshing(false);
            }
        });

        swiper2.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                adapter.notifyDataSetChanged();
                adapter.notifyItemRemoved(position);
                swiper.setRefreshing(false);
            }
        });

//
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference Ref = database.getReference("Songs").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        Ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ls.clear();
                for(DataSnapshot ds : snapshot.getChildren()) {
                    String Idd= ds.getKey();
                    String x = ds.child("title").getValue(String.class);
                    String y = ds.child("uri").getValue(String.class);
                    ls.add(new SongModel(Idd,x,y,ds.child("artist")
                            .getValue(String.class),null,ds.child("album")
                            .getValue(String.class),ds.child("track")
                            .getValue(String.class)));
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                throw error.toException();

            }
        });



        rv = view.findViewById(R.id.ARV);
        adapter = new AdapterSong(ls, getActivity());
        RecyclerView.LayoutManager lm = new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false);
        rv.setLayoutManager(lm);
        rv.setAdapter(adapter);




        mAuth = FirebaseAuth.getInstance();

//        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
//        Toast.makeText(getActivity(), "" + currentFirebaseUser.getUid(), Toast.LENGTH_SHORT).show();

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Name = name.getText().toString();

                final FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference ref = database.getReference("users");

                ref.orderByChild("number").equalTo(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot ds : snapshot.getChildren()) {
                            Name=ds.child("name").getValue().toString();
                            String link = ds.child("dp").getValue(String.class);
                            Picasso.get().load(link).into(profile);
                            name.setText(Name);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        throw error.toException();

                    }
                });



                drawerLayout.openDrawer(GravityCompat.START);


                navigationView.getMenu().getItem(2).setEnabled(false);
                navigationView.getMenu().getItem(3).setEnabled(false);
                navigationView.getMenu().getItem(4).setEnabled(false);
                navigationView.getMenu().getItem(5).setEnabled(false);

            }

        });


    }






    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        TextView name = (TextView) view.findViewById(R.id.Songname);
        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SongDisplay songDisplay = new SongDisplay();
                FragmentManager manager = getFragmentManager();
                manager.beginTransaction().replace(R.id.container,songDisplay).commit();
            }
        });

        return view;

    }

    @Override
    public void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
//        adapter.notifyItemRemoved(0);
    }
}
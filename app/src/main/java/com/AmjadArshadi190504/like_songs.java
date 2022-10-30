package com.AmjadArshadi190504;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.List;

public class like_songs extends Fragment {

    RecyclerView rv;
    LikedAdapter adapter;
    List LS;

    SwipeRefreshLayout swiper;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_like_songs, container, false);
        TextView playlist = (TextView) view.findViewById(R.id.Playlist_liked);
        swiper = view.findViewById(R.id.LikedSwiper);

//        LS = ((MyApplication) getActivity().getApplication()).getLS();
//        swiper.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                adapter.notifyDataSetChanged();
//                swiper.setRefreshing(false);
//            }
//        });

        playlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LikedPlaylist likedPlaylist = new LikedPlaylist();
                FragmentManager manager = getFragmentManager();
                manager.beginTransaction().replace(R.id.container,likedPlaylist).commit();
            }
        });


        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference Ref = database.getReference("Songs").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

//        Ref.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                LS.clear();
//                for(DataSnapshot ds : snapshot.getChildren()) {
//                    String Idd= ds.getKey();
//                    if(ds.child("Liked").getValue(String.class) == "true")
//                    {
//                        String x = ds.child("title").getValue(String.class);
//                        String z = ds.child("artist").getValue(String.class);
//                        String y = ds.child("uri").getValue(String.class);
//                        LS.add(new LikedSong(x,y,z,Idd));
//                    }
//
//
//
//                }
//            }
//
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                throw error.toException();
//
//            }
//        });

//        rv = view.findViewById(R.id.ARV);
//        adapter = new LikedAdapter(LS,getActivity());
//        RecyclerView.LayoutManager lm = new LinearLayoutManager(getActivity());
//        rv.setLayoutManager(lm);
//        rv.setAdapter(adapter);



        return view;

    }
}
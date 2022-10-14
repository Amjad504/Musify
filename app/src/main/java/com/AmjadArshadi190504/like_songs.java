package com.AmjadArshadi190504;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class like_songs extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_like_songs, container, false);
        TextView playlist = (TextView) view.findViewById(R.id.Playlist_liked);
        playlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LikedPlaylist likedPlaylist = new LikedPlaylist();
                FragmentManager manager = getFragmentManager();
                manager.beginTransaction().replace(R.id.container,likedPlaylist).commit();
            }
        });


        return view;

    }
}
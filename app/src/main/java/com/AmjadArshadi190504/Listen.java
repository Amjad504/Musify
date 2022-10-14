package com.AmjadArshadi190504;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


public class Listen extends Fragment {





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_listen, container, false);
        TextView playlist = (TextView) view.findViewById(R.id.Playlist);
        playlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Listen2 listen2 = new Listen2();
                FragmentManager manager = getFragmentManager();
                manager.beginTransaction().replace(R.id.container,listen2).commit();
            }
        });


        return view;

    }
}
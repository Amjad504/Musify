package com.AmjadArshadi190504;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


public class SongDisplay extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_song_display, container, false);
        LinearLayout fav = (LinearLayout) view.findViewById(R.id.favdown);
        ImageView resume = (ImageView) view.findViewById(R.id.play);
        ImageView pause = (ImageView) view.findViewById(R.id.pause);
        resume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    pause.setVisibility(View.VISIBLE);
                    resume.setVisibility(View.INVISIBLE);
                    fav.setVisibility(View.INVISIBLE);

            }
        });



        return view;
    }
}
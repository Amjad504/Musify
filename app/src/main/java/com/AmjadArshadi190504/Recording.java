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


public class Recording extends Fragment {

    int counter = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recording, container, false);
        ImageView pause = (ImageView) view.findViewById(R.id.pause);
        LinearLayout seek = (LinearLayout) view.findViewById(R.id.seek);
        ImageView play = (ImageView) view.findViewById(R.id.play);
        TextView next = (TextView) view.findViewById(R.id.next);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(counter == 0)
                {
                    pause.setVisibility(View.VISIBLE);
                    seek.setVisibility(View.VISIBLE);
                    play.setVisibility(View.INVISIBLE);
                    counter = 1;
                }



            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Selecting_Playlist selecting_playlist = new Selecting_Playlist();
                FragmentManager manager = getFragmentManager();
                manager.beginTransaction().replace(R.id.container,selecting_playlist).commit();
            }
        });




        return view;
    }
}
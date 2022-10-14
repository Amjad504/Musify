package com.AmjadArshadi190504;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Home extends Fragment {




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
        LinearLayout song = (LinearLayout) view.findViewById(R.id.song);
        song.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SongReview songReview = new SongReview();
                FragmentManager manager = getFragmentManager();
                manager.beginTransaction().replace(R.id.container,songReview).commit();
            }
        });



        return view;

    }


}
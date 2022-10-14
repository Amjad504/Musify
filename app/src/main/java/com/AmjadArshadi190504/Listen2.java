package com.AmjadArshadi190504;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class Listen2 extends Fragment {



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_listen2, container, false);
        TextView music = (TextView) view.findViewById(R.id.music);
        music.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Listen listen = new Listen();
                FragmentManager manager = getFragmentManager();
                manager.beginTransaction().replace(R.id.container,listen).commit();
            }
        });


        return view;
    }
}
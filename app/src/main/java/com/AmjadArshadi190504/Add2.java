package com.AmjadArshadi190504;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class Add2 extends Fragment {



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_add2, container, false);
        TextView recording = (TextView) view.findViewById(R.id.Recording);
        recording.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Record record = new Record();
                FragmentManager manager = getFragmentManager();
                manager.beginTransaction().replace(R.id.container,record).commit();
            }
        });


        return view;
    }
}
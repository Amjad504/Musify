package com.AmjadArshadi190504;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class Record extends Fragment {


    int counter = 0;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_record, container, false);
        TextView time = (TextView) view.findViewById(R.id.time);
        ImageView start = (ImageView) view.findViewById(R.id.starting);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(counter == 0)
                {
                    time.setVisibility(View.VISIBLE);
                    counter = 1;
                }
                else
                {
                    Recording recording = new Recording();
                    FragmentManager manager = getFragmentManager();
                    manager.beginTransaction().replace(R.id.container,recording).commit();
                }


            }
        });



        return view;
    }
}
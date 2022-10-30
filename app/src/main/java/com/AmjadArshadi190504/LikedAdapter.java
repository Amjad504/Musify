package com.AmjadArshadi190504;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class LikedAdapter extends RecyclerView.Adapter<LikedAdapter.MyViewHolder> {
    List<LikedSong> ls;
    Context c;
    int position;

    public LikedAdapter(List<LikedSong> ls, Context c)
    {
        this.ls = ls;
        this.c = c;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View row= LayoutInflater.from(c).inflate(R.layout.artistrow,parent,false);
        return new MyViewHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.name.setText(ls.get(position).getTitle());
        holder.artist.setText(ls.get(position).getArtist());
        holder.uri.setText(ls.get(position).getUri());
        holder.name.setOnClickListener(view -> {
            int  pos = (int) getItemId(position) + 1;
            SharedPreferences.Editor editor = c.getSharedPreferences("DeviceToken", c.MODE_PRIVATE).edit();
            editor.putString("name",ls.get(holder.getAdapterPosition()).getTitle());
            editor.putString("Artist",ls.get(holder.getAdapterPosition()).getArtist());
            editor.putString("uri",ls.get(holder.getAdapterPosition()).getUri());
            editor.putInt("pos",position);

            Toast.makeText(c.getApplicationContext(), "You clicked " + position, Toast.LENGTH_SHORT).show();

            editor.apply();
            FragmentManager manager = ((AppCompatActivity)c).getSupportFragmentManager();
            SongDisplay tab1 = new SongDisplay();
            manager.beginTransaction().replace(R.id.container,tab1)
                    .commit();


        });

    }

    @Override
    public int getItemCount() {
        return ls.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name,artist,uri;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.likeSong);
            artist = itemView.findViewById(R.id.likeArtist);
            uri = itemView.findViewById(R.id.likedURI);






        }
    }
}

package com.AmjadArshadi190504;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.checkerframework.checker.units.qual.A;

import java.io.IOException;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class SongDisplay extends Fragment implements playable{




    TextView title, artist, Duration, currDuration;
    ImageView songDp, playbtn, pauseBtn, back, next, delete;
    SeekBar musicbar,volumebar;
    MediaPlayer mediaPlayer;
    AudioManager audioManager;
    Handler handler = new Handler();
    String TITLE, ARTIST, URI;
    Runnable runnable;
    String value, getValue;
    String SongID;
    List ls,tracks;

    Uri uri;
    int pos;
    int position = 0;

    NotificationManager notificationManager;

    boolean isplaying = false;
    boolean ischeck = false;

    DatabaseReference reference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_song_display, container, false);






        LinearLayout fav = (LinearLayout) view.findViewById(R.id.favdown);
        ImageView resume = (ImageView) view.findViewById(R.id.play);
        ImageView pause = (ImageView) view.findViewById(R.id.pause);

        getActivity().setVolumeControlStream(AudioManager.STREAM_MUSIC);
        ls = ((MyApplication) getActivity().getApplication()).getLs();
        tracks = ls;



        title = view.findViewById(R.id.song_title);
        artist = view.findViewById(R.id.song_artist);
        songDp = view.findViewById(R.id.songDP);
        playbtn = view.findViewById(R.id.playmusic);
        pauseBtn = view.findViewById(R.id.pausemusic);
        back = view.findViewById(R.id.back);
        next = view.findViewById(R.id.nextsong);
        musicbar = view.findViewById(R.id.seekBar);
        Duration = view.findViewById(R.id.Duration);
        currDuration = view.findViewById(R.id.currDuration);
        delete = view.findViewById(R.id.delete);



        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            creteChannel();
            getActivity().registerReceiver(broadcastReceiver,new IntentFilter("TRACKS_TRACKS"));
            getActivity().startService(new Intent(getContext(),OnClearFromRecentServices.class));
        }

//        N_play.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//
//            }
//        });


        volumebar = view.findViewById(R.id.seekBar2);
        audioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);
        int maxvolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int currVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

        volumebar.setMax(maxvolume);
        volumebar.setProgress(currVolume);

        volumebar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,i,0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        TITLE = title.getText().toString();
        ARTIST = artist.getText().toString();


        SharedPreferences prefs = getActivity().getSharedPreferences("DeviceToken", getActivity().MODE_PRIVATE);
        title.setText(prefs.getString("name", null)); // get it here
        artist.setText(prefs.getString("Artist", null)); // get it here
        SongID = prefs.getString("ID",null);
        URI = prefs.getString("uri", null); // get it here
        pos = prefs.getInt("pos",0);

//


        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        String ID = currentUser.getUid();


        // calling method to play audio.
        playAudio(URI);
//

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                delete(SongID);
            }
        });

        return view;
    }

    private void creteChannel() {

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            NotificationChannel channel = new NotificationChannel(ApplicationClass.CHANNEL_ID,
                    "Channel(1)", NotificationManager.IMPORTANCE_LOW);
            notificationManager = getActivity().getSystemService(NotificationManager.class);
            if(notificationManager != null)
            {
                notificationManager.createNotificationChannel(channel);
            }
        }



    }



    private void delete(String songID) {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        String Id = currentUser.getUid();
        FirebaseDatabase db=FirebaseDatabase.getInstance();
        DatabaseReference root=db.getReference().child("Songs").child(Id).child(songID);
        root.setValue(null);
        Toast.makeText(getActivity(), "Data Deleted Successfully", Toast.LENGTH_SHORT).show();
        Home home = new Home();
        Bundle bundle = new Bundle();
        bundle.putInt("pos", pos);
        home.setArguments(bundle);
        FragmentManager manager = getFragmentManager();
        manager.beginTransaction().replace(R.id.container,home).commit();
    }


    private void playAudio(String audioUrl) {
        // initializing media player
        mediaPlayer = new MediaPlayer();

        // below line is use to set the audio stream type for our media player.
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            // below line is use to set our
            // url to our media player.
            mediaPlayer.setDataSource(audioUrl);

            // below line is use to prepare
            // and start our media player.
            mediaPlayer.prepare();


            MediaPLay(mediaPlayer);

            // below line is use to display a toast message.
            Toast.makeText(getActivity(), "Audio started playing..", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            // this line of code is use to handle error while playing our audio file.
            Toast.makeText(getActivity(), "Error found is " + e, Toast.LENGTH_SHORT).show();
        }
    }


    private void MediaPLay(MediaPlayer mediaplayer)
    {

        runnable = new Runnable() {
            @Override
            public void run() {
                musicbar.setProgress(mediaPlayer.getCurrentPosition());
                handler.postDelayed(this,500);
            }
        };
        int duration = mediaPlayer.getDuration();
        String sDuration = convertFormat(duration);
        Duration.setText(sDuration);

        playbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



//                    playbtn.setVisibility(View.INVISIBLE);
//                    pauseBtn.setVisibility(View.VISIBLE);
//                    mediaPlayer.start();
//                    musicbar.setMax(mediaplayer.getDuration());
//                    handler.postDelayed(runnable,0);

                    if(isplaying)
                    {
                        mediaPlayer.pause();
                        handler.removeCallbacks(runnable);
                        onTrackPause();

                    }
                    else
                    {
                        mediaPlayer.start();
                        musicbar.setMax(mediaplayer.getDuration());
                        handler.postDelayed(runnable,0);
                        onTrackPlay();

                    }
                }


        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentPosition = mediaPlayer.getCurrentPosition();
                int duration = mediaPlayer.getDuration();
                if(mediaPlayer.isPlaying() && duration != currentPosition)
                {
                    currentPosition = currentPosition+ 50000;
                    currDuration.setText(convertFormat((currentPosition)));
                    mediaPlayer.seekTo(currentPosition);
                }
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentPosition = mediaPlayer.getCurrentPosition();
                if(mediaPlayer.isPlaying() && currentPosition > 5000)
                {
                    currentPosition = currentPosition - 50000;
                    currDuration.setText(convertFormat(currentPosition));
                    mediaPlayer.seekTo(currentPosition);

                }
            }
        });

        musicbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if(b)
                {
                    mediaPlayer.seekTo(i);
                }
                currDuration.setText(convertFormat(mediaPlayer.getCurrentPosition()));
//                Log.d("CURRENTTTT : ", String.valueOf(currDuration));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                pauseBtn.setVisibility(View.INVISIBLE);
                playbtn.setVisibility((View.VISIBLE));
                mediaPlayer.seekTo(0);
            }
        });

    }

    private String convertFormat(int duration) {
        return String.format("%02d:%02d"
                , TimeUnit.MILLISECONDS.toMinutes(duration)
                ,TimeUnit.MILLISECONDS.toSeconds(duration)-
                        TimeUnit.MINUTES.toSeconds((TimeUnit.MILLISECONDS.toMinutes((duration)))));

    }


    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getExtras().getString("actionname");

            switch (action)
            {
                case ApplicationClass.ACTION_PREV:
                    int currentPosition = mediaPlayer.getCurrentPosition();
                    if(mediaPlayer.isPlaying() && currentPosition > 5000)
                    {
                        currentPosition = currentPosition - 50000;
                        currDuration.setText(convertFormat(currentPosition));
                        mediaPlayer.seekTo(currentPosition);

                    }
                    onTrackPrevious();
                    break;
                case ApplicationClass.ACTION_PLAY:
                    if(isplaying)
                    {
                        mediaPlayer.pause();
                        handler.removeCallbacks(runnable);
                        onTrackPause();
                    } else
                    {
                        mediaPlayer.start();
                        musicbar.setMax(mediaPlayer.getDuration());
                        handler.postDelayed(runnable,0);
                        onTrackPlay();
                    }
                    break;
                case ApplicationClass.ACTION_NEXT:
                    int currentPosition2 = mediaPlayer.getCurrentPosition();
                    int duration = mediaPlayer.getDuration();
                    if(mediaPlayer.isPlaying() && duration != currentPosition2)
                    {
                        currentPosition = currentPosition2+ 50000;
                        currDuration.setText(convertFormat((currentPosition)));
                        mediaPlayer.seekTo(currentPosition);
                    }
                    onTrackNext();
                    break;
            }

        }
    };


    @Override
    public void onTrackPrevious() {

//        pos--;
//        ApplicationClass.createNotification(getActivity(), (SongModel) tracks.get(pos),
//                R.drawable.pause,pos,tracks.size()-1);
//        title.setText(((SongModel) tracks.get(pos)).getTitle());

    }

    @Override
    public void onTrackPlay() {

        ApplicationClass.createNotification(getActivity(),(SongModel)tracks.get(pos),
                R.drawable.pause,pos,tracks.size()-1);
        playbtn.setImageResource(R.drawable.pause);
        title.setText( ((SongModel) tracks.get(pos)).getTitle());
        mediaPlayer.start();

        isplaying = true;

    }

    @Override
    public void onTrackPause() {


        ApplicationClass.createNotification(getActivity(),(SongModel)tracks.get(pos),
                R.drawable.play2,pos,tracks.size()-1);
        playbtn.setImageResource(R.drawable.ic_baseline_play_arrow_24);
        title.setText(((SongModel) tracks.get(pos)).getTitle());

        isplaying = false;

    }

    @Override
    public void onTrackNext() {

//        pos++;
//        ApplicationClass.createNotification(getActivity(),(SongModel)tracks.get(pos),
//                R.drawable.pause,pos,tracks.size()-1);
//        title.setText(((SongModel) tracks.get(pos)).getTitle());

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            notificationManager.cancelAll();
        }
        getActivity().unregisterReceiver(broadcastReceiver);
    }
}
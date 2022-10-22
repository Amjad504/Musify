package com.AmjadArshadi190504;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.AudioManager;
import android.media.Image;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Download_Music extends AppCompatActivity {

    TextView title,artist,Duration,currDuration,songid;
    ImageView songDp,playbtn,pauseBtn,back,next;
    SeekBar musicbar;
    MediaPlayer mediaPlayer;
    Handler handler = new Handler();
    String TITLE,ARTIST,URI;
    Runnable runnable;
    String SongID;
    List ls;
    Uri uri;
    ImageView comment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_music);


        ls = ((MyApplication) this.getApplication()).getLs();



        title = findViewById(R.id.song_title);
        artist = findViewById(R.id.song_artist);
        songDp = findViewById(R.id.songDP);
        playbtn = findViewById(R.id.playmusic);
        pauseBtn = findViewById(R.id.pausemusic);
        back = findViewById(R.id.back);
        next = findViewById(R.id.nextsong);
        musicbar = findViewById(R.id.seekBar);
        Duration = findViewById(R.id.Duration);
        currDuration = findViewById(R.id.currDuration);
        songid = findViewById(R.id.Song_ID);
        comment = findViewById(R.id.cmnt);


        TITLE = title.getText().toString();
        ARTIST = artist.getText().toString();



        Bundle bundle = getIntent().getExtras();
        title.setText(bundle.getString("name"));
        artist.setText("By "+(bundle.getString("Artist")));
        URI = bundle.getString("uri");

        Log.d("UIDDDDDDDD : : ",URI);

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        String ID = currentUser.getUid();

                // calling method to play audio.
                playAudio(URI);
//
//        pauseBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // checking the media player
//                // if the audio is playing or not.
//                if (mediaPlayer.isPlaying()) {
//                    // pausing the media player if
//                    // media player is playing we are
//                    // calling below line to stop our media player.
//                    mediaPlayer.stop();
//                    mediaPlayer.reset();
//                    mediaPlayer.release();
//
//                    // below line is to display a message when media player is paused.
//                    Toast.makeText(Download_Music.this, "Audio has been paused", Toast.LENGTH_SHORT).show();
//                } else {
//                    // this method is called when media player is not playing.
//                    Toast.makeText(Download_Music.this, "Audio has not played", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });











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
            Toast.makeText(this, "Audio started playing..", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            // this line of code is use to handle error while playing our audio file.
            Toast.makeText(this, "Error found is " + e, Toast.LENGTH_SHORT).show();
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
                    playbtn.setVisibility(View.INVISIBLE);
                    pauseBtn.setVisibility(View.VISIBLE);
                    mediaPlayer.start();
                    musicbar.setMax(mediaPlayer.getDuration());
                    handler.postDelayed(runnable,0);

                }

            });

            pauseBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pauseBtn.setVisibility(View.INVISIBLE);
                    playbtn.setVisibility(View.VISIBLE);
                    mediaPlayer.pause();
                    handler.removeCallbacks(runnable);

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


}
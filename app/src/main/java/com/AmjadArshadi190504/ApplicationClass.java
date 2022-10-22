package com.AmjadArshadi190504;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.media.session.MediaSessionCompat;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import Services.NotificationActionService;

public class ApplicationClass extends MyApplication {
    public static final String CHANNEL_ID = "CHANNEL_1";
    public static final String CHANNEL_ID_2 = "CHANNEL_2";
    public static final String ACTION_NEXT = "NEXT";
    public static final String ACTION_PREV = "PREV";
    public static final String ACTION_PLAY = "PLay";

    public static Notification notification;

//    @Override
//    public void onCreate() {
//        super.onCreate();
//        createNotifcationChannel();
//
//    }

    public static void createNotification(Context context, SongModel track, int playbtn, int pos, int size)
    {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
            MediaSessionCompat mediaSessionCompat = new MediaSessionCompat(context,"tag");


            PendingIntent pendingIntentPrev;
            int drw_previous;

                Intent intentPrevious = new Intent(context, NotificationActionService.class)
                        .setAction((ACTION_PREV));
                pendingIntentPrev = PendingIntent.getBroadcast(context,0,
                        intentPrevious, PendingIntent.FLAG_UPDATE_CURRENT);
                drw_previous = R.drawable.ic_baseline_skip_previous_24;


            Intent intentPlay = new Intent(context, NotificationActionService.class)
                    .setAction((ACTION_PLAY));
            PendingIntent pendingIntentPlay = PendingIntent.getBroadcast(context,0,
                    intentPlay, PendingIntent.FLAG_UPDATE_CURRENT);
            int drw_play;
            drw_play = R.drawable.ic_baseline_play_arrow_24;


            PendingIntent pendingIntentNext;
            int drw_Next;

                Intent intentNext = new Intent(context, NotificationActionService.class)
                        .setAction((ACTION_NEXT));
                pendingIntentNext = PendingIntent.getBroadcast(context,0,
                        intentNext, PendingIntent.FLAG_UPDATE_CURRENT);
                drw_Next = R.drawable.ic_baseline_skip_next_24;


            notification = new NotificationCompat.Builder(context,CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_baseline_music_note_24)
                    .setContentTitle(track.getTitle())
                    .setContentText(track.getArtist())
                    .setOnlyAlertOnce(true)
                    .setShowWhen(false)
                    .addAction(drw_previous,"Previous",pendingIntentPrev)
                    .addAction(playbtn,"Play",pendingIntentPlay)
                    .addAction(drw_Next,"Next",pendingIntentNext)
                    .setStyle(new androidx.media.app.NotificationCompat.MediaStyle()
                            .setShowActionsInCompactView(0,1,2)
                            .setMediaSession(mediaSessionCompat.getSessionToken()))
                    .setPriority(NotificationCompat.PRIORITY_LOW)
                    .build();

            notificationManagerCompat.notify(1,notification);
        }


    }
}


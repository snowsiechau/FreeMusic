package gloryhunter.freemusic.notification;


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import android.widget.RemoteViews;

import com.squareup.picasso.Picasso;

import gloryhunter.freemusic.R;
import gloryhunter.freemusic.activities.MainActivity;
import gloryhunter.freemusic.database.TopSongModel;
import gloryhunter.freemusic.utils.MusicHandle;

/**
 * Created by SNOW on 11/5/2017.
 */

public class MusicNotification {

    public static final int NOTI_ID = 0;
    private static RemoteViews remoteViews;
    public static NotificationManager notificationManager;
    private static NotificationCompat.Builder builder;

    public static void setupNotification(Context context, TopSongModel topSongModel){
        remoteViews = new RemoteViews(context.getPackageName(), R.layout.layout_notification);

        remoteViews.setTextViewText(R.id.tv_noti_song, topSongModel.getSong());
        remoteViews.setTextViewText(R.id.tv_noti_artist, topSongModel.getArtist());

        Intent intent = new Intent(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        builder = new NotificationCompat.Builder(context);
        builder.setSmallIcon(R.drawable.exo_controls_fastforward)
                .setContent(remoteViews)
                .setOngoing(true)
                .setContentIntent(pendingIntent);

        notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        notificationManager.notify(NOTI_ID, builder.build());

        Picasso.with(context).load(topSongModel.getSmallImage())
                            .into(remoteViews, R.id.iv_noti_song, NOTI_ID, builder.build());

        setOnClickPlayPause(context);
    }

    private static void setOnClickPlayPause(Context context){

        Intent intent = new Intent(context, MusicService.class);
        PendingIntent pendingIntent = PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        remoteViews.setOnClickPendingIntent(R.id.iv_noti_play, pendingIntent);
    }

    public static void updateNotification(){

        if (MusicHandle.hybridMediaPlayer.isPlaying()){
            remoteViews.setImageViewResource(R.id.iv_noti_play, R.drawable.exo_controls_pause);
        }else {
            remoteViews.setImageViewResource(R.id.iv_noti_play, R.drawable.exo_controls_play);
        }
        notificationManager.notify(0, builder.build());
    }
}

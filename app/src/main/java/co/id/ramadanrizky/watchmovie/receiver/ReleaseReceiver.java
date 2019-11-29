package co.id.ramadanrizky.watchmovie.receiver;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import androidx.core.app.NotificationCompat;
import co.id.ramadanrizky.watchmovie.BuildConfig;
import co.id.ramadanrizky.watchmovie.MainActivity;
import co.id.ramadanrizky.watchmovie.R;
import co.id.ramadanrizky.watchmovie.SettingsActivity;
import co.id.ramadanrizky.watchmovie.entity.ListMovieEntity;
import co.id.ramadanrizky.watchmovie.entity.ListNotifEntity;
import cz.msebera.android.httpclient.Header;

public class ReleaseReceiver extends BroadcastReceiver {

    public static String NOTIFICATION_RELEASE_ID = "notification_release_id";
    private static final int MAX_NOTIF = 2;
    private static final CharSequence CHANNEL_NAME = "WatchMovieChannel";
    private final static String GROUP_KEY_MOVIE = "group_key_movie";
    private final static int NOTIFICATION_REQUEST_CODE = 200;

    @Override
    public void onReceive(final Context context, final Intent intent) {
        int id = intent.getIntExtra("id",0);
        getData(context ,id);
    }

    private void getData(final Context context, final int id) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        final String date = sdf.format(new Date());
        Log.d("NOTIFDB : ", date);
        AsyncHttpClient client = new AsyncHttpClient();
        String url = "https://api.themoviedb.org/3/discover/movie?api_key="+ BuildConfig.TMDB_API_KEY+"&primary_release_date.gte="+date+"&primary_release_date.lte="+date;

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String response = new String(responseBody);
                try {
                    JSONObject object = new JSONObject(response);
                    JSONArray array = object.getJSONArray("results");
                    for (int i=0;i<array.length();i++){
                        JSONObject objectdata = array.getJSONObject(i);
                        if (objectdata.getString("release_date").equals(date)){
                            String title1 = array.getJSONObject(0).getString("title");
                            String title2 = array.getJSONObject(1).getString("title");
                            String title3 = array.getJSONObject(2).getString("title");
                            String title4 = array.getJSONObject(3).getString("title");
                            sendNotif(context, title1, title2, title3, title4, id);
                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    private void sendNotif(Context context, String title1, String title2, String title3, String title4, int id) {
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, NOTIFICATION_REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder mBuilder;
        String CHANNEL_ID = "channel_01";
        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle()
                .addLine("new Release " + title1)
                .addLine("new Release " + title2)
                .addLine("new Release " + title3)
                .addLine("new Release " + title4)
                .setBigContentTitle(context.getString(R.string.release_now))
                .setSummaryText("Watch Movie");
        mBuilder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentTitle(context.getString(R.string.release_now))
                .setContentText("Watch Movie")
                .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                .setGroup(GROUP_KEY_MOVIE)
                .setGroupSummary(true)
                .setContentIntent(pendingIntent)
                .setStyle(inboxStyle)
                .setAutoCancel(true);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channelRelease = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
            NotificationManager notificationManagerRelease = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            channelRelease.setShowBadge(true);
            channelRelease.enableLights(true);
            channelRelease.enableVibration(true);
            channelRelease.setVibrationPattern(new long[] {1000, 1000, 1000, 1000, 1000});
            channelRelease.setLightColor(Color.BLUE);
            mBuilder.setChannelId(CHANNEL_ID);

            if (notificationManagerRelease != null){
                notificationManagerRelease.createNotificationChannel(channelRelease);
            }
        }
        Notification notification = mBuilder.build();
        Log.d("SEND NOTIF: ", "notif sender");
        if (mNotificationManager != null) {
            mNotificationManager.notify(id, notification);
        }
    }

    public void setAlarm(Context context,int notifId){
        cancelAlarm(context);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, ReleaseReceiver.class);
        intent.putExtra("id", notifId);
        PendingIntent pendingIntent =PendingIntent.getBroadcast(context, NOTIFICATION_REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 8);
        calendar.set(Calendar.MINUTE, 56);
        calendar.set(Calendar.SECOND, 0);


        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        } else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        }
        Toast.makeText(context, context.getString(R.string.release_reminder)+ " is Activated", Toast.LENGTH_SHORT).show();


    }

    private static PendingIntent getPendingIntent(Context context) {
        Intent intent = new Intent(context, ReleaseReceiver.class);
        return PendingIntent.getBroadcast(context, NOTIFICATION_REQUEST_CODE, intent, PendingIntent.FLAG_CANCEL_CURRENT);
    }

    public void cancelAlarm(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(getPendingIntent(context));
        Toast.makeText(context, context.getString(R.string.release_reminder)+ " is Canceled", Toast.LENGTH_SHORT).show();
    }

}

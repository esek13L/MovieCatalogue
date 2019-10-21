package com.esekiel.moviecatalogue.util.notifications;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

import com.esekiel.moviecatalogue.BuildConfig;
import com.esekiel.moviecatalogue.MainActivity;
import com.esekiel.moviecatalogue.R;
import com.esekiel.moviecatalogue.data.model.Movie;
import com.esekiel.moviecatalogue.data.model.MovieResult;
import com.esekiel.moviecatalogue.data.rest.NotificationClient;
import com.esekiel.moviecatalogue.data.rest.RestApi;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import androidx.core.app.NotificationCompat;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationReceiver extends BroadcastReceiver {

    private static final String EXTRA_TYPE = "type";
    private static final String TYPE_DAILY = "daily_reminder";
    private static final String TYPE_RELEASE = "release_reminder";
    private static final int ID_DAILY_NOTIFICATION = 1;
    private static final int ID_RELEASE_NOTIFICATION = 2;

    private Context context;

    public NotificationReceiver() {

    }

    public NotificationReceiver(Context context) {
        this.context = context;
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        String type = intent.getStringExtra(EXTRA_TYPE);
        if (type.equals(TYPE_DAILY)) {
            showDaily(context);
        } else if (type.equals(TYPE_RELEASE)) {
            getReleaseToday(context);
        }

    }

    private Calendar getReminderTime(String type) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, type.equals(TYPE_DAILY) ? 7 : 8);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        if (calendar.before(Calendar.getInstance())) {
            calendar.add(Calendar.DATE, 1);
        }

        return calendar;
    }

    private Intent getReminderIntent(String type) {
        Intent intent = new Intent(context, NotificationReceiver.class);
        intent.putExtra(EXTRA_TYPE, type);
        return intent;
    }

    public void setReleaseTodayReminder() {
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, ID_RELEASE_NOTIFICATION, getReminderIntent(TYPE_RELEASE), 0);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, getReminderTime(TYPE_RELEASE).getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);


    }

    public void setDailyReminder() {
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, ID_DAILY_NOTIFICATION, getReminderIntent(TYPE_DAILY), 0);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, getReminderTime(TYPE_DAILY).getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);


    }

    private void getReleaseToday(final Context context) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date date = new Date();
        final String now = dateFormat.format(date);
        RestApi api;
        api = NotificationClient.getClient().create(RestApi.class);
        Call<Movie> call = api.discoverMovie(BuildConfig.TMDB_API_KEY, now, now);

        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                if (response.isSuccessful()) {
                    List<MovieResult> results = new ArrayList<>();
                    results = response.body().getResults();
                    int id = 2;
                    for (MovieResult result : results) {
                        String title = result.getTitle();
                        String desc = title + " " + context.getString(R.string.release_today_msg);
                        showRelease(context, title, desc, id);
                    }
                }
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {

            }
        });
    }

    private void showDaily(Context context) {
        int NOTIFICATION_ID = 1;
        String CHANNEL_ID = "Channel_1";
        String CHANNEL_NAME = "Daily Reminder channel";

        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, NOTIFICATION_ID, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        Uri uriRingtone = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_movie_white_24dp)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_movie_white_24dp))
                .setContentTitle(context.getResources().getString(R.string.app_name))
                .setContentText(context.getResources().getString(R.string.message_daily))
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setSound(uriRingtone)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        Notification notification = mBuilder.build();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
            mBuilder.setChannelId(CHANNEL_ID);

            if (mNotificationManager != null) {
                mNotificationManager.createNotificationChannel(channel);
            }
        }
        if (mNotificationManager != null) {
            mNotificationManager.notify(NOTIFICATION_ID, notification);
        }
    }

    private void showRelease(Context context, String title, String desc, int id) {
        String CHANNEL_ID = "Channel_2";
        String CHANNEL_NAME = "Today release channel";

        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, id, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        Uri uriRingtone = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_movie_white_24dp)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_movie_white_24dp))
                .setContentTitle(title)
                .setContentText(desc)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setSound(uriRingtone)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        Notification notification = mBuilder.build();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
            mBuilder.setChannelId(CHANNEL_ID);

            if (mNotificationManager != null) {
                mNotificationManager.createNotificationChannel(channel);
            }
        }
        if (mNotificationManager != null) {
            mNotificationManager.notify(id, notification);
        }
    }


    private void cancelReminder(Context context, String type) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, NotificationReceiver.class);
        int requestCode = type.equalsIgnoreCase(TYPE_DAILY) ? ID_DAILY_NOTIFICATION : ID_RELEASE_NOTIFICATION;
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, 0);
        pendingIntent.cancel();
        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent);
        }
    }

    public void cancelDailyReminder(Context context) {
        cancelReminder(context, TYPE_DAILY);

    }

    public void cancelReleaseToday(Context context) {
        cancelReminder(context, TYPE_RELEASE);

    }






    /*
    public NotificationReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        sendNotification(context, context.getString(R.string.app_name),
                context.getString(R.string.message_daily));
    }

    public void setDailyAlarm(Context context) {

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, NotificationReceiver.class);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 7);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, NOTIFICATION_ID, intent, 0);
        if (alarmManager != null) {
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        }

        Toast.makeText(context, "Daily Alarm Activated", Toast.LENGTH_SHORT).show();
    }

    private void sendNotification(Context context, String title, String desc) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(
                Context.NOTIFICATION_SERVICE);
        Intent intent = new Intent(context, MainActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, NotificationReceiver.NOTIFICATION_ID, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        Uri uriTone = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_movie_white_24dp)
                .setContentTitle(title)
                .setContentText(desc)
                .setContentIntent(pendingIntent)
                .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setPriority(Notification.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setSound(uriTone);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(
                    NOTIFICATION_CHANNEL_ID, NOTIFICATION_NAME,
                    NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.YELLOW);
            notificationChannel.enableVibration(true);
            notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});

            builder.setChannelId(NOTIFICATION_CHANNEL_ID);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(notificationChannel);
            }
        }
        if (notificationManager != null) {
            notificationManager.notify(NotificationReceiver.NOTIFICATION_ID, builder.build());
        }
    }

    public void cancelAlarm(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (alarmManager != null) {
            alarmManager.cancel(getPendingIntent(context));
        }
    }


    private static PendingIntent getPendingIntent(Context context) {
        Intent intent = new Intent(context, MovieReleaseReceiver.class);
        return PendingIntent.getBroadcast(context, NOTIFICATION_ID, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
    }

     */
}

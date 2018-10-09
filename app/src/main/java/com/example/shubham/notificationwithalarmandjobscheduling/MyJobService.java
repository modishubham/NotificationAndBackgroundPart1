package com.example.shubham.notificationwithalarmandjobscheduling;


import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;

import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;
import android.widget.Toast;

public class MyJobService extends JobService {

    private static final String CHANNEL_ID = "channel_id";
    private static final int NOTIFICATION_ID = 1;

    private static final String TAG = MyJobService.class.getSimpleName();
    private boolean mJobCancelled = false;

    Context context;

    /**
     * return true if you have to make some background operation
     *
     * @param params need to pass job params to stop job
     * @return
     */

    @Override
    public boolean onStartJob(JobParameters params) {
        Log.d(TAG, "Job started");
        doBackgroundWork(params);
        return true;
    }

    private void doBackgroundWork(final JobParameters params) {
        Toast.makeText(getBaseContext(),"job is running",Toast.LENGTH_LONG).show();
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getBaseContext(), CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_assessment_black)
                .setContentTitle("Job Scheduler")
                .setContentText("this is the example of job scheduler")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        this.context = getBaseContext();
        createNotificationChannel();

        Intent notificationIntent = new Intent(context, NotificationResult.class);

        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        //notification message will get at NotificationView
        notificationIntent.putExtra("INTENT_KEY_MESSAGE", "This is example of job scheduler");
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0);

        // Set the intent that will fire when the user taps the notification
        builder.setContentIntent(pendingIntent);

        // Notice this code calls setAutoCancel(), which automatically
        // removes the notification when the user taps it.
        builder.setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        // notificationId is a unique int for each notification that you must
        // define Remember to save the notification ID that you pass to NotificationManagerCompat.notify()
        // because you'll need it later if you want to update or remove the notification.
        notificationManager.notify(NOTIFICATION_ID, builder.build());

    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "channel_name";
            String description = "channel_description";

            /*
            NotificationChannel constructor requires an importance, using one of the constants from the NotificationManager class.
            This parameter determines how to interrupt the user for any notification that belongs
            to this channel—though you must also set the priority with setPriority() to
            support Android 7.1 and lower
             */
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

    }


    @Override
    public boolean onStopJob(JobParameters params) {
        Log.d(TAG, "Job cancelled before completion");
        mJobCancelled = true;
        return true;
    }
}

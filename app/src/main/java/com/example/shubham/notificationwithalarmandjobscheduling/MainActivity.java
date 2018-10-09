package com.example.shubham.notificationwithalarmandjobscheduling;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    Button mSetAlarmButton;
    Button goJobScheduler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSetAlarmButton = findViewById(R.id.set_alarm_button);
        goJobScheduler = findViewById(R.id.btn_goJobSchedulerPage);

        goJobScheduler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, JobSchedulerExampleActivity.class);
                startActivity(intent);
            }
        });

        mSetAlarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                long time = calendar.getTimeInMillis();
                setAlarm(time);
            }
        });

    }

    private void setAlarm(long time) {
        //getting the alarm manager
        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        //intent to open the broadcast receiver
        Intent intent = new Intent(this, AlarmManagerBroadcastReceiver.class);

        //pending intent to be passed in alarm manager
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);

        //logic to call the alarm to be run everyday
        am.setRepeating(AlarmManager.RTC, time, 60*1000, pendingIntent);
        Toast.makeText(this, "Alarm set", Toast.LENGTH_SHORT).show();
    }
}

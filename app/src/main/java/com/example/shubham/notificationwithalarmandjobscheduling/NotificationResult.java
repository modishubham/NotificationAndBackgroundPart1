package com.example.shubham.notificationwithalarmandjobscheduling;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class NotificationResult extends AppCompatActivity {

    TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_result);

        result=findViewById(R.id.tv_notificationResult);
        String resultData = getIntent().getStringExtra("INTENT_KEY_MESSAGE");
        result.setText(resultData);
    }
}

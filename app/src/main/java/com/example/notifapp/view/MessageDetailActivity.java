package com.example.notifapp.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.notifapp.R;
import com.example.notifapp.model.MessageModel;

public class MessageDetailActivity extends AppCompatActivity {

    TextView userName, message, time, severity, read_time;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_detail);
        userName = findViewById(R.id.userName);
        message = findViewById(R.id.message);
        time = findViewById(R.id.time);
        severity = findViewById(R.id.severity);
        read_time = findViewById(R.id.read_time);

        Intent intent = getIntent();
        String extraUserName = intent.getStringExtra("userName");
        String extraMessage = intent.getStringExtra("message");
        String extraTime = intent.getStringExtra("time");
        String extraSeverity = intent.getStringExtra("severity");
            userName.setText(extraUserName);
            message.setText(extraMessage);
            time.setText(extraTime);
            severity.setText(extraSeverity);


    }


}
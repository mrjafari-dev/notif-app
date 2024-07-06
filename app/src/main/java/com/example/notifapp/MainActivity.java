package com.example.notifapp;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.notifapp.view.Login;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    TextView textViewName, textviewEmail, textViewFeutchResult;
    SharedPreferences sharedPreference;
    Button buttonLogOut, buttonFetchUser;


    private static final String CHANNEL_ID = "channel_id";
    private static final String CHANNEL_NAME = "Channel Name";
    private static final String CHANNEL_DESC = "Channel Description";


    private static final int NOTIFICATION_ID = 1;
    private static final long NOTIFICATION_INTERVAL = 30 * 1000;

    private final Handler handler = new Handler(Looper.getMainLooper());
    private final Runnable notificationRunnable = new Runnable() {
        @Override
        public void run() {
            displayNotification();
            handler.postDelayed(this, NOTIFICATION_INTERVAL);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViewName = findViewById(R.id.name);
        textviewEmail = findViewById(R.id.email);
        buttonLogOut = findViewById(R.id.logout);
        buttonFetchUser = findViewById(R.id.fetchProfile);
        textViewFeutchResult = findViewById(R.id.fetchResult);


        createNotificationChannel();
        sharedPreference = getSharedPreferences("MyAppName", MODE_PRIVATE);

        if (sharedPreference.getString("logged", "false").equals("true")) {
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
            finish();
        }

        textViewName.setText(sharedPreference.getString("name",""));
        Log.d("sharedPreferences id is", sharedPreference.getString("id",""));
        textviewEmail.setText(sharedPreference.getString("email",""));
        buttonLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                String url = "http://192.168.80.182/NotificationApp_Api/logout.php";
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                if (response.equals("success")) {
                                    //shared Preferences in order to hold the login and we dont need to login everytime
                                    SharedPreferences.Editor editor = sharedPreference.edit();
                                    editor.putString("logged ", "");
                                    editor.putString("name ", "");
                                    editor.putString("email ", "");
                                    editor.apply();
                                    Intent intent = new Intent(getApplicationContext(), Login.class);
                                    startActivity(intent);
                                    finish();

                                }
                                else Toast.makeText(MainActivity.this,response,Toast.LENGTH_SHORT).show();


                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();


                    }
                }) {
                    protected Map<String, String> getParams() {
                        Map<String, String> paramV = new HashMap<>();
                        paramV.put("email", sharedPreference.getString("email",""));

                        return paramV;
                    }
                };
                queue.add(stringRequest);


            }
        });

        //handler.post(notificationRunnable);

    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            channel.setDescription(CHANNEL_DESC);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
    }

    private void displayNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.notification)
                .setContentTitle("It's working, hurray")
                .setContentText("First notification")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(notificationRunnable);
    }
}

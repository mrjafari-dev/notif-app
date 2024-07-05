package com.example.notifapp;

import android.content.SharedPreferences;

import android.os.Bundle;

import android.util.Log;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.notifapp.model.MessageModel;
import com.example.notifapp.view.MessageAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Messages extends AppCompatActivity {
    SharedPreferences sharedPreference;
    TextView user,message,message_time,severity,read_time;
    private RecyclerView recyclerView;
    private MessageAdapter messageAdapter;
    private ArrayList<MessageModel> messageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_details);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        user = findViewById(R.id.message_user);
        message = findViewById(R.id.message);
        message_time = findViewById(R.id.message_time);
        severity = findViewById(R.id.severity);
        read_time = findViewById(R.id.message_read_time);

        sharedPreference = getSharedPreferences("MyAppName", MODE_PRIVATE);

        user.setText(sharedPreference.getString("user_msg", ""));
        message.setText(sharedPreference.getString("message", ""));
        message_time.setText(sharedPreference.getString("message_time", ""));
        read_time.setText(sharedPreference.getString("read_time", ""));
        severity.setText(sharedPreference.getString("severity", ""));
        messageList = new ArrayList<>();
        messageAdapter = new MessageAdapter(messageList);
        recyclerView.setAdapter(messageAdapter);
        fetchMessages();
    }
    private void fetchMessages() {
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url = "http://192.168.135.230/NotificationApp_Api/Messages.php";
        String userId = sharedPreference.getString("id", "");

        Log.i("cfdsfdsfsf",userId);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("fetch function Response", "Raw Response: " + response);

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String status = jsonObject.getString("status");
                            Log.i("cfdsfdsfsf",status);
                            if(status.equals("success")){
                                parseJson(jsonObject);
                            }

                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }



                        Log.d("message api saved in shared refrences", "message: " + sharedPreference.getString("message" , ""));

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("id", userId);
                Log.d("map funktion sent the id", "id ia " + userId );
                return params;
            }
        };

        queue.add(stringRequest);
    }
    private void parseJson(JSONObject jsonObject) {
        try {
            JSONArray nachrichtenArray = jsonObject.getJSONArray("nachrichten");
            Log.i("cfdsfdsfsf",jsonObject.toString());

            for (int i = 0; i < nachrichtenArray.length(); i++) {
                JSONObject nachrichtObject = nachrichtenArray.getJSONObject(i);

                String benutzername = nachrichtObject.getString("benutzername");
                String nachricht = nachrichtObject.getString("nachricht");
                String time = nachrichtObject.getString("time");
                String severity = nachrichtObject.getString("severity");
                String read_time = nachrichtObject.getString("read_time");

                MessageModel message = new MessageModel(benutzername, nachricht, time, severity, read_time,0);
                messageList.add(message);
            }

            // Notify the adapter that the data set has changed
            messageAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

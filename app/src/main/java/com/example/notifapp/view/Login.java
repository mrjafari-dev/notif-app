package com.example.notifapp.view;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.notifapp.R;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {



    String user_msg, message, message_time, severity, read_time;
    TextView textviewRegisterNow;
    TextInputEditText textInputEditTextEmail, textInputEditTextPassword;
    Button buttonSubmmit;
    String name, email, password, loginId;
    TextView textViewError;
    ProgressBar progressBar;
    SharedPreferences sharedPreference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        textviewRegisterNow = findViewById(R.id.registerNow);
        textInputEditTextEmail = findViewById(R.id.email);
        textInputEditTextPassword = findViewById(R.id.password);
        buttonSubmmit = findViewById(R.id.submit);
        textViewError = findViewById(R.id.error);
        progressBar = findViewById(R.id.loading);
        sharedPreference = getSharedPreferences("MyAppName", MODE_PRIVATE);
        String userId = sharedPreference.getString("id", "");
        Log.i("546465456",userId);

        if (!userId.isEmpty()) {
            Intent intent = new Intent(getApplicationContext(), MessageViewActivity.class);
            startActivity(intent);
            finish();

        }
        buttonSubmmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                textViewError.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);

                email = String.valueOf(textInputEditTextEmail.getText());
                password = String.valueOf(textInputEditTextPassword.getText());
               if (sharedPreference.getString("logged", "false").equals("true")) {
                    Intent intent = new Intent(getApplicationContext(), MessageViewActivity.class);
                    startActivity(intent);
                    finish();

                }


                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                String url = "http://192.168.135.230/NotificationApp_Api/login.php";

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                progressBar.setVisibility(View.GONE);
                                try {
                                    Log.d("Response", "Raw Response: " + response);
                                    JSONObject jsonObject = new JSONObject(response);
                                    String status = jsonObject.getString("status");
                                    String message = jsonObject.getString("message");
                                    if (status.equals("success")) {
                                        fetchMessages();
                                        Log.d("fetch mesage", "function got called ");
                                        name = jsonObject.getString("name");
                                        email = jsonObject.getString("email");
                                        loginId = jsonObject.getString("id");

                                        //shared Preferences in order to hold the login and we dont need to login everytime
                                        SharedPreferences.Editor editor = sharedPreference.edit();

                                        editor.putString("logged ", "true");
                                        editor.putString("name", name);
                                        editor.putString("email", email);
                                        editor.putString("id", loginId);

                                        editor.apply();

                                        Intent intent = new Intent(getApplicationContext(), MessageViewActivity.class);
                                        startActivity(intent);
                                        finish();


                                    }

                                } catch (JSONException e) {
                                    throw new RuntimeException(e);
                                }


                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBar.setVisibility(View.GONE);
                        textViewError.setText(error.getLocalizedMessage());
                        textViewError.setVisibility(View.VISIBLE);
                        System.out.print(error.getLocalizedMessage());

                    }
                }) {
                    protected Map<String, String> getParams() {
                        Map<String, String> paramV = new HashMap<>();

                        paramV.put("email", email);
                        paramV.put("password", password);
                        return paramV;
                    }
                };
                queue.add(stringRequest);


            }
        });


        textviewRegisterNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Registration.class);
                startActivity(intent);
                finish();

            }
        });

    }

    private void fetchMessages() {
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url = "http://192.168.0.159/NotificationApp_Api/Messages.php";
        String userId = sharedPreference.getString("id", "");


        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("fetch function Response", "Raw Response: " + response);

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String status = jsonObject.getString("status");
                            if (status.equals("success")) {
                                JSONArray nachrichtenArray = jsonObject.getJSONArray("nachrichten");
                                if (nachrichtenArray.length() > 0) {
                                    JSONObject firstNachricht = nachrichtenArray.getJSONObject(0);
                                    user_msg = firstNachricht.getString("benutzername");
                                    message = firstNachricht.getString("nachricht");
                                    message_time = firstNachricht.getString("time");
                                    severity = firstNachricht.getString("severity");
                                    read_time = firstNachricht.getString("read_time");
                                    SharedPreferences.Editor editor = sharedPreference.edit();
                                    editor.putString("user_msg", user_msg);
                                    editor.putString("message", message);
                                    editor.putString("message_time", message_time);
                                    editor.putString("severity", severity);
                                    editor.putString("read_time", read_time);
                                    editor.putString("logged ", "true");
                                    Log.d("sharedpref", "user and etc" + sharedPreference.getString("user_msg", ""));
                                    editor.apply();


                                }


                            }

                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }


                        Log.d("message api saved in shared refrences", "message: " + sharedPreference.getString("message", ""));

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
                Log.d("map funktion sent the id", "id ia " + userId);
                return params;
            }
        };

        queue.add(stringRequest);
    }


}
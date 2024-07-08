package com.example.notifapp.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notifapp.R;
import com.example.notifapp.model.MessageModel;
import com.example.notifapp.model.MessageResponse;
import com.example.notifapp.notificationManager.NotificationHelper;
import com.example.notifapp.viewModel.MessageActivityViewModel;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class MessageViewActivity extends AppCompatActivity {
    TextView user,message,message_time,severity,read_time;
    private RecyclerView recyclerView;
    private MessageAdapter messageAdapter;
    private ArrayList<MessageModel> messageList;
    private NotificationHelper notificationHelper;
    private LinearLayout noMessageView;
    private Button logOutButton;
    MessageActivityViewModel messageActivityViewModel;
    Timer timer ;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_message_view);
        messageActivityViewModel = new ViewModelProvider(this).get(MessageActivityViewModel.class);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        notificationHelper = new NotificationHelper(this);

        // شروع آلارم تکرارشونده
        notificationHelper.startRepeatingAlarm();

        recyclerView = findViewById(R.id.recycler_view);
        noMessageView = findViewById(R.id.noMessageView);
        logOutButton = findViewById(R.id.LogOutButton);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        messageList = new ArrayList<>();
        messageAdapter = new MessageAdapter(messageList);
        recyclerView.setAdapter(messageAdapter);
        messageActivityViewModel.getMessage(messageActivityViewModel.getUserId(this));

        messageActivityViewModel.messageResponseMutableLiveData.observe(this, new Observer<MessageResponse>() {
            @Override
            public void onChanged(MessageResponse messageResponse) {
                if (messageResponse.getNachrichten() != null){
                    noMessageView.setVisibility(View.GONE);
                    if (messageList.size() < messageResponse.getNachrichten().size()){
                        messageActivityViewModel.deleteMessagesFromDb(MessageViewActivity.this,messageList);
                        messageList.removeAll(messageList);

                        for (MessageModel item:
                                messageResponse.getNachrichten()) {
                            messageList.add(item);
                        }
                        messageActivityViewModel.insertMessagesToDb(MessageViewActivity.this,messageList);
                        messageAdapter.notifyDataSetChanged();

                    }
                }
                else {
                    noMessageView.setVisibility(View.VISIBLE);
                }


            }
        });

        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                messageActivityViewModel.logOut(MessageViewActivity.this);
                startActivity(new Intent(MessageViewActivity.this, Login.class));
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        timer =new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {

                messageActivityViewModel.getMessage(messageActivityViewModel.getUserId(MessageViewActivity.this));
            }
        },0,5000);
    }

    @Override
    protected void onStop() {
        super.onStop();
        timer.cancel();
    }

}
package com.example.notifapp.notificationManager;

import static android.content.Context.MODE_PRIVATE;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.notifapp.AppDb.MessageDbRepository;
import com.example.notifapp.R;
import com.example.notifapp.Remote.UserRepository;
import com.example.notifapp.interfaces.UserRepositoryImp;
import com.example.notifapp.model.MessageModel;
import com.example.notifapp.model.MessageResponse;
import com.example.notifapp.view.Login;

import java.util.List;

public class AlarmReciver extends BroadcastReceiver implements UserRepositoryImp {
    public static final String CHANNEL_ID = "23";
    public static final CharSequence CHANNEL_NAME = "Your";
    public static final String CHANNEL_DESCRIPTION = "Channel";
    UserRepository userRepository;
    private MessageDbRepository messageDbRepository;
    SharedPreferences sharedPreference;

    private Context context;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("AlarmReceiver", "Alarm received");
        try {
            this.context = context;
            userRepository = new UserRepository(this);
            messageDbRepository = new MessageDbRepository(context);
            sharedPreference = context.getSharedPreferences("MyAppName", MODE_PRIVATE);
            String userId = sharedPreference.getString("id", "");
            userRepository.getUserMessage(userId);
        } catch (Exception e) {
            Log.d("AlarmReceiver", e.getMessage());

        }

    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            channel.setDescription(CHANNEL_DESCRIPTION);

            // Register the channel with the system
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void showNotification(String title, String message) {
        // Example notification implementation
        Intent intent = new Intent(context, Login.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent =
                PendingIntent.getActivity(
                        context, 0, intent,
                        PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(message)
                .setSmallIcon(R.drawable.notification)
                .setContentIntent(pendingIntent) // Set the intent that will fire when the user taps the notification
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true)
                ;

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        notificationManager.notify(1, builder.build());
    }

    @Override
    public void MessageSuccess(MessageResponse messageResponse) {
        List<MessageModel> messageModels = messageDbRepository.getMessage();
        if (messageResponse.getNachrichten() != null){
            if (messageModels.size() < messageResponse.getNachrichten().size()) {
                createNotificationChannel();
                String message = messageResponse.getNachrichten().get(messageResponse.getNachrichten().size() - 1).getNachricht();
                showNotification("Message", message);
            }
        }


    }
}

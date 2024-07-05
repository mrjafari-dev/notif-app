package com.example.notifapp.AppDb;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.notifapp.model.MessageModel;

@Database(entities = {MessageModel.class}, version = 1)

public abstract class AppDb extends RoomDatabase {

    private static AppDb instance;

    public abstract MessageDao messagesDao();

    public static synchronized AppDb getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDb.class, "app_database")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }
}

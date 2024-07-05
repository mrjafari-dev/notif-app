package com.example.notifapp.AppDb;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.notifapp.model.MessageModel;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface MessageDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(ArrayList<MessageModel> messageModels);

    @Update
    void update(ArrayList<MessageModel> messageModels);

    @Query("DELETE FROM messages")
    void deleteAll();

    @Query("SELECT * FROM messages")
    List<MessageModel> getMessages();
}

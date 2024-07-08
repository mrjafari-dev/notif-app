package com.example.notifapp.AppDb;

import android.content.Context;

import com.example.notifapp.model.MessageModel;

import java.util.ArrayList;
import java.util.List;

public class MessageDbRepository {
    private MessageDao messageDao;

    public MessageDbRepository(Context context){
        AppDb db = AppDb.getInstance(context);
        messageDao = db.messagesDao();
    }
    public void insert(ArrayList<MessageModel> messageModels) {
        messageDao.insert(messageModels);  // Insert if not exists
    }

    public void deleteMessages(){
        messageDao.deleteAll();
    }
    public List<MessageModel> getMessage(){
        return messageDao.getMessages();
    }
}

package com.example.notifapp.viewModel;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.notifapp.AppDb.MessageDbRepository;
import com.example.notifapp.Remote.UserRepository;
import com.example.notifapp.interfaces.UserRepositoryImp;
import com.example.notifapp.model.MessageModel;
import com.example.notifapp.model.MessageResponse;

import java.util.ArrayList;

public class MessageActivityViewModel extends ViewModel implements UserRepositoryImp {

    UserRepository userRepository = new UserRepository(this);
    private MessageDbRepository messageDbRepository;
    SharedPreferences sharedPreference;

    public MutableLiveData<MessageResponse> messageResponseMutableLiveData = new MutableLiveData<>();
    public void getMessage(String userId) {
        userRepository.getUserMessage(userId);
    }

    public void insertMessagesToDb(Context context, ArrayList<MessageModel> messageModels){
        messageDbRepository=new MessageDbRepository(context);
        messageDbRepository.insert(messageModels);

    }
    public void deleteMessagesFromDb(Context context, ArrayList<MessageModel> messageModels){
        messageDbRepository=new MessageDbRepository(context);
        messageDbRepository.deleteMessages(messageModels);

    }

    @Override
    public void MessageSuccess(MessageResponse messageResponse) {
    messageResponseMutableLiveData.setValue(messageResponse);
        Log.i("1234567890",messageResponse.getNachrichten().get(0).getNachricht()+"sdfgsdgv");
    }

    public String getUserId(Context context){
        sharedPreference = context.getSharedPreferences("MyAppName", MODE_PRIVATE);
        return sharedPreference.getString("id", "");
    }
}

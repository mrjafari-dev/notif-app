package com.example.notifapp.Remote;

import com.example.notifapp.model.LoginResponseModel;
import com.example.notifapp.model.MessageResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiService {



    @FormUrlEncoded
    @POST("Messages.php")
    Call<MessageResponse> getMessage(@Field("id") String id);


}

package com.example.notifapp.Remote;

import android.util.Log;

import com.example.notifapp.interfaces.UserRepositoryImp;
import com.example.notifapp.model.LoginResponseModel;
import com.example.notifapp.model.MessageResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class UserRepository {
    ApiService apiService =  ConnectionClass.getApiService();
        UserRepositoryImp userRepositoryImp;



    public UserRepository(UserRepositoryImp userRepositoryImp) {
        this.userRepositoryImp = userRepositoryImp;
    }

    public void getUserMessage(String id){
        Call<MessageResponse> call=apiService.getMessage(id);
        call.enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                userRepositoryImp.MessageSuccess(response.body());

            }
            @Override
            public void onFailure(Call<MessageResponse> call, Throwable t) {
                Log.i("1234567890",t.toString());
            }
        });
    }


}

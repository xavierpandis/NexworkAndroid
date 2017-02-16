package com.cjx.nexwork.managers.user;

import android.content.Context;
import android.util.Log;

import com.cjx.nexwork.exceptions.NexworkTokenException;
import com.cjx.nexwork.managers.BaseManager;
import com.cjx.nexwork.managers.UserLoginManager;
import com.cjx.nexwork.managers.UserTokenManager;
import com.cjx.nexwork.model.User;
import com.cjx.nexwork.services.UserService;
import com.cjx.nexwork.util.CustomProperties;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Xavi on 08/02/2017.
 */

public class UserManager extends BaseManager{
    private static UserManager ourInstance;
    private List<User> users;
    private UserService userService;
    private User user;

    private UserManager() {
        userService = retrofit.create(UserService.class);
    }

    public static UserManager getInstance() {
        if (ourInstance == null) {
            ourInstance = new UserManager();
        }

        return ourInstance;
    }

    public synchronized void getUser(final UserDetailCallback userDetailCallback) {
        Call<User> call = userService.getUser("admin");

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                user = response.body();

                int code = response.code();

                if(code == 200 || code == 201){
                    if(user != null){
                        userDetailCallback.onSuccess(user);
                    }
                }
                else{
                    userDetailCallback.onFailure(new Throwable("ERROR" + code + ", " + response.raw().message()));
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("StudyManager->", "getAllStudies()->ERROR: " + t);


                if (t instanceof NexworkTokenException){
                    Log.d("nxw => T", t.getMessage());
                }
                userDetailCallback.onFailure(t);
            }
        });
    }

    public synchronized void getCurrentUser(final UserDetailCallback userDetailCallback) {
        Call<User> call = userService.getCurrentUser();

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                user = response.body();

                int code = response.code();

                if(code == 200 || code == 201){
                    if(user != null){
                        userDetailCallback.onSuccess(user);
                    }
                }
                else{
                    userDetailCallback.onFailure(new Throwable("ERROR" + code + ", " + response.raw().message()));
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("StudyManager->", "getAllStudies()->ERROR: " + t);

                userDetailCallback.onFailure(t);
            }
        });
    }

}

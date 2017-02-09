package com.cjx.nexwork.managers.user;

import android.content.Context;
import android.util.Log;

import com.cjx.nexwork.managers.UserLoginManager;
import com.cjx.nexwork.model.User;
import com.cjx.nexwork.services.UserService;
import com.cjx.nexwork.util.CustomProperties;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Xavi on 08/02/2017.
 */

public class UserManager {

    private static UserManager ourInstance;
    private List<User> studies;
    private Retrofit retrofit;
    private Context context;
    private UserService userService;
    private User user;

    private UserManager(Context cntxt) {
        context = cntxt;
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd")
                .create();

        retrofit = new Retrofit.Builder()
                .baseUrl(CustomProperties.getInstance(context).get("app.baseUrl"))
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        userService = retrofit.create(UserService.class);
    }

    public static UserManager getInstance(Context cntxt) {
        if (ourInstance == null) {
            ourInstance = new UserManager(cntxt);
        }

        ourInstance.context = cntxt;

        return ourInstance;
    }

    public synchronized void getUser(final UserDetailCallback userDetailCallback) {
        Call<User> call = userService.getUser(UserLoginManager.getInstance(context).getUsername(), UserLoginManager.getInstance(context).getBearerToken());

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

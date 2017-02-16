package com.cjx.nexwork.managers;

import android.util.Log;

import com.cjx.nexwork.exceptions.NexworkTokenException;
import com.cjx.nexwork.managers.user.UserDetailCallback;
import com.cjx.nexwork.model.User;
import com.cjx.nexwork.services.UserService;
import com.cjx.nexwork.util.CustomProperties;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Xavi on 16/02/2017.
 */

public class RegisterManager {
    private static RegisterManager ourInstance;
    private UserService userService;
    private Retrofit retrofit;

    private RegisterManager() {
        retrofit = new Retrofit.Builder()
                .baseUrl(CustomProperties.baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        userService = retrofit.create(UserService.class);
    }

    public static RegisterManager getInstance() {
        if (ourInstance == null) {
            ourInstance = new RegisterManager();
        }
        return ourInstance;
    }

    public synchronized void createUser(User user, final RegisterCallback registerCallback) {

        Call<ResponseBody> call = userService.registerAccount(user);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                ResponseBody responseBody = response.body();

                int code = response.code();

                if(code == 200 || code == 201){
                    if(responseBody != null){
                        registerCallback.onSuccessRegister(responseBody);
                    }
                }
                else{
                    registerCallback.onFailureRegister(new Throwable("ERROR" + code + ", " + response.raw().message()));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if (t instanceof NexworkTokenException){
                    Log.d("nxw => T", t.getMessage());
                    Log.d("nxw => T", call.toString());
                }
                registerCallback.onFailureRegister(t);
            }
        });


    }
}

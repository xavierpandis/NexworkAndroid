package com.cjx.nexwork.managers.user;

import android.content.Context;
import android.util.Log;

import com.cjx.nexwork.exceptions.NexworkTokenException;
import com.cjx.nexwork.managers.BaseManager;
import com.cjx.nexwork.managers.UploadImageCallback;
import com.cjx.nexwork.managers.friend.FriendCallback;
import com.cjx.nexwork.model.User;
import com.cjx.nexwork.services.UserService;
import com.facebook.LoggingBehavior;

import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

    public synchronized void getUser(String login, final UserDetailCallback userDetailCallback) {
        Call<User> call = userService.getUser(login);

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

    public synchronized void updateUserImage(RequestBody fbody, RequestBody name, final UploadImageCallback uploadImageCallback){
        Call<ResponseBody> call = userService.updateUserImage(fbody, name);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    uploadImageCallback.onSuccessUploadImage(response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Log.d("upload", response.message());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                uploadImageCallback.onFailureUploadImage(t);
                Log.d("upload", t.getMessage());
            }
        });
    }

    public synchronized void updateUserData(User user, final UserDetailCallback userDetailCallback){
        Call<User> call = userService.updateUserData(user);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                userDetailCallback.onSuccessSaved(response.body());
                Log.d("upload", response.message());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                userDetailCallback.onFailureSaved(t);
                Log.d("upload", t.getMessage());
            }
        });
    }

    public synchronized Call updatePassword(String password){
        Call<ResponseBody> call = userService.updatePassword(password);
        Log.isLoggable(password, 1);
        return call;
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

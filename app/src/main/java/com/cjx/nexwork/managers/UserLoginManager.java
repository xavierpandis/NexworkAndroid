package com.cjx.nexwork.managers;

import android.accounts.AccountManager;
import android.util.Log;

import com.cjx.nexwork.model.UserToken;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserLoginManager {
    private static UserLoginManager ourInstance;
    private UserToken userToken;
    private String bearerToken;
    private String username;
    AccountManager mAccountManager;

    private UserLoginManager() {
    }

    public static UserLoginManager getInstance() {
        if(ourInstance == null){
            ourInstance = new UserLoginManager();
        }

        return ourInstance;
    }

    public synchronized void performLogin(final String username, String password, final LoginCallback loginCallback){
        Call<UserToken> call =  UserTokenManager.getInstance().getUserToken(username, password);
        TokenStoreManager.getInstance().setUsername(username);
        call.enqueue(new Callback<UserToken>() {
            @Override
            public void onResponse(Call<UserToken> call, Response<UserToken> response) {
                Log.i("UserLoginManager ", " performtaks->call.enqueue->onResponse res: " + response.body());
                userToken = response.body();

                int code = response.code();

                if (code == 200 || code == 201) {
                    bearerToken = "Bearer " + userToken.getAccessToken();
                    loginCallback.onSuccess(userToken);
                } else {
                    loginCallback.onFailure(new Throwable("ERROR " + code + ", " + response.raw().message()));
                }
            }

            @Override
            public void onFailure(Call<UserToken> call, Throwable t) {
                Log.e("UserLoginManager ", " performtaks->call.enqueue->onResponse err: " + t.toString());
                loginCallback.onFailure(t);
            }
        });
    }

    public UserToken getUserToken(){
        return userToken;
    }

    public String getBearerToken() {
        return bearerToken;
    }

    public String getUsername() {
        return username;
    }
}
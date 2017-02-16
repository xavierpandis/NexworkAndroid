package com.cjx.nexwork.managers;

import android.content.Context;

/**
 * Created by Xavi on 15/02/2017.
 */

public class TokenStoreManager {
    private static TokenStoreManager ourInstance;
    private String accessToken;
    private String refreshToken;
    private String username;
    private String tokenType;
    private Context context;

    public static TokenStoreManager getInstance() {
        if(ourInstance == null){
            ourInstance = new TokenStoreManager();
        }
        return ourInstance;
    }

    private TokenStoreManager(){
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}

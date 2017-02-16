package com.cjx.nexwork.managers;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.cjx.nexwork.activities.LoginActivity;
import com.cjx.nexwork.exceptions.NexworkTokenException;
import com.cjx.nexwork.model.UserToken;
import com.cjx.nexwork.util.CustomProperties;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import okhttp3.Authenticator;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Xavi on 09/02/2017.
 */

public abstract class BaseManager {
    protected Retrofit retrofit;

    public BaseManager(){
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd")
                .create();

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request original = chain.request();

                //String bearerToken = UserLoginManager.getInstance().getBearerToken();
                String bearerToken = TokenStoreManager.getInstance().getAccessToken();
                //QUITAR ESTO

                if(bearerToken == null || bearerToken.equals("")){
                    throw new NexworkTokenException("Token is missing");
                }

                // Request customization: add request headers
                Request.Builder requestBuilder = original.newBuilder()
                        .header("Authorization", TokenStoreManager.getInstance().getTokenType()+" "+bearerToken); // <-- this is the important line

                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        });

        httpClient.authenticator(new Authenticator() {
            @Override
            public Request authenticate(Route route, Response response) throws IOException {

                Call<UserToken> call = UserTokenManager.getInstance().getNewToken(TokenStoreManager.getInstance().getRefreshToken());

                try{

                    retrofit2.Response<UserToken> tokenResponse = call.execute();
                    if(tokenResponse.code() == 200) {
                        UserToken newToken = tokenResponse.body();

                        TokenStoreManager.getInstance().setTokenType(newToken.getTokenType());
                        TokenStoreManager.getInstance().setAccessToken(newToken.getAccessToken());
                        TokenStoreManager.getInstance().setRefreshToken(newToken.getRefreshToken());

                        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(TokenStoreManager.getInstance().getContext());
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("accessToken", newToken.getAccessToken());
                        editor.putString("refreshToken",newToken.getRefreshToken());
                        editor.putString("tokenType", newToken.getTokenType());
                        editor.apply();

                        return response.request().newBuilder()
                                .header("Authorization", newToken.getTokenType() + " " + newToken.getAccessToken())
                                .build();
                    }
                    else {
                        return null;
                    }

                }catch (IOException e){
                    return null;
                }
            }
        });

        OkHttpClient client = httpClient.build();

        retrofit = new Retrofit.Builder()
                .baseUrl(CustomProperties.baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();

    }

}

package com.cjx.nexwork.managers;

import com.cjx.nexwork.util.CustomProperties;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Xavi on 09/02/2017.
 */

public class BaseManager {
    private static BaseManager ourInstance;
    private Retrofit retrofit;

    public BaseManager(){

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd")
                .create();

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request original = chain.request();

                // Request customization: add request headers
                Request.Builder requestBuilder = original.newBuilder()
                        .header("Authorization", UserLoginManager.getInstance().getBearerToken()); // <-- this is the important line

                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        });

        OkHttpClient client = httpClient.build();

        retrofit = new Retrofit.Builder()
                .baseUrl(CustomProperties.baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();

    }

    public static BaseManager getInstance() {
        if (ourInstance == null) {
            ourInstance = new BaseManager();
        }

        return ourInstance;
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }
}

package com.cjx.nexwork.services;

import com.cjx.nexwork.model.User;
import com.cjx.nexwork.model.UserToken;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface TokenService {
    @POST("/oauth/token")
    @FormUrlEncoded
        //username=admin&password=admin&grant_type=password&scope=read%20write&client_secret=mySecretOAuthSecret&client_id=basketballapp
    Call<UserToken> requestToken(
            @Header("Authorization") String Authorization,
            @Field("username") String userName,
            @Field("password") String password,
            @Field("grant_type") String grantType,
            @Field("scope") String scope,
            @Field("client_secret") String client_secret,
            @Field("client_id") String client_id
    );

    @FormUrlEncoded
    @POST("/oauth/token")
    Call<UserToken> getNewAccessToken(
            @Field("client_id") String client_id,
            @Field("client_secret") String client_secret,
            @Field("refresh_token") String refresh_token,
            @Field("grant_type") String grantType);

}

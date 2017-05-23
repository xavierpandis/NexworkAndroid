package com.cjx.nexwork.services;

import com.cjx.nexwork.model.Friend;
import com.cjx.nexwork.model.User;

import java.util.HashMap;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

/**
 * Created by Xavi on 08/02/2017.
 */

public interface UserService {
    @GET("/api/users/{login}")
    Call<User> getUser(
            /**
             * "Bearer [space ]token"
             */
            @Path("login") String login
            //@Header("Authorization") String Authorization
    );

    @GET("/api/account")
    Call<User> getCurrentUser();

    @POST("/api/account")
    Call<User> updateUserData(@Body User user);

    @POST("/api/register/app")
    Call<ResponseBody> registerAccount(@Body User user);

    @Multipart
    @POST("/api/account/update_image/android")
    Call<ResponseBody> updateUserImage (
            @Part("file\"; filename=\"pp.png\" ") RequestBody file ,
            @Part("name") RequestBody name);

    @GET("")
    Call<List<Friend>> getFriendsUserConnected();

    @GET("")
    Call<List<Friend>> getFriendsUser(@Path("id") Long id);

}

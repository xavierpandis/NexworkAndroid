package com.cjx.nexwork.services;

import com.cjx.nexwork.model.User;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
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
            @Path("login") String login,
            @Header("Authorization") String Authorization
    );

}

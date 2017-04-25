package com.cjx.nexwork.services;

import com.cjx.nexwork.model.Friend;
import com.cjx.nexwork.model.User;

import java.util.List;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

/**
 * Created by Xavi on 08/02/2017.
 */

public interface FriendService {

    @GET("/api/user/connected/friends")
    Call<List<User>> getFriendsUserConnected();

    @GET("/api/user/{login}/friends")
    Call<List<User>> getFriendsUser(@Path("login") String login);

}

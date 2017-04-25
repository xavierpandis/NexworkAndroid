package com.cjx.nexwork.managers.friend;

/**
 * Created by Xavi on 04/04/2017.
 */

import android.util.Log;

import com.cjx.nexwork.exceptions.NexworkTokenException;
import com.cjx.nexwork.managers.BaseManager;
import com.cjx.nexwork.managers.user.UserDetailCallback;
import com.cjx.nexwork.model.Friend;
import com.cjx.nexwork.model.User;
import com.cjx.nexwork.services.FriendService;
import com.cjx.nexwork.services.UserService;

import java.util.List;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Xavi on 08/02/2017.
 */

public class FriendManager extends BaseManager {
    private static FriendManager ourInstance;
    private List<User> friends;
    private FriendService friendService;
    private Friend friend;

    private FriendManager() {friendService = retrofit.create(FriendService.class);}

    public static FriendManager getInstance() {
        if (ourInstance == null) {
            ourInstance = new FriendManager();
        }

        return ourInstance;
    }

    public synchronized void getFriendsCurrentUser(final FriendCallback friendCallback) {
        Call<List<User>> call = friendService.getFriendsUserConnected();

        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                friends = response.body();

                int code = response.code();

                if(code == 200 || code == 201){
                    if(friends != null){
                        friendCallback.onSuccess(friends);
                    }
                }
                else{
                    friendCallback.onFailure(new Throwable("ERROR" + code + ", " + response.raw().message()));
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Log.e("StudyManager->", "getAllStudies()->ERROR: " + t);

                friendCallback.onFailure(t);
            }
        });
    }

    public synchronized void getFriendsUser(String login, final FriendCallback friendCallback) {
        Call<List<User>> call = friendService.getFriendsUser(login);

        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                friends = response.body();

                int code = response.code();

                if(code == 200 || code == 201){
                    if(friends != null){
                        friendCallback.onSuccess(friends);
                    }
                }
                else{
                    friendCallback.onFailure(new Throwable("ERROR" + code + ", " + response.raw().message()));
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Log.e("StudyManager->", "getAllStudies()->ERROR: " + t);

                friendCallback.onFailure(t);
            }
        });
    }

}


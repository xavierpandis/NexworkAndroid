package com.cjx.nexwork.managers.friend;

import com.cjx.nexwork.model.Friend;
import com.cjx.nexwork.model.User;

import java.util.List;

/**
 * Created by Xavi on 04/04/2017.
 */

public interface FriendCallback {
    void onSuccess(List<User> userList);
    void onFailure(Throwable t);
}

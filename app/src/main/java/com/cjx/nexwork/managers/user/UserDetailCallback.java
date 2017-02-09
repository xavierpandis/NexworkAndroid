package com.cjx.nexwork.managers.user;

import com.cjx.nexwork.model.User;

/**
 * Created by Xavi on 08/02/2017.
 */

public interface UserDetailCallback {
    void onSuccess(User user);
    void onFailure(Throwable t);
}

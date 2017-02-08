package com.cjx.nexwork.managers;

import com.cjx.nexwork.model.UserToken;

public interface LoginCallback {
    void onSuccess(UserToken userToken);
    void onFailure(Throwable t);
}

package com.cjx.nexwork.managers;

import com.cjx.nexwork.model.User;

import okhttp3.ResponseBody;

/**
 * Created by Xavi on 16/02/2017.
 */

public interface RegisterCallback {
    void onSuccessRegister(ResponseBody responseBody);
    void onFailureRegister(Throwable t);
}

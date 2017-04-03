package com.cjx.nexwork.managers;

/**
 * Created by Xavi on 01/04/2017.
 */

public interface UpdateImageCallback {
    void onUpdateImage(String url);
    void onErrorUpdateImage(Throwable t);
}

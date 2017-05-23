package com.cjx.nexwork.managers;

public interface UploadImageCallback {
    void onSuccessUploadImage(String image);
    void onFailureUploadImage(Throwable t);
}

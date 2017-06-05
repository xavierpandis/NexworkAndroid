package com.cjx.nexwork.util;

import android.content.Context;
import android.content.res.AssetManager;
import android.view.View;
import android.view.ViewGroup;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class CustomProperties {
    public static String clientId = "littletimmyapp";
    public static String clientSecret = "my-secret-token-to-change-in-production";
    public static String grantType = "password";
    public static String scope = "read write";
    //public static String baseUrl = "http://192.168.1.39:8080";
    public static String baseUrl = "http://10.1.103.196:8080";
    //public static String baseWebsocket = "172.16.129.63:8080";
    //public static String baseUrl = "http://172.16.129.63:8080";
    public static final String ANDROID_EMULATOR_LOCALHOST = "10.0.2.2";
    //public static String baseUrl = "http://ba2c06b8.ngrok.io";
}
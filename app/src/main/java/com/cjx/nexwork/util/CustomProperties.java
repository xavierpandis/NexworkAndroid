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
    public static String baseUrl = "http://172.16.140.114:8080";
    //public static String baseUrl = "http://192.168.1.39:8080";
}
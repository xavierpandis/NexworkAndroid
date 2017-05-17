package com.cjx.nexwork.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.Display;

/**
 * Created by Xavi on 01/04/2017.
 */

public class AppUtils {

    /*public Bitmap roundCornerImage(Bitmap raw, float round) {
        Transformation transformation = new RoundedTransformationBuilder()
                .borderColor(getResources().getColor(R.color.my_special_orange))
                .borderWidthDp(5)
                .cornerRadiusDp(50)
                .oval(false)
                .build();

        Picasso.with(mContext)
                .load("http://{some_url}.jpg")
                .fit()
                .transform(transformation)
                .into(profile_image);

        return result;
    }*/

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivity =(ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivity == null) {
            return false;
        } else {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

}

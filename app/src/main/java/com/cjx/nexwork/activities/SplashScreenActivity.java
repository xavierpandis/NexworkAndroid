package com.cjx.nexwork.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.cjx.nexwork.R;
import com.cjx.nexwork.managers.TokenStoreManager;
import com.cjx.nexwork.managers.UserLoginManager;
import com.cjx.nexwork.model.UserToken;

import java.util.Timer;
import java.util.TimerTask;

public class SplashScreenActivity extends AppCompatActivity {

    private static final long SPLASH_SCREEN_DELAY = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_splash_screen);
        getSupportActionBar().hide();

        final UserToken userToken = UserLoginManager.getInstance().getUserToken();

        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        final String accessToken =  preferences.getString("accessToken","");

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                if(accessToken.equals("")){
                    Intent loginIntent = new Intent(SplashScreenActivity.this, LoginActivity.class);
                    startActivity(loginIntent);
                }else{

                    TokenStoreManager.getInstance().setAccessToken(preferences.getString("accessToken",""));
                    TokenStoreManager.getInstance().setRefreshToken(preferences.getString("refreshToken",""));
                    TokenStoreManager.getInstance().setTokenType(preferences.getString("tokenType",""));
                    TokenStoreManager.getInstance().setUsername(preferences.getString("username",""));
                    TokenStoreManager.getInstance().setContext(SplashScreenActivity.this);

                    Intent loginIntent = new Intent(SplashScreenActivity.this, MainActivity.class);
                    startActivity(loginIntent);
                }
                finish();
            }
        };

        // Simulate a long loading process on application startup.
        Timer timer = new Timer();
        timer.schedule(task, SPLASH_SCREEN_DELAY);
    }

    @Override
    protected void onResume(){
        super.onResume();
    }

}

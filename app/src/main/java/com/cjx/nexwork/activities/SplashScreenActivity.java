package com.cjx.nexwork.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.cjx.nexwork.R;
import com.cjx.nexwork.managers.TokenStoreManager;
import com.cjx.nexwork.managers.UserLoginManager;
import com.cjx.nexwork.managers.user.UserDetailCallback;
import com.cjx.nexwork.managers.user.UserManager;
import com.cjx.nexwork.model.User;
import com.cjx.nexwork.model.UserToken;

public class SplashScreenActivity extends AppCompatActivity implements UserDetailCallback {

    private static final long SPLASH_SCREEN_DELAY = 3000;

    private static final int PERMISSION_REQUEST_CODE = 1;

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    private void requestPermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Toast.makeText(this, "Write External Storage permission allows us to do store images. Please allow this permission in App Settings.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.e("value", "Permission Granted, Now you can use local drive .");
                } else {
                    Log.e("value", "Permission Denied, You cannot use local drive .");
                }
                break;
        }
    }

    public void enterApp(SharedPreferences preferences){
        TokenStoreManager.getInstance().setAccessToken(preferences.getString("accessToken",""));
        TokenStoreManager.getInstance().setRefreshToken(preferences.getString("refreshToken",""));
        TokenStoreManager.getInstance().setTokenType(preferences.getString("tokenType",""));
        TokenStoreManager.getInstance().setUsername(preferences.getString("username",""));
        TokenStoreManager.getInstance().setContext(SplashScreenActivity.this);

        Log.d("refresh_token token", TokenStoreManager.getInstance().getRefreshToken());
        Log.d("access_token token", TokenStoreManager.getInstance().getAccessToken());

        UserManager.getInstance().getCurrentUser(this);

        /*Intent loginIntent = new Intent(SplashScreenActivity.this, MainActivity.class);
        startActivity(loginIntent);*/
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_splash_screen);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        enterApp(preferences);

        /*TimerTask task = new TimerTask() {
            @Override
            public void run() {
                if(accessToken.equals("")){
                    Intent loginIntent = new Intent(SplashScreenActivity.this, MainActivity.class);
                    startActivity(loginIntent);
                }else{

                    if (Build.VERSION.SDK_INT >= 23)
                    {
                        if (checkPermission()) {
                            enterApp(preferences);
                        } else {
                            requestPermission(); // Code for permission
                            enterApp(preferences);
                        }
                    }
                    else
                    {
                        enterApp(preferences);
                    }
                }
                finish();
            }
        };

        // Simulate a long loading process on application startup.
        Timer timer = new Timer();
        timer.schedule(task, SPLASH_SCREEN_DELAY);*/
    }

    @Override
    protected void onResume(){
        super.onResume();
    }


    @Override
    protected void onDestroy() {
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        getWindow().getDecorView().clearFocus();
        super.onDestroy();
    }

    public void accessApp(String login){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);

        TokenStoreManager.getInstance().setAccessToken(preferences.getString("accessToken",""));
        TokenStoreManager.getInstance().setRefreshToken(preferences.getString("refreshToken",""));
        TokenStoreManager.getInstance().setTokenType(preferences.getString("tokenType",""));
        TokenStoreManager.getInstance().setUsername(login);

        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("username", login);
        editor.apply();

        Intent loginIntent = new Intent(SplashScreenActivity.this, MainActivity.class);
        startActivity(loginIntent);
        finish();
    }

    @Override
    public void onSuccess(User user) {
        Log.d("success", "user success");

        if (Build.VERSION.SDK_INT >= 23)
        {
            if (checkPermission()) {
                accessApp(user.getLogin());
            } else {
                requestPermission(); // Code for permission
                accessApp(user.getLogin());
            }
        }
        else accessApp(user.getLogin());

    }

    @Override
    public void onFailure(Throwable t) {
        Log.d("error", t.getMessage());
        Intent loginIntent = new Intent(SplashScreenActivity.this, LoginActivity.class);
        startActivity(loginIntent);
    }

    @Override
    public void onSuccessSaved(User user) {

    }

    @Override
    public void onFailureSaved(Throwable t) {

    }
}

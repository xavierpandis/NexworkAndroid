package com.cjx.nexwork.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cjx.nexwork.R;
import com.cjx.nexwork.activities.study.StudiesActivity;
import com.cjx.nexwork.managers.TokenStoreManager;
import com.cjx.nexwork.managers.UserLoginManager;
import com.cjx.nexwork.model.UserToken;

public class MainActivity extends AppCompatActivity {

    private TextView accessToken;
    private TextView timeExpire;
    private Button btnStudies;
    private Button btnUserProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        accessToken = (TextView) findViewById(R.id.accessToken);
        timeExpire = (TextView) findViewById(R.id.expireTime);

        btnStudies = (Button) findViewById(R.id.btnStudies);
        btnUserProfile = (Button) findViewById(R.id.btnUserProfile);

        btnStudies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent studiesIntent = new Intent(MainActivity.this, StudiesActivity.class);
                startActivity(studiesIntent);
            }
        });

        btnUserProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent studiesIntent = new Intent(MainActivity.this, UserProfileActivity.class);
                startActivity(studiesIntent);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        UserToken userToken = UserLoginManager.getInstance().getUserToken();

        if(TokenStoreManager.getInstance().getAccessToken()!=null || TokenStoreManager.getInstance().getAccessToken() != "") {
            accessToken.setText("Token: " + TokenStoreManager.getInstance().getAccessToken() +" || "+ TokenStoreManager.getInstance().getRefreshToken());
            //timeExpire.setText("expires in: " +  Math.ceil(userToken.getExpiresIn()) + " seconds.");
        } else {
            Log.e("MainActivity->", "onResume ERROR: userToken is NULL");
            Intent loginIntent = new Intent(this, LoginActivity.class);
            startActivity(loginIntent);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.logOut:
                logOut();
                return true;
            case R.id.goProfile:
                Intent profileIntent = new Intent(MainActivity.this, UserProfileActivity.class);
                startActivity(profileIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void logOut(){
        SharedPreferences mySPrefs =PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = mySPrefs.edit();
        editor.remove("accessToken");
        editor.remove("refreshToken");
        editor.remove("tokenType");
        editor.remove("username");
        editor.apply();
        TokenStoreManager.getInstance().setAccessToken("");
        TokenStoreManager.getInstance().setRefreshToken("");
        TokenStoreManager.getInstance().setTokenType("");
        Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(loginIntent);
    }


}

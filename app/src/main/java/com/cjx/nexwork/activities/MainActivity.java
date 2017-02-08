package com.cjx.nexwork.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cjx.nexwork.R;
import com.cjx.nexwork.activities.study.StudiesActivity;
import com.cjx.nexwork.managers.UserLoginManager;
import com.cjx.nexwork.model.UserToken;

public class MainActivity extends AppCompatActivity {

    private TextView accessToken;
    private TextView timeExpire;
    private Button btnStudies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        accessToken = (TextView) findViewById(R.id.accessToken);
        timeExpire = (TextView) findViewById(R.id.expireTime);

        btnStudies = (Button) findViewById(R.id.btnStudies);

        btnStudies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent studiesIntent = new Intent(MainActivity.this, StudiesActivity.class);
                startActivity(studiesIntent);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        UserToken userToken = UserLoginManager.getInstance(this.getApplicationContext()).getUserToken();

        if(userToken!=null) {
            accessToken.setText("Token: " + userToken.getAccessToken());
            timeExpire.setText("expires in: " +  Math.ceil(userToken.getExpiresIn() / 60) + " minutes.");
            Log.d("nxw", "RefreshToken: " + userToken.getRefreshToken());
        } else {
            Log.e("MainActivity->", "onResume ERROR: userToken is NULL");
            Intent loginIntent = new Intent(this, LoginActivity.class);
            startActivity(loginIntent);
        }

    }
}

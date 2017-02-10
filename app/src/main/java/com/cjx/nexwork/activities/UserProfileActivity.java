package com.cjx.nexwork.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.cjx.nexwork.R;
import com.cjx.nexwork.managers.user.UserDetailCallback;
import com.cjx.nexwork.managers.user.UserManager;
import com.cjx.nexwork.model.User;
import com.cjx.nexwork.util.CustomProperties;
import com.squareup.picasso.Picasso;

public class UserProfileActivity extends AppCompatActivity implements UserDetailCallback {

    private User user;
    private TextView userName;
    private ImageView userImage;
    private TextView userFacebook, userTwitter, userGithub, userDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        userName = (TextView) findViewById(R.id.userName);
        userImage = (ImageView) findViewById(R.id.userImage);
        userFacebook = (TextView) findViewById(R.id.userFacebook);
        userTwitter = (TextView) findViewById(R.id.userTwitter);
        userGithub = (TextView) findViewById(R.id.userGithub);
        userDescription = (TextView) findViewById(R.id.userDescription);

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        UserManager.getInstance().getUser(UserProfileActivity.this);
    }


    @Override
    public void onSuccess(User user) {
        Log.d("nxw", user.toString());

        userName.setText(user.getFirstName() + " " + user.getLastName());
        if(user.getImagen() == null){
            userImage.setImageResource(R.drawable.default_user_image);
        }else{
            Picasso.with(this)
                    .load(CustomProperties.baseUrl+"/"+user.getImagen())
                    .resize(300, 300)
                    .into(userImage);
        }
        userFacebook.setText(user.getFacebook());
        userTwitter.setText(user.getTwitter());
        userGithub.setText(user.getGithub());
        userDescription.setText(user.getCarta_presentacion());
    }

    @Override
    public void onFailure(Throwable t) {

    }
}

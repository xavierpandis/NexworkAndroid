package com.cjx.nexwork.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cjx.nexwork.R;
import com.cjx.nexwork.managers.user.UserDetailCallback;
import com.cjx.nexwork.managers.user.UserManager;
import com.cjx.nexwork.model.User;
import com.cjx.nexwork.util.CustomProperties;
import com.squareup.picasso.Picasso;

/**
 * Created by Xavi on 23/02/2017.
 */

public class UserProfileFragment extends Fragment implements UserDetailCallback {

    View view;
    private User user;
    private TextView userName;
    private ImageView userImage;
    private TextView userFacebook, userTwitter, userGithub, userDescription;
    private ProgressBar spinner;
    private LinearLayout boxUser;

    public static UserProfileFragment newInstance() {
        return new UserProfileFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_user_profile, container, false);
        UserManager.getInstance().getCurrentUser(this);
        spinner = (ProgressBar) view.findViewById(R.id.spinnerLoading);
        spinner.setVisibility(View.VISIBLE);

        userName = (TextView) view.findViewById(R.id.userName);
        userImage = (ImageView) view.findViewById(R.id.userImage);
        userFacebook = (TextView) view.findViewById(R.id.userFacebook);
        userTwitter = (TextView) view.findViewById(R.id.userTwitter);
        userGithub = (TextView) view.findViewById(R.id.userGithub);
        userDescription = (TextView) view.findViewById(R.id.userDescription);
        boxUser = (LinearLayout) view.findViewById(R.id.boxUser);
        boxUser.setVisibility(View.INVISIBLE);
        return view;
    }

    @Override
    public void onSuccess(User user) {
        Log.d("nxw",user.toString());

        spinner.setVisibility(View.INVISIBLE);
        boxUser.setVisibility(View.VISIBLE);

        userName.setText(user.getFirstName() + " " + user.getLastName());
        if(user.getImagen() == null){
            userImage.setImageResource(R.drawable.account_profile_icon);
        }else{
            Picasso.with(getActivity())
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

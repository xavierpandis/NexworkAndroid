package com.cjx.nexwork.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cjx.nexwork.R;
import com.cjx.nexwork.activities.MainActivity;
import com.cjx.nexwork.activities.study.StudiesActivity;
import com.cjx.nexwork.activities.work.WorkActivity;
import com.cjx.nexwork.managers.user.UserDetailCallback;
import com.cjx.nexwork.managers.user.UserManager;
import com.cjx.nexwork.model.User;
import com.cjx.nexwork.util.CustomProperties;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

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
    private ViewPager mViewPager;

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

    private void setupViewPager(ViewPager viewPager) {
        DemoCollectionPagerAdapter adapter = new DemoCollectionPagerAdapter(getActivity().getSupportFragmentManager());
        adapter.addFragment(new UserProfileStudiesFragment());
        adapter.addFragment(new UserProfileStudiesFragment());
        adapter.addFragment(new UserProfileStudiesFragment());
        adapter.addFragment(new UserProfileStudiesFragment());
        viewPager.setAdapter(adapter);
    }

    public class DemoCollectionPagerAdapter extends FragmentStatePagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public DemoCollectionPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            return mFragmentList.get(i);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment) {
            mFragmentList.add(fragment);
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_user_profile, container, false);
        UserManager.getInstance().getCurrentUser(this);
        spinner = (ProgressBar) view.findViewById(R.id.spinnerLoading);
        spinner.setVisibility(View.VISIBLE);

        TabLayout tabs = (TabLayout) view.findViewById(R.id.tabsUserProfile);
        tabs.addTab(tabs.newTab().setIcon(R.drawable.home_icon));
        tabs.addTab(tabs.newTab().setIcon(R.drawable.chat_icon));
        tabs.addTab(tabs.newTab().setIcon(R.drawable.account_profile_icon));
        tabs.addTab(tabs.newTab().setIcon(R.drawable.notification_icon));

        Button puton = (Button) view.findViewById(R.id.button3);
        puton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intento = new Intent(getActivity(), WorkActivity.class);
                getActivity().startActivity(intento);
            }
        });

        Button btnStudies = (Button) view.findViewById(R.id.button4);
        btnStudies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intento = new Intent(getActivity(), StudiesActivity.class);
                getActivity().startActivity(intento);
            }
        });

        Button btnChat = (Button) view.findViewById(R.id.btnChat);
        btnChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        mViewPager = (ViewPager) view.findViewById(R.id.pagerProfile);
        setupViewPager(mViewPager);
        tabs.setupWithViewPager(mViewPager);
        tabs.getTabAt(0).setIcon(R.drawable.home_icon);
        tabs.getTabAt(1).setIcon(R.drawable.home_icon);
        tabs.getTabAt(2).setIcon(R.drawable.home_icon);
        tabs.getTabAt(3).setIcon(R.drawable.home_icon);

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

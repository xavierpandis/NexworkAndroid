package com.cjx.nexwork.activities;

import android.annotation.SuppressLint;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cjx.nexwork.R;
import com.cjx.nexwork.fragments.FragmentFriendList;
import com.cjx.nexwork.fragments.UserProfileFragment;
import com.cjx.nexwork.fragments.study.FragmentListStudy;
import com.cjx.nexwork.fragments.work.FragmentListWork;
import com.cjx.nexwork.managers.user.UserDetailCallback;
import com.cjx.nexwork.managers.user.UserManager;
import com.cjx.nexwork.model.User;
import com.cjx.nexwork.util.BlurTransformation;
import com.cjx.nexwork.util.CircleTransform;
import com.cjx.nexwork.util.CustomProperties;
import com.github.florent37.picassopalette.PicassoPalette;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("NewApi")
public class ContactProfileActivity extends AppCompatActivity implements UserDetailCallback {

    View view;
    private User user;

    private TextView userFacebook, userTwitter, userGithub, userDescription;
    private ProgressBar spinner;
    private LinearLayout boxUser;
    private ViewPager mViewPager;

    Drawable drawableHomeIcon;
    Drawable drawableChatIcon;
    Drawable drawableManagementIcon;

    private TextView userName;
    private ImageView userImage;
    private TextView userAlias;
    private ImageView userBackground;
    private TabLayout tabLayout;
    public static Boolean profileConnected = false;
    CollapsingToolbarLayout collapsingToolbar;
    private AppBarLayout appBarLayout;
    private Toolbar toolbarCollapsed;
    private String userLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.collapse_layout);

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            userLogin = extras.getString("friendlogin");
        }

        UserManager.getInstance().getUser(userLogin, this);

        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.htab_collapse_toolbar);

        collapsingToolbar.setTitle("Username");
        collapsingToolbar.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));
        collapsingToolbar.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

            }
        });

        userName = (TextView) findViewById(R.id.profileUserRealName);
        userImage = (ImageView) findViewById(R.id.profileUserImage);
        userAlias = (TextView) findViewById(R.id.profileUserAlias);
        userBackground = (ImageView) findViewById(R.id.profileUserBackgroundImage);

        appBarLayout = (AppBarLayout) findViewById(R.id.htab_appbar);

        tabLayout = (TabLayout) findViewById(R.id.tabs_profile);

        tabLayout.addTab(tabLayout.newTab());
        tabLayout.addTab(tabLayout.newTab());
        tabLayout.addTab(tabLayout.newTab());

        ContactProfileActivity.DemoCollectionPagerAdapter adapter = new ContactProfileActivity.DemoCollectionPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.pager_profile);
        mViewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.getTabAt(0).setText("Experience");
        tabLayout.getTabAt(1).setText("Studies");
        tabLayout.getTabAt(2).setText("Contacts");

    }

    @Override
    public void onSuccess(User user) {
        collapsingToolbar.setTitle(user.getFirstName().concat(" ").concat(user.getLastName()));

        if(user.getImagen() == null){
            userImage.setImageResource(R.drawable.account_circle);
            userBackground.setImageResource(R.drawable.account_circle);
        }

        userName.setText(user.getFirstName() + " " + user.getLastName());
        userAlias.setText(user.getLogin());
    }

    @Override
    public void onFailure(Throwable t) {

    }

    public class DemoCollectionPagerAdapter extends FragmentStatePagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public DemoCollectionPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            switch (i){
                case 0: return new FragmentListWork(userLogin, false);
                case 1: return new FragmentListStudy(userLogin, false);
                case 2: return new FragmentFriendList(userLogin, false);
                default: return new FragmentListWork(userLogin, false);
            }
        }

        @Override
        public int getCount() {
            return 3;
        }

        public void addFragment(Fragment fragment) {
            mFragmentList.add(fragment);
        }

    }
}

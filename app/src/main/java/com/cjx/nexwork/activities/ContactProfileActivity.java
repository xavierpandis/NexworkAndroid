package com.cjx.nexwork.activities;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
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

    private ImageView backButton;
    private TextView toolbarTitle;

    private int colorScrim = 0;
    private int colorTextScrim = 0;

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.collapse_layout);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);


        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            userLogin = extras.getString("friendlogin");
        }

        UserManager.getInstance().getUser(userLogin, this);

        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.htab_collapse_toolbar);

        //collapsingToolbar.setTitle("Username");
        collapsingToolbar.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));

        Toolbar htab_toolbar = (Toolbar) findViewById(R.id.htab_toolbar);
        toolbarTitle = (TextView) htab_toolbar.findViewById(R.id.toolbar_title);
        backButton = (ImageView) htab_toolbar.findViewById(R.id.imageView7);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        appBarLayout = (AppBarLayout) findViewById(R.id.htab_appbar);

        tabLayout = (TabLayout) findViewById(R.id.tabs_profile);

        AppBarLayout.OnOffsetChangedListener mListener = new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                Log.d("verticalOffset", String.valueOf(verticalOffset));

                if (collapsingToolbar.getHeight() + verticalOffset < 2 * ViewCompat.getMinimumHeight(collapsingToolbar)) {
                    Log.d("collapse", "collapse");
                    //toolbarTitle.setAlpha(1.0f);
                } else {
                    Log.d("collapsed", "no collapsed");
                    toolbarTitle.setAlpha(0.0f);
                }

                if(verticalOffset <= -335){
                    if(colorScrim == 0){
                        collapsingToolbar.setContentScrimColor(getResources().getColor(R.color.colorAccent));
                        collapsingToolbar.setStatusBarScrimColor(getResources().getColor(R.color.colorAccent));
                    }else{
                        collapsingToolbar.setContentScrimColor(colorScrim);
                        collapsingToolbar.setStatusBarScrimColor(colorScrim);
                    }
                    if(colorTextScrim == 0){
                        backButton.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
                    }
                    else{
                        backButton.setColorFilter(colorTextScrim, PorterDuff.Mode.SRC_IN);
                    }

                }else{
                    collapsingToolbar.setContentScrimColor(Color.argb(0,0,0,0));
                    collapsingToolbar.setStatusBarScrimColor(Color.argb(0,0,0,0));
                    backButton.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
                }

                float alpha = Math.abs(verticalOffset / (float)
                        appBarLayout.getTotalScrollRange());


                userImage.setAlpha(1.0f - alpha);
                userName.setAlpha(1.0f - alpha);
                userAlias.setAlpha(1.0f - alpha);
                toolbarTitle.setAlpha(0.0f + alpha);
            }
        };

        appBarLayout.addOnOffsetChangedListener(mListener);

        userName = (TextView) findViewById(R.id.profileUserRealName);
        userImage = (ImageView) findViewById(R.id.profileUserImage);
        userAlias = (TextView) findViewById(R.id.profileUserAlias);
        userBackground = (ImageView) findViewById(R.id.profileUserBackgroundImage);

        tabLayout.addTab(tabLayout.newTab());
        tabLayout.addTab(tabLayout.newTab());
        tabLayout.addTab(tabLayout.newTab());

        collapsingToolbar.setTitleEnabled(false);
        htab_toolbar.setPadding(0, getStatusBarHeight(), 0, 0);

        ContactProfileActivity.DemoCollectionPagerAdapter adapter = new ContactProfileActivity.DemoCollectionPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.pager_profile);
        mViewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.getTabAt(0).setText(getString(R.string.works_tab));
        tabLayout.getTabAt(1).setText(getString(R.string.studies_tab));
        tabLayout.getTabAt(2).setText(getString(R.string.contacts_tab));

    }

    @Override
    public void onSuccess(final User user) {

        //collapsingToolbar.setTitleEnabled(false);
       // collapsingToolbar.setTitle(user.getFirstName().concat(" ").concat(user.getLastName()));
        toolbarTitle.setText(user.getFirstName().concat(" ").concat(user.getLastName()));

        if(user.getImagen() == null){
            userImage.setImageResource(R.drawable.account_circle);
            userBackground.setImageResource(R.drawable.account_circle);
        }else{

            Log.d("width", String.valueOf(userImage.getWidth()));
            Log.d("height", String.valueOf(userImage.getHeight()));

            userImage.getViewTreeObserver()
                    .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                        // Wait until layout to call Picasso
                        @Override
                        public void onGlobalLayout() {
                            // Ensure we call this only once
                            userImage.getViewTreeObserver()
                                    .removeOnGlobalLayoutListener(this);


                            Picasso.with(getApplicationContext()).load(CustomProperties.baseUrl+"/"+user.getImagen())
                                    .resize(userImage.getWidth(), userImage.getHeight())
                                    .transform(new CircleTransform())
                                    .into(userImage,
                                            PicassoPalette.with(CustomProperties.baseUrl+"/"+user.getImagen(), userImage)
                                                    .intoCallBack(
                                                            new PicassoPalette.CallBack() {
                                                                @Override
                                                                public void onPaletteLoaded(Palette palette) {
                                                                    paletteToolbar(palette);
                                                                }
                                                            })
                                    );
                        }
                    });

            userBackground.getViewTreeObserver()
                    .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                        // Wait until layout to call Picasso
                        @Override
                        public void onGlobalLayout() {
                            // Ensure we call this only once
                            userBackground.getViewTreeObserver()
                                    .removeOnGlobalLayoutListener(this);


                            Picasso
                                    .with(getApplicationContext())
                                    .load(CustomProperties.baseUrl+"/"+user.getImagen())
                                    //.resize(userBackground.getWidth(), userBackground.getHeight())
                                    .resize(userBackground.getWidth(), userBackground.getHeight())
                                    .transform(new BlurTransformation(getApplicationContext()))
                                    .centerCrop()
                                    .into(userBackground);

                        }
                    });


        }


        userName.setText(user.getFirstName() + " " + user.getLastName());
        userAlias.setText(user.getLogin());
    }

    @Override
    public void onFailure(Throwable t) {

    }

    @Override
    public void onSuccessSaved(User user) {

    }

    @Override
    public void onFailureSaved(Throwable t) {

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

    public void paletteToolbar(Palette palette){
        Palette.Swatch vibrantSwatch = palette.getVibrantSwatch();
        Palette.Swatch vibrantLightSwatch = palette.getLightVibrantSwatch();
        Palette.Swatch vibrantDarkSwatch = palette.getDarkVibrantSwatch();
        Palette.Swatch mutedSwatch = palette.getMutedSwatch();
        Palette.Swatch dominantSwatch = palette.getDominantSwatch();
        Palette.Swatch mutedLightSwatch = palette.getLightMutedSwatch();
        Palette.Swatch mutedDarkSwatch = palette.getDarkMutedSwatch();

        if(vibrantSwatch != null){
            colorScrim = vibrantSwatch.getRgb();
            colorTextScrim = vibrantSwatch.getTitleTextColor();
            collapsingToolbar.setContentScrimColor(vibrantSwatch.getRgb());
            collapsingToolbar.setStatusBarScrimColor(vibrantSwatch.getRgb());
            toolbarTitle.setTextColor(vibrantSwatch.getTitleTextColor());
            tabLayout.setTabTextColors(vibrantSwatch.getTitleTextColor(), vibrantSwatch.getBodyTextColor());
            tabLayout.setBackgroundColor(vibrantSwatch.getRgb());
            appBarLayout.setBackgroundColor(vibrantSwatch.getRgb());
        }
        else{
            if(vibrantLightSwatch == null){
                colorScrim = dominantSwatch.getRgb();
                colorTextScrim = dominantSwatch.getTitleTextColor();
                collapsingToolbar.setContentScrimColor(dominantSwatch.getRgb());
                collapsingToolbar.setStatusBarScrimColor(dominantSwatch.getRgb());
                toolbarTitle.setTextColor(dominantSwatch.getTitleTextColor());
                tabLayout.setTabTextColors(dominantSwatch.getTitleTextColor(), dominantSwatch.getBodyTextColor());
                tabLayout.setBackgroundColor(dominantSwatch.getRgb());
                appBarLayout.setBackgroundColor(dominantSwatch.getRgb());
            }
            else{
                colorTextScrim = vibrantLightSwatch.getTitleTextColor();
                toolbarTitle.setTextColor(vibrantLightSwatch.getTitleTextColor());
                colorScrim = vibrantLightSwatch.getRgb();
                collapsingToolbar.setContentScrimColor(vibrantLightSwatch.getRgb());
                collapsingToolbar.setStatusBarScrimColor(vibrantLightSwatch.getRgb());
                tabLayout.setTabTextColors(vibrantLightSwatch.getTitleTextColor(), vibrantLightSwatch.getBodyTextColor());
                tabLayout.setBackgroundColor(vibrantLightSwatch.getRgb());
                appBarLayout.setBackgroundColor(vibrantLightSwatch.getRgb());
            }
        }
    }
}

package com.cjx.nexwork.fragments;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cjx.nexwork.R;
import com.cjx.nexwork.fragments.study.FragmentListStudy;
import com.cjx.nexwork.fragments.work.FragmentListWork;
import com.cjx.nexwork.managers.TokenStoreManager;
import com.cjx.nexwork.managers.user.UserDetailCallback;
import com.cjx.nexwork.managers.user.UserManager;
import com.cjx.nexwork.model.User;
import com.cjx.nexwork.util.BlurTransformation;
import com.cjx.nexwork.util.CircleTransform;
import com.cjx.nexwork.util.CustomProperties;
import com.github.florent37.picassopalette.PicassoPalette;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Xavi on 23/02/2017.
 */

@SuppressLint("NewApi")
public class UserProfileFragment extends Fragment implements UserDetailCallback {

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

    public static UserProfileFragment newInstance(Boolean userConnected) {
        profileConnected = userConnected;
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
    public void onDetach(){
        super.onDetach();
    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
    }

    public class DemoCollectionPagerAdapter extends FragmentStatePagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public DemoCollectionPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            TokenStoreManager.getInstance().setUsername(preferences.getString("username",""));
            Log.d("username", TokenStoreManager.getInstance().getUsername());
            switch (i){
                case 0:
                    Fragment fragment = FragmentListWork.newInstance();
                    Bundle args = new Bundle();
                    args.putString("login", TokenStoreManager.getInstance().getUsername());
                    args.putBoolean("userConnected", false);
                    fragment.setArguments(args);
                    return fragment;
                case 1:
                    return new FragmentListStudy(TokenStoreManager.getInstance().getUsername(), false);
                case 2:
                    return new FragmentFriendList(TokenStoreManager.getInstance().getUsername(), false);
                default:
                    return new FragmentListWork(TokenStoreManager.getInstance().getUsername(), false);
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

    Toolbar htab_toolbar;
    private TextView toolbarTitle;
    private ImageView backButton;

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    private void setupViewPager(ViewPager viewPager) {
        DemoCollectionPagerAdapter adapter = new DemoCollectionPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new FragmentListWork(TokenStoreManager.getInstance().getUsername(), false));
        adapter.addFragment(new FragmentListStudy(TokenStoreManager.getInstance().getUsername(), false));
        adapter.addFragment(new FragmentFriendList(TokenStoreManager.getInstance().getUsername(), false));
        viewPager.setAdapter(adapter);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.collapse_layout, container, false);

        UserManager.getInstance().getCurrentUser(this);

        collapsingToolbar = (CollapsingToolbarLayout) view.findViewById(R.id.htab_collapse_toolbar);

        collapsingToolbar.setTitle("Username");
        collapsingToolbar.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getActivity().getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);


        htab_toolbar = (Toolbar) view.findViewById(R.id.htab_toolbar);

        userName = (TextView) view.findViewById(R.id.profileUserRealName);
        userImage = (ImageView) view.findViewById(R.id.profileUserImage);
        userAlias = (TextView) view.findViewById(R.id.profileUserAlias);
        userBackground = (ImageView) view.findViewById(R.id.profileUserBackgroundImage);

        ImageView imageView = (ImageView) htab_toolbar.findViewById(R.id.imageView7);
        imageView.setVisibility(View.GONE);

        appBarLayout = (AppBarLayout) view.findViewById(R.id.htab_appbar);
        htab_toolbar.setPadding(0, getStatusBarHeight(), 0, 0);

        toolbarTitle = (TextView) htab_toolbar.findViewById(R.id.toolbar_title);
        backButton = (ImageView) htab_toolbar.findViewById(R.id.imageView7);

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
        tabLayout = (TabLayout) view.findViewById(R.id.tabs_profile);

        tabLayout.addTab(tabLayout.newTab());
        tabLayout.addTab(tabLayout.newTab());
        tabLayout.addTab(tabLayout.newTab());

        //DemoCollectionPagerAdapter adapter = new DemoCollectionPagerAdapter(getChildFragmentManager());
        mViewPager = (ViewPager) view.findViewById(R.id.pager_profile);
       // mViewPager.setAdapter(adapter);

        setupViewPager(mViewPager);

        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.getTabAt(0).setText(getString(R.string.works_tab));
        tabLayout.getTabAt(1).setText(getString(R.string.studies_tab));
        tabLayout.getTabAt(2).setText(getString(R.string.contacts_tab));

        return view;
    }

    private int colorScrim = 0;
    private int colorTextScrim = 0;

    @Override
    public void onSuccess(User user) {

       // collapsingToolbar.setTitle(user.getFirstName().concat(" ").concat(user.getLastName()));

        toolbarTitle.setText(user.getFirstName().concat(" ").concat(user.getLastName()));

        if(user.getImagen() == null){
            userImage.setImageResource(R.drawable.account_circle);
            userBackground.setImageResource(R.drawable.account_circle);
        }else{

            Picasso.with(getContext()).load(CustomProperties.baseUrl+"/"+user.getImagen())
                    .resize(userImage.getWidth(), userImage.getHeight())
                    .transform(new CircleTransform())
                    .centerCrop()
                    .into(userImage, PicassoPalette.with(CustomProperties.baseUrl+"/"+user.getImagen(), userImage)
                            .intoCallBack(new PicassoPalette.CallBack() {
                                @Override
                                public void onPaletteLoaded(Palette palette) {
                                    paletteToolbar(palette);
                                }
                            })
                    );

            Picasso
                    .with(getContext())
                    .load(CustomProperties.baseUrl+"/"+user.getImagen())
                    .resize(userBackground.getWidth(), userBackground.getHeight())
                    .transform(new BlurTransformation(getContext()))
                    .centerCrop()
                    .into(userBackground);

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

package com.cjx.nexwork.fragments;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
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
import com.cjx.nexwork.activities.MainActivity;
import com.cjx.nexwork.activities.account.EditProfileActivity;
import com.cjx.nexwork.activities.study.StudiesActivity;
import com.cjx.nexwork.activities.work.WorkActivity;
import com.cjx.nexwork.fragments.study.FragmentListStudy;
import com.cjx.nexwork.fragments.work.FragmentListWork;
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
            switch (i){
                case 0: return new FragmentListWork(false);
                case 1: return new FragmentListStudy();
                case 2: return new FragmentFriendList();
                default: return new FragmentListWork(false);
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.collapse_layout, container, false);

        UserManager.getInstance().getCurrentUser(this);

        collapsingToolbar = (CollapsingToolbarLayout) view.findViewById(R.id.htab_collapse_toolbar);

        collapsingToolbar.setTitle("Username");
        collapsingToolbar.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));
        collapsingToolbar.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

            }
        });

        userName = (TextView) view.findViewById(R.id.profileUserRealName);
        userImage = (ImageView) view.findViewById(R.id.profileUserImage);
        userAlias = (TextView) view.findViewById(R.id.profileUserAlias);
        userBackground = (ImageView) view.findViewById(R.id.profileUserBackgroundImage);

        appBarLayout = (AppBarLayout) view.findViewById(R.id.htab_appbar);

        tabLayout = (TabLayout) view.findViewById(R.id.tabs_profile);

        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getActivity().getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);*/

        tabLayout.addTab(tabLayout.newTab());
        tabLayout.addTab(tabLayout.newTab());
        tabLayout.addTab(tabLayout.newTab());

        DemoCollectionPagerAdapter adapter = new DemoCollectionPagerAdapter(getActivity().getSupportFragmentManager());
        mViewPager = (ViewPager) view.findViewById(R.id.pager_profile);
        mViewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.getTabAt(0).setText("Experience");
        tabLayout.getTabAt(1).setText("Studies");
        tabLayout.getTabAt(2).setText("Contacts");

        return view;
    }

    @Override
    public void onSuccess(User user) {

        collapsingToolbar.setTitle(user.getFirstName().concat(" ").concat(user.getLastName()));

        //((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(user.getFirstName().concat(" ").concat(user.getLastName()));

        if(user.getImagen() == null){
            userImage.setImageResource(R.drawable.account_circle);
            userBackground.setImageResource(R.drawable.account_circle);
        }else{

            Picasso.with(getContext()).load(CustomProperties.baseUrl+"/"+user.getImagen())
                    .resize(userImage.getWidth(), userImage.getHeight())
                    .transform(new CircleTransform())
                    .centerCrop()
                    .into(userImage,
                            PicassoPalette.with(CustomProperties.baseUrl+"/"+user.getImagen(), userImage)
                                    .intoCallBack(
                                            new PicassoPalette.CallBack() {
                                                @Override
                                                public void onPaletteLoaded(Palette palette) {
                                                    //specific task
                                                    Palette.Swatch vibrantSwatch = palette.getVibrantSwatch();
                                                    Palette.Swatch vibrantLightSwatch = palette.getLightVibrantSwatch();
                                                    Palette.Swatch vibrantDarkSwatch = palette.getDarkVibrantSwatch();
                                                    Palette.Swatch mutedSwatch = palette.getMutedSwatch();
                                                    Palette.Swatch dominantSwatch = palette.getDominantSwatch();
                                                    Palette.Swatch mutedLightSwatch = palette.getLightMutedSwatch();
                                                    Palette.Swatch mutedDarkSwatch = palette.getDarkMutedSwatch();

                                                    Log.d("nxw",""+vibrantSwatch.getRgb());
                                                    Log.d("nxw",""+vibrantLightSwatch.getRgb());
                                                    Log.d("nxw",""+vibrantDarkSwatch.getRgb());
                                                    Log.d("nxw",""+mutedSwatch.getRgb());
                                                    Log.d("nxw",""+dominantSwatch.getRgb());
                                                    Log.d("nxw",""+mutedLightSwatch.getRgb());
                                                    Log.d("nxw",""+mutedDarkSwatch.getRgb());

                                                    if(vibrantSwatch != null){
                                                        collapsingToolbar.setContentScrimColor(vibrantSwatch.getRgb());
                                                        collapsingToolbar.setStatusBarScrimColor(vibrantSwatch.getRgb());
                                                        appBarLayout.setBackgroundColor(vibrantSwatch.getRgb());

                                                        /*drawableHomeIcon.setTint(vibrantSwatch.getTitleTextColor());
                                                        drawableHomeIcon.setTintMode(PorterDuff.Mode.SRC_IN);

                                                        drawableChatIcon.setTint(vibrantSwatch.getTitleTextColor());
                                                        drawableChatIcon.setTintMode(PorterDuff.Mode.SRC_IN);

                                                        drawableManagementIcon.setTint(vibrantSwatch.getTitleTextColor());
                                                        drawableManagementIcon.setTintMode(PorterDuff.Mode.SRC_IN);

                                                        tabLayout.getTabAt(0).setIcon(drawableHomeIcon);
                                                        tabLayout.getTabAt(1).setIcon(drawableChatIcon);
                                                        tabLayout.getTabAt(2).setIcon(drawableManagementIcon);*/
                                                    }
                                                    else{
                                                        if(vibrantLightSwatch == null){
                                                            collapsingToolbar.setContentScrimColor(dominantSwatch.getRgb());
                                                            collapsingToolbar.setStatusBarScrimColor(dominantSwatch.getRgb());
                                                            appBarLayout.setBackgroundColor(dominantSwatch.getRgb());

                                                            /*drawableHomeIcon.setTint(dominantSwatch.getTitleTextColor());
                                                            drawableHomeIcon.setTintMode(PorterDuff.Mode.SRC_IN);

                                                            drawableChatIcon.setTint(dominantSwatch.getTitleTextColor());
                                                            drawableChatIcon.setTintMode(PorterDuff.Mode.SRC_IN);

                                                            drawableManagementIcon.setTint(dominantSwatch.getTitleTextColor());
                                                            drawableManagementIcon.setTintMode(PorterDuff.Mode.SRC_IN);*/
                                                        }
                                                        else{
                                                            collapsingToolbar.setContentScrimColor(vibrantLightSwatch.getRgb());
                                                            collapsingToolbar.setStatusBarScrimColor(vibrantLightSwatch.getRgb());
                                                            /*appBarLayout.setBackgroundColor(vibrantLightSwatch.getRgb());

                                                            drawableHomeIcon.setTint(vibrantLightSwatch.getTitleTextColor());
                                                            drawableHomeIcon.setTintMode(PorterDuff.Mode.SRC_IN);

                                                            drawableChatIcon.setTint(vibrantLightSwatch.getTitleTextColor());
                                                            drawableChatIcon.setTintMode(PorterDuff.Mode.SRC_IN);

                                                            drawableManagementIcon.setTint(vibrantLightSwatch.getTitleTextColor());
                                                            drawableManagementIcon.setTintMode(PorterDuff.Mode.SRC_IN);*/
                                                        }

                                                        /*tabLayout.getTabAt(0).setIcon(drawableHomeIcon);
                                                        tabLayout.getTabAt(1).setIcon(drawableChatIcon);
                                                        tabLayout.getTabAt(2).setIcon(drawableManagementIcon);*/
                                                    }

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

}

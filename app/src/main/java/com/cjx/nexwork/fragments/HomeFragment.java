package com.cjx.nexwork.fragments;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.cjx.nexwork.R;
import com.cjx.nexwork.fragments.work.FragmentListWork;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Xavi on 08/04/2017.
 */

public class HomeFragment extends Fragment{

    View view;
    ViewPager mViewPager;
    Toolbar toolbar;
    TabLayout tabs;

    Drawable drawableHomeIcon;
    Drawable drawableChatIcon;
    Drawable drawableManagementIcon;

    public static HomeFragment newInstance() {
        return new HomeFragment();
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        tabs = (TabLayout) view.findViewById(R.id.home_tabs);

        tabs.addTab(tabs.newTab());
        tabs.addTab(tabs.newTab());
        tabs.addTab(tabs.newTab());
        tabs.addTab(tabs.newTab());

        drawableHomeIcon = getResources().getDrawable(R.drawable.home_icon);
        drawableChatIcon = getResources().getDrawable(R.drawable.chat_icon);
        drawableManagementIcon = getResources().getDrawable(R.drawable.account_profile_icon);

        mViewPager = (ViewPager) view.findViewById(R.id.home_pager);
        setupViewPager(mViewPager);
        tabs.setupWithViewPager(mViewPager);
        tabs.getTabAt(0).setIcon(drawableHomeIcon);
        tabs.getTabAt(1).setIcon(drawableChatIcon);
        tabs.getTabAt(2).setIcon(drawableManagementIcon);
        tabs.getTabAt(3).setIcon(R.drawable.account_profile_icon);

        return view;
    }


    private void setupViewPager(ViewPager viewPager) {
        HomeFragment.DemoCollectionPagerAdapter adapter = new HomeFragment.DemoCollectionPagerAdapter(getActivity().getSupportFragmentManager());
        adapter.addFragment(UserManagmentFragment.newInstance());
        adapter.addFragment(UserManagmentFragment.newInstance());
        adapter.addFragment(UserManagmentFragment.newInstance());
        adapter.addFragment(UserProfileFragment.newInstance(true));
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

}

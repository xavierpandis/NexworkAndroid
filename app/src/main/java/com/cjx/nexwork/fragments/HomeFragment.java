package com.cjx.nexwork.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import android.support.v7.view.menu.ShowableListMenu;
import android.support.v7.widget.ForwardingListener;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.cjx.nexwork.R;
import com.cjx.nexwork.fragments.management.ManagementFragment;
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

        getActivity().onSearchRequested();

        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        tabs = (TabLayout) view.findViewById(R.id.home_tabs);



        tabs.addTab(tabs.newTab());
        tabs.addTab(tabs.newTab());
        tabs.addTab(tabs.newTab());

        mViewPager = (ViewPager) view.findViewById(R.id.home_pager);
        setupViewPager(mViewPager);
        tabs.setupWithViewPager(mViewPager);

        tabs.getTabAt(0).setIcon(R.drawable.home_tab_selected);
        tabs.getTabAt(1).setIcon(R.drawable.management_tab_normal);
        tabs.getTabAt(2).setIcon(R.drawable.account_tab_normal);

        //tabs.setSelectedTabIndicatorColor(Color.parseColor("#DD050F48"));

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.d("position scrolled", String.valueOf(position));
                //getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            }

            @Override
            public void onPageSelected(int position) {
                Log.d("position page", String.valueOf(position));
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                Log.d("state", String.valueOf(state));
            }
        });

        tabs.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager) {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                super.onTabSelected(tab);
                int numTab = tab.getPosition();
                switch (numTab){
                    case 0:
                        tabs.getTabAt(0).setIcon(R.drawable.home_tab_selected);
                        tabs.getTabAt(1).setIcon(R.drawable.management_tab_normal);
                        tabs.getTabAt(2).setIcon(R.drawable.account_tab_normal);
                        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
                        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
                        break;
                    case 1:
                        tabs.getTabAt(0).setIcon(R.drawable.home_tab_normal);
                        tabs.getTabAt(1).setIcon(R.drawable.management_tab_selected);
                        tabs.getTabAt(2).setIcon(R.drawable.account_tab_normal);
                        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
                        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
                        break;
                    case 2:
                        tabs.getTabAt(0).setIcon(R.drawable.home_tab_normal);
                        tabs.getTabAt(1).setIcon(R.drawable.management_tab_normal);
                        tabs.getTabAt(2).setIcon(R.drawable.account_tab_selected);
                        if(((AppCompatActivity) getActivity()).getSupportActionBar().isShowing()){
                            ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
                        }
                        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
                        break;
                }
            }
        });

        return view;
    }

    private void setupViewPager(ViewPager viewPager) {
        HomeFragment.HomePagerAdapter adapter = new HomeFragment.HomePagerAdapter(getChildFragmentManager());
        adapter.addFragment(FeedFragment.newInstance());
        adapter.addFragment(ManagementFragment.newInstance());
        adapter.addFragment(UserProfileFragment.newInstance(true));
        viewPager.setAdapter(adapter);
    }

    public class HomePagerAdapter extends FragmentStatePagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public HomePagerAdapter(FragmentManager fm) {
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

package com.cjx.nexwork.fragments;

import android.content.Context;
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

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

        mViewPager = (ViewPager) view.findViewById(R.id.home_pager);
        setupViewPager(mViewPager);
        tabs.setupWithViewPager(mViewPager);
        tabs.getTabAt(0).setIcon(R.drawable.home_white);
        tabs.getTabAt(1).setIcon(R.drawable.tooltip_edit);
        tabs.getTabAt(2).setIcon(R.drawable.account_circle);

        tabs.setSelectedTabIndicatorColor(Color.parseColor("#DD050F48"));

        tabs.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager) {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                super.onTabSelected(tab);
                int numTab = tab.getPosition();
                switch (numTab){
                    case 0:
                        tabs.getTabAt(0).setIcon(R.drawable.home_white);
                        tabs.getTabAt(1).setIcon(R.drawable.tooltip_edit);
                        tabs.getTabAt(2).setIcon(R.drawable.account_circle);
                        break;
                    case 1:
                        tabs.getTabAt(0).setIcon(R.drawable.home);
                        tabs.getTabAt(1).setIcon(R.drawable.tooltip_edit_white);
                        tabs.getTabAt(2).setIcon(R.drawable.account_circle);
                        break;
                    case 2:
                        tabs.getTabAt(0).setIcon(R.drawable.home);
                        tabs.getTabAt(1).setIcon(R.drawable.tooltip_edit);
                        tabs.getTabAt(2).setIcon(R.drawable.account_circle_white);
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

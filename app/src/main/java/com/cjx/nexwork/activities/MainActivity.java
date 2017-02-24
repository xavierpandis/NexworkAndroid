package com.cjx.nexwork.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.cjx.nexwork.R;
import com.cjx.nexwork.fragments.UserProfileFragment;
import com.cjx.nexwork.managers.TokenStoreManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class MainActivity extends AppCompatActivity {

    Stack<Integer> pageHistory;
    int currentPage;
    boolean saveToHistory;
    ViewPager mViewPager;
    Toolbar toolbar;
    TabLayout tabs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tabs = (TabLayout) findViewById(R.id.tabs);
        tabs.addTab(tabs.newTab().setIcon(R.drawable.home_icon));
        tabs.addTab(tabs.newTab().setIcon(R.drawable.chat_icon));
        tabs.addTab(tabs.newTab().setIcon(R.drawable.account_profile_icon));
        tabs.addTab(tabs.newTab().setIcon(R.drawable.notification_icon));


       /* getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_main, UserProfileFragment.newInstance())
                .addToBackStack(null)
                .commit();*/

        mViewPager = (ViewPager) findViewById(R.id.pager);
        setupViewPager(mViewPager);
        tabs.setupWithViewPager(mViewPager);
        tabs.getTabAt(0).setIcon(R.drawable.home_icon);
        tabs.getTabAt(1).setIcon(R.drawable.home_icon);
        tabs.getTabAt(2).setIcon(R.drawable.home_icon);
        tabs.getTabAt(3).setIcon(R.drawable.home_icon);
    }

    private void setupViewPager(ViewPager viewPager) {
        DemoCollectionPagerAdapter adapter = new DemoCollectionPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new UserProfileFragment());
        adapter.addFragment(new UserProfileFragment());
        adapter.addFragment(new UserProfileFragment());
        adapter.addFragment(new UserProfileFragment());
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

        @Override
        public CharSequence getPageTitle(int position) {

            Log.d("xs",mFragmentList.get(position).getView()+"");
            String  spannableString = null;

            if (position == 0) {
            }
            if (position == 1) {
            }
            if (position == 2) {
            }
            return spannableString;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        final String accessToken =  preferences.getString("accessToken","");

        if(accessToken.equals("")){
            Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(loginIntent);
        }else{

            TokenStoreManager.getInstance().setAccessToken(preferences.getString("accessToken",""));
            TokenStoreManager.getInstance().setRefreshToken(preferences.getString("refreshToken",""));
            TokenStoreManager.getInstance().setTokenType(preferences.getString("tokenType",""));
            TokenStoreManager.getInstance().setUsername(preferences.getString("username",""));
            TokenStoreManager.getInstance().setContext(MainActivity.this);
        }

    }

    public void logOut(){
        SharedPreferences mySPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = mySPrefs.edit();
        editor.remove("accessToken");
        editor.remove("refreshToken");
        editor.remove("tokenType");
        editor.remove("username");
        editor.apply();
        TokenStoreManager.getInstance().setAccessToken("");
        TokenStoreManager.getInstance().setRefreshToken("");
        TokenStoreManager.getInstance().setTokenType("");
        Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(loginIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            logOut();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
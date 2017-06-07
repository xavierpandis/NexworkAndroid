package com.cjx.nexwork.activities;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;

import com.cjx.nexwork.R;
import com.cjx.nexwork.fragments.HomeFragment;
import com.cjx.nexwork.managers.TokenStoreManager;
import com.cjx.nexwork.util.CustomDatePicker;

import java.util.Stack;

public class MainActivity extends AppCompatActivity {

    Stack<Integer> pageHistory;
    int currentPage;
    boolean saveToHistory;
    ViewPager mViewPager;
    Toolbar toolbar;
    TabLayout tabs;
    public Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = getApplicationContext();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_main, HomeFragment.newInstance())
                .commit();

    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
           // doMySearch(query);
            Log.d("search",query);
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
        Log.d("nxw", item.getItemId()+"");
        if(id == android.R.id.home){
            Log.d("nxw", "back");
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        }
        else {
            super.onBackPressed();
            overridePendingTransition(R.anim.swap_in_bottom, R.anim.swap_out_bottom);
        }
    }
}
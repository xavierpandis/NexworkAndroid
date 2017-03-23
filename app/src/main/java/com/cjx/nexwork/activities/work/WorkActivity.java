package com.cjx.nexwork.activities.work;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.cjx.nexwork.R;
import com.cjx.nexwork.activities.LoginActivity;
import com.cjx.nexwork.fragments.work.FragmentCreateWork;
import com.cjx.nexwork.fragments.work.FragmentListWork;
import com.cjx.nexwork.managers.work.WorkCallback;
import com.cjx.nexwork.managers.work.WorkManager;
import com.cjx.nexwork.model.Work;

import java.util.Date;
import java.util.List;

public class WorkActivity extends AppCompatActivity{

    private List<Work> works;
    private FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("YOUR WORKS");

        setSupportActionBar(toolbar);

        FragmentListWork mainFragment = FragmentListWork.newInstance();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_work, mainFragment).commit();
    }
    @Override
    protected void onPostResume() {
        super.onPostResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_work, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.create_work) {

            FragmentCreateWork mainFragment = FragmentCreateWork.newInstance();

            getSupportFragmentManager().beginTransaction().add(R.id.fragment_work, mainFragment).commit();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}

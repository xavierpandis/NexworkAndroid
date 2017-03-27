package com.cjx.nexwork.activities.work;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
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

        getSupportFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right).replace(R.id.fragment_work, new FragmentListWork()).commit();
    }
    @Override
    protected void onPostResume() {
        super.onPostResume();
    }

}

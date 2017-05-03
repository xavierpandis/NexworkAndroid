package com.cjx.nexwork.fragments.study;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.cjx.nexwork.R;
import com.cjx.nexwork.adapters.StudyListAdapter;
import com.cjx.nexwork.adapters.WorkListAdapter;
import com.cjx.nexwork.fragments.work.FragmentCreateWork;
import com.cjx.nexwork.managers.study.StudyCallback;
import com.cjx.nexwork.managers.study.StudyManager;
import com.cjx.nexwork.managers.work.WorkCallback;
import com.cjx.nexwork.managers.work.WorkManager;
import com.cjx.nexwork.model.Study;

import java.util.ArrayList;
import java.util.List;

public class FragmentListStudy extends Fragment implements StudyCallback, View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private RecyclerView recyclerView;
    private View view;
    private FloatingActionButton floatingActionButton;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private String loginUser;
    private Boolean userConected;

    public FragmentListStudy() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public FragmentListStudy(String login, Boolean userConected) {
        this.loginUser = login;
        this.userConected = userConected;
    }

    // TODO: Rename and change types and number of parameters
    public static FragmentListStudy newInstance() {
        FragmentListStudy fragment = new FragmentListStudy();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_list_study,container, false);

        StudyManager.getInstance().getUserStudies(loginUser, this);

        List<Study> studyList = new ArrayList<>();

        Context context = view.getContext();
        recyclerView = (RecyclerView) view.findViewById(R.id.list_study);

        floatingActionButton = (FloatingActionButton) view.findViewById(R.id.btnAddJob);
        floatingActionButton.setOnClickListener(this);

        return view;
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onSuccess(List<Study> studyList) {
        Log.d("nxw", studyList.toString());

        recyclerView = (RecyclerView) view.findViewById(R.id.list_study);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        /*GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(layoutManager);*/
        recyclerView.setAdapter(new StudyListAdapter(studyList, this, getContext()));

    }

    @Override
    public void onFailure(Throwable t) {

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Do something that differs the Activity's menu here
        inflater.inflate(R.menu.menu_work, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.create_work) {

            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                    .replace(R.id.fragment_work, new FragmentCreateWork(), "listStudies")
                    .addToBackStack(null)
                    .commit();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnAddJob:
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                        .replace(R.id.fragment_work, new FragmentCreateWork())
                        .addToBackStack(null)
                        .commit();
                break;
        }
    }
}

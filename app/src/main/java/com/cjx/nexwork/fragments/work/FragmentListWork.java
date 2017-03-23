package com.cjx.nexwork.fragments.work;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cjx.nexwork.R;
import com.cjx.nexwork.activities.work.WorkActivity;
import com.cjx.nexwork.adapters.WorkListAdapter;
import com.cjx.nexwork.managers.work.WorkCallback;
import com.cjx.nexwork.managers.work.WorkManager;
import com.cjx.nexwork.model.Work;

import java.util.ArrayList;
import java.util.List;

public class FragmentListWork extends Fragment implements WorkCallback{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private RecyclerView recyclerView;
    private View view;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentListWork() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static FragmentListWork newInstance() {
        FragmentListWork fragment = new FragmentListWork();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        WorkManager.getInstance().getWorksUser(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_list_work,container, false);

        List<Work> workList = new ArrayList<>();

        Context context = view.getContext();
        recyclerView = (RecyclerView) view.findViewById(R.id.list_work);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(new WorkListAdapter(workList, this, view.getContext()));

        return view;
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
    public void onSuccess(List<Work> workList) {
        Log.d("nxw", workList.toString());

        recyclerView = (RecyclerView) view.findViewById(R.id.list_work);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new WorkListAdapter(workList, this, getContext()));

    }

    @Override
    public void onFailure(Throwable t) {

    }
}

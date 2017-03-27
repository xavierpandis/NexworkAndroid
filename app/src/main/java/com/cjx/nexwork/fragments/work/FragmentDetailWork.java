package com.cjx.nexwork.fragments.work;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cjx.nexwork.R;
import com.cjx.nexwork.managers.work.WorkDetailCallback;
import com.cjx.nexwork.managers.work.WorkManager;
import com.cjx.nexwork.model.Work;
import com.cjx.nexwork.util.CustomProperties;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Xavi on 25/03/2017.
 */

public class FragmentDetailWork extends Fragment implements WorkDetailCallback {

    public static final String WORK_ID = "0";

    private TextView workPosition;
    private TextView workWorker;
    private ImageView workerImage;
    private TextView dateStart;
    private TextView dateEnded;
    private TextView workDescription;

    public FragmentDetailWork() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null){
            WorkManager.getInstance().getDetailWork(getArguments().getLong(WORK_ID), this);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_work_detail,container, false);

        Log.d("nxw", "DETAIL WORK");

        workPosition = (TextView) view.findViewById(R.id.workPosition);
        workWorker = (TextView) view.findViewById(R.id.workWorker);
        workerImage = (ImageView) view.findViewById(R.id.workUserImage);
        dateStart = (TextView) view.findViewById(R.id.fechaInicio);
        dateEnded = (TextView) view.findViewById(R.id.fechaFinal);
        workDescription = (TextView) view.findViewById(R.id.workDescription);

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
    public void onSuccess(Work work) {
        Log.d("nxw", work.toString());

        workPosition.setText(work.getCargo());
        workWorker.setText(work.getWorker().getFirstName() + " " + work.getWorker().getLastName());
        dateStart.setText(work.getFechaInicio().toString());
        if(work.getActualmente()) dateEnded.setText(R.string.current_work);
        else dateEnded.setText(work.getFechaFin().toString());
        workDescription.setText(work.getDescripcionCargo());

        Picasso.with(getContext())
                .load(CustomProperties.baseUrl+"/"+work.getWorker().getImagen())
                .resize(100, 100)
                .into(workerImage);
    }

    @Override
    public void onFailure(Throwable t) {

    }
}

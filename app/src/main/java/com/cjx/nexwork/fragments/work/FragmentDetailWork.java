package com.cjx.nexwork.fragments.work;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ScrollingView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.cjx.nexwork.R;
import com.cjx.nexwork.WorkaroundMapFragment;
import com.cjx.nexwork.activities.work.WorkActivity;
import com.cjx.nexwork.managers.work.WorkDetailCallback;
import com.cjx.nexwork.managers.work.WorkManager;
import com.cjx.nexwork.model.Work;
import com.cjx.nexwork.util.CustomProperties;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Xavi on 25/03/2017.
 */

public class FragmentDetailWork extends Fragment implements WorkDetailCallback, OnMapReadyCallback {

    public static final String WORK_ID = "0";

    private TextView workPosition;
    private TextView workWorker;
    private ImageView workerImage;
    private TextView dateStart;
    private TextView dateEnded;
    private TextView workDescription;
    private TextView txtWorkCompany;
    private TextView workCompany;
    private ActionBar actionBar;

    private Work work;

    private Toolbar toolbar;

    private GoogleMap mMap;
    SupportMapFragment mapFragment;

    public FragmentDetailWork() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null){
            WorkManager.getInstance().getDetailWork(getArguments().getLong(WORK_ID), this);
        }


       // actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_work_detail,container, false);

        Log.d("nxw", "DETAIL WORK");

        mapFragment = (WorkaroundMapFragment) getChildFragmentManager().findFragmentById(R.id.mapWorkDetail);
        final ScrollView mScrollView = (ScrollView) view.findViewById(R.id.scrollviewDetail);

        ((WorkaroundMapFragment) getChildFragmentManager().findFragmentById(R.id.mapWorkDetail)).setListener(new WorkaroundMapFragment.OnTouchListener() {
            @Override
            public void onTouch() {
                mScrollView.requestDisallowInterceptTouchEvent(true);
            }
        });

        workPosition = (TextView) view.findViewById(R.id.workPosition);
        workWorker = (TextView) view.findViewById(R.id.workWorker);
        workerImage = (ImageView) view.findViewById(R.id.workUserImage);
        dateStart = (TextView) view.findViewById(R.id.fechaInicio);
        dateEnded = (TextView) view.findViewById(R.id.fechaFinal);
        workDescription = (TextView) view.findViewById(R.id.workDescription);

        txtWorkCompany = (TextView) view.findViewById(R.id.txtWorkCompany);
        workCompany = (TextView) view.findViewById(R.id.workCompany);

        return view;
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onSuccess(Work work) {
        Log.d("nxw", work.toString());

        this.work = work;
        SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
        mapFragment.getMapAsync(this);

        workPosition.setText(work.getCargo());
        //workPosition.setVisibility(View.GONE);
        workWorker.setText(work.getWorker().getFirstName() + " " + work.getWorker().getLastName());
        dateStart.setText(formateador.format(work.getFechaInicio()));
        if(work.getActualmente()) dateEnded.setText(R.string.current_work);
        else {
            dateEnded.setText(formateador.format(work.getFechaFin()));
        }

        workDescription.setText(work.getDescripcionCargo());

        if(work.getCompany() == null){
            workCompany.setText("No establecida");
        }else workCompany.setText(work.getCompany().getNombre());

        Picasso.with(getContext())
                .load(CustomProperties.baseUrl+"/"+work.getWorker().getImagen())
                .resize(200, 200)
                .into(workerImage);

    }

    @Override
    public void onFailure(Throwable t) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if(this.work.getCompany() == null){
            LatLng sydney = new LatLng(-34, 151);
            mMap.addMarker(new MarkerOptions().position(sydney).title("Sidney"));
            mMap.animateCamera(CameraUpdateFactory.newLatLng(sydney));
        }
        else{
            if(this.work.getCompany().getLatitud() == null){
                LatLng sydney = new LatLng(-34, 151);
                mMap.addMarker(new MarkerOptions().position(sydney).title("Sidney"));
                mMap.animateCamera(CameraUpdateFactory.newLatLng(sydney));
            }
            else{
                LatLng latLng = new LatLng(Double.parseDouble(this.work.getCompany().getLatitud()),
                        Double.parseDouble(this.work.getCompany().getLongitud()));
                mMap.addMarker(new MarkerOptions().position(latLng).title(this.work.getCompany().getNombre()));
                CameraUpdate yourLocation = CameraUpdateFactory.newLatLngZoom(latLng, 15);
                mMap.animateCamera(yourLocation);
            }
        }
    }
}

package com.cjx.nexwork.fragments.work;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;

import com.cjx.nexwork.R;
import com.cjx.nexwork.activities.MainActivity;
import com.cjx.nexwork.managers.work.WorkCallback;
import com.cjx.nexwork.managers.work.WorkDetailCallback;
import com.cjx.nexwork.managers.work.WorkManager;
import com.cjx.nexwork.model.Work;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Xavi on 28/03/2017.
 */

public class FragmentEditWork extends Fragment implements View.OnClickListener, CheckBox.OnCheckedChangeListener, WorkDetailCallback {

    public static final String WORK = null;

    private Long workId;

    private Work work;

    Calendar startCalendar = Calendar.getInstance();
    Calendar endCalendar = Calendar.getInstance();

    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    private EditText editPosition;
    private EditText editDateStarted;
    private EditText editDateEnd;
    private CheckBox checkWorking;
    private EditText editDescription;
    private View view;

    private boolean editPetition = false;

    public FragmentEditWork() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        if(getArguments() != null){
            this.workId = getArguments().getLong(WORK);
            WorkManager.getInstance().getDetailWork(this.workId, this);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_create_work,container, false);

        editPosition = (EditText) view.findViewById(R.id.editPosition);
        editDateStarted = (EditText) view.findViewById(R.id.editDateStarted);
        editDateEnd = (EditText) view.findViewById(R.id.editDateEnd);
        checkWorking = (CheckBox) view.findViewById(R.id.checkWorking);
        editDescription = (EditText) view.findViewById(R.id.editDescription);

        Button btnAddJob = (Button) view.findViewById(R.id.btn_add_job);
        btnAddJob.setText("GUARDAR");
        btnAddJob.setOnClickListener(this);

        editDateStarted.setFocusable(false);
        editDateStarted.setClickable(false);
        editDateEnd.setFocusable(false);
        editDateEnd.setClickable(false);

        editDateStarted.setOnClickListener(this);
        editDateEnd.setOnClickListener(this);
        checkWorking.setOnCheckedChangeListener(this);

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
        /*ViewPager viewPager  = (ViewPager) getActivity().findViewById(R.id.pager);
        viewPager.setVisibility(View.VISIBLE);
        FrameLayout frameLayout = (FrameLayout) getActivity().findViewById(R.id.relfragmentapp);
        frameLayout.setVisibility(View.GONE);*/
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        super.onDetach();
    }

    @Override
    public void onSuccess(Work work) {
        Log.d("nxw", ""+editPetition);
        if(!editPetition){
            this.work = work;

            SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");

            editPosition.setText(work.getCargo());
            editDateStarted.setText(formateador.format(work.getFechaInicio()));
            startCalendar.setTime(work.getFechaInicio());
            if(!work.getActualmente()){
                editDateEnd.setText(formateador.format(work.getFechaFin()));
                endCalendar.setTime(work.getFechaFin());
                checkWorking.setChecked(false);
            }
            else {
                checkWorking.setChecked(true);
            }
            editDescription.setText(work.getDescripcionCargo());
        }
        else{
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.enter, R.anim.exit)
                    .replace(R.id.fragment_work, new FragmentListWork(), "editFragment")
                    .addToBackStack(null)
                    .commit();
        }

    }

    @Override
    public void onFailure(Throwable t) {
        Log.d("nxw", t.getMessage());
    }

    @Override
    public void onClick(final View v) {
        if(v.getId() == R.id.editPosition){
            Log.d("nxw", "position");
        }
        switch (v.getId())
        {
            case R.id.btn_add_job:
                editJob();
                break;
            case R.id.editPosition:
                Log.d("nxw", "position");
                break;
            case R.id.editDateStarted:
                Log.d("nxw", "start");
                new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        startCalendar.set(Calendar.YEAR, year);
                        startCalendar.set(Calendar.MONTH, month);
                        startCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                        EditText editText = (EditText) v;
                        editText.setText(sdf.format(startCalendar.getTime()));

                    }
                }, startCalendar.get(Calendar.YEAR), startCalendar.get(Calendar.MONTH),
                        startCalendar.get(Calendar.DAY_OF_MONTH)).show();
                break;
            case R.id.editDateEnd:
                Log.d("nxw", "end");
                new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        endCalendar.set(Calendar.YEAR, year);
                        endCalendar.set(Calendar.MONTH, month);
                        endCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                        EditText editText = (EditText) v;
                        editText.setText(sdf.format(endCalendar.getTime()));
                    }
                }, endCalendar.get(Calendar.YEAR), endCalendar.get(Calendar.MONTH),
                        endCalendar.get(Calendar.DAY_OF_MONTH)).show();
                break;
        }
    }

    private void editJob() {


        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        try{
            work.setCargo(editPosition.getText().toString());
            Date startDate = new Date();
            Date endDate = new Date();
            if(!checkWorking.isChecked()) {
                endDate = dateFormat.parse(editDateEnd.getText().toString());
                work.setFechaFin(endDate);
            }else work.setActualmente(true);

            if(!editDescription.getText().toString().isEmpty()) work.setDescripcionCargo(editDescription.getText().toString());
            startDate = dateFormat.parse(editDateStarted.getText().toString());
            work.setFechaInicio(startDate);
            editPetition = true;
            WorkManager.getInstance().editWork(work, this);


        }
        catch (Exception e){
            e.printStackTrace();
        }


    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        final TableRow rowEnd = (TableRow) view.findViewById(R.id.rowEnd);
        switch (buttonView.getId()){
            case R.id.checkWorking:
                if(isChecked){
                    rowEnd.animate().alpha(0.0f).setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            rowEnd.setVisibility(View.GONE);
                        }
                    });
                }
                else {
                    rowEnd.animate().alpha(1.0f).setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            rowEnd.setVisibility(View.VISIBLE);
                        }
                    });
                }
                break;
        }
    }
}

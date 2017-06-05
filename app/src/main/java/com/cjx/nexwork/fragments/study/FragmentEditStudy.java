package com.cjx.nexwork.fragments.study;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TableRow;

import com.cjx.nexwork.R;
import com.cjx.nexwork.managers.TokenStoreManager;
import com.cjx.nexwork.managers.study.StudyDetailCallback;
import com.cjx.nexwork.managers.study.StudyManager;
import com.cjx.nexwork.managers.work.WorkDetailCallback;
import com.cjx.nexwork.managers.work.WorkManager;
import com.cjx.nexwork.model.Study;
import com.cjx.nexwork.model.Work;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Xavi on 28/03/2017.
 */

public class FragmentEditStudy extends Fragment implements View.OnClickListener, CheckBox.OnCheckedChangeListener, StudyDetailCallback {

    public static final String STUDY = null;

    private Long studyId;

    private Study study;

    Calendar startCalendar = Calendar.getInstance();
    Calendar endCalendar = Calendar.getInstance();

    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    private EditText editPosition;
    private EditText editDateStarted;
    private EditText editDateEnd;
    private CheckBox checkWorking;
    private EditText editDescription;

    private boolean editPetition = false;

    public FragmentEditStudy() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null){
            this.studyId = getArguments().getLong(STUDY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_work,container, false);

        StudyManager.getInstance().getDetailStudy(this.studyId, this);

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
        super.onDetach();
    }

    @Override
    public void onSuccess(Study study) {
        Log.d("nxw", ""+editPetition);
        if(!editPetition){
            this.study = study;

            SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");

            editPosition.setText(study.getCurso());
            editDateStarted.setText(formateador.format(study.getFechaInicio()));
            startCalendar.setTime(study.getFechaInicio());
            if(study.getActualmente() == null){
                checkWorking.setChecked(false);
            }
            else if(!study.getActualmente()){
                editDateEnd.setText(formateador.format(study.getFechaFinal()));
                endCalendar.setTime(study.getFechaFinal());
                checkWorking.setChecked(false);
            }
            else {
                checkWorking.setChecked(true);
            }
            editDescription.setText(study.getDescripcion());
        }
        else{
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                    .replace(R.id.content_main, new FragmentListStudy(TokenStoreManager.getInstance().getUsername(), true), "editFragment")
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
                editStudy();
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

    private void editStudy() {


        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        try{
            study.setCurso(editPosition.getText().toString());
            Date startDate = new Date();
            Date endDate = new Date();
            if(!checkWorking.isChecked()) {
                endDate = dateFormat.parse(editDateEnd.getText().toString());
                study.setFechaFinal(endDate);
            }else study.setActualmente(true);

            if(!editDescription.getText().toString().isEmpty()) study.setDescripcion(editDescription.getText().toString());
            startDate = dateFormat.parse(editDateStarted.getText().toString());
            study.setFechaInicio(startDate);
            editPetition = true;
            StudyManager.getInstance().editStudy(study, this);


        }
        catch (Exception e){
            e.printStackTrace();
        }


    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        final TableRow rowEnd = (TableRow) getView().findViewById(R.id.rowEnd);
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

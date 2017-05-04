package com.cjx.nexwork.fragments.study;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cjx.nexwork.R;
import com.cjx.nexwork.managers.study.StudyDetailCallback;
import com.cjx.nexwork.managers.study.StudyManager;
import com.cjx.nexwork.managers.work.WorkDetailCallback;
import com.cjx.nexwork.managers.work.WorkManager;
import com.cjx.nexwork.model.Study;
import com.cjx.nexwork.model.Work;
import com.cjx.nexwork.util.CustomProperties;
import com.squareup.picasso.Picasso;

import org.joda.time.DateTime;
import org.joda.time.Instant;
import org.joda.time.Interval;
import org.joda.time.LocalDate;
import org.joda.time.Period;
import org.joda.time.PeriodType;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Xavi on 25/03/2017.
 */

public class FragmentDetailStudy extends Fragment implements StudyDetailCallback {

    public static final String STUDY_ID = "0";

    private TextView studyName;
    private TextView studyStudent;
    private ImageView studentImage;
    private TextView studyDescription;
    private TextView studyCenter;
    private TextView studyGrade;
    private TextView studyDuration;
    private TextView durationTimeStudy;

    public FragmentDetailStudy() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null){

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_study_detail, container, false);

        studyName = (TextView) view.findViewById(R.id.studyName);
        studyStudent = (TextView) view.findViewById(R.id.studyStudent);
        studentImage = (ImageView) view.findViewById(R.id.StudentUserImage);
        studyDescription = (TextView) view.findViewById(R.id.studyDescription);
        studyCenter = (TextView) view.findViewById(R.id.centerStudy);
        studyGrade = (TextView) view.findViewById(R.id.studyGrade);
        studyDuration = (TextView) view.findViewById(R.id.studyDuration);
        durationTimeStudy = (TextView) view.findViewById(R.id.durationTimeStudy);

        StudyManager.getInstance().getDetailStudy(getArguments().getLong(STUDY_ID), this);

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
        Log.d("nxw", study.toString());

        studyName.setText(study.getCurso());
        studyStudent.setText(study.getUser().getFirstName().concat(study.getUser().getLastName()));

        Picasso.with(getContext())
                .load(CustomProperties.baseUrl+"/"+study.getUser().getImagen())
                .resize(200, 200)
                .error(R.drawable.default_user_image)
                .placeholder(R.drawable.default_user_image)
                .into(studentImage);

        SimpleDateFormat format = new SimpleDateFormat("dd MMM yyyy");
        String studyDurationTxt = "";
        if(study.getFechaInicio() == null) studyDurationTxt = studyDurationTxt.concat("");
        else studyDurationTxt = studyDurationTxt.concat(format.format(study.getFechaInicio()));

        studyDurationTxt = studyDurationTxt.concat(" - ");

        if(study.getActualmente() == null){
            studyDurationTxt = studyDurationTxt.concat("");
        }
        else{
            DateFormat df = new SimpleDateFormat("dd MM yyyy");
            String dateToday = df.format(Calendar.getInstance().getTime());
            int months = 0;
            if(study.getActualmente()){
                months = new Period(new LocalDate(study.getFechaInicio()), LocalDate.now(), PeriodType.months()).getMonths();
                studyDurationTxt = studyDurationTxt.concat(getString(R.string.current_work));
            }
            else{
                months = new Period(new LocalDate(study.getFechaInicio()), new LocalDate(study.getFechaFinal()), PeriodType.months()).getMonths();
                studyDurationTxt = studyDurationTxt.concat(format.format(study.getFechaFinal()));
            }
            /*DateTime timeInicio = new DateTime(study.getFechaInicio());
            Interval interval = new Interval(timeInicio, new Instant());*/
            durationTimeStudy.setText(months+" months");
        }

        studyDuration.setText(studyDurationTxt);

        if(study.getNota() == null) studyGrade.setText("Not graded");
        else studyGrade.setText(study.getNota().toString());

        studyDescription.setText(study.getDescripcion());

        if(study.getCenter() != null) studyCenter.setText(study.getCenter().getNombre());
        else studyCenter.setText("Not assigned");

    }

    @Override
    public void onFailure(Throwable t) {

    }
}

package com.cjx.nexwork.activities.study;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.cjx.nexwork.R;
import com.cjx.nexwork.managers.study.StudyDetailCallback;
import com.cjx.nexwork.managers.study.StudyManager;
import com.cjx.nexwork.model.Study;

import java.util.Date;

public class StudyDetailActivity extends AppCompatActivity implements StudyDetailCallback {
    private Long id;

    private TextView nameCourse;
    private TextView fechaInicio;
    private TextView fechaFinal;
    private TextView studyDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_detail);

        Intent intent = getIntent();
        id = intent.getLongExtra("id", 1);

        nameCourse = (TextView) findViewById(R.id.nameCourse);
        fechaInicio = (TextView) findViewById(R.id.fechaInicio);
        fechaFinal = (TextView) findViewById(R.id.fechaFinal);
        studyDescription = (TextView) findViewById(R.id.studyDescription);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        StudyManager.getInstance(this.getApplicationContext()).getDetailStudy(id, StudyDetailActivity.this);
    }

    @Override
    public void onSuccess(Study study) {
        nameCourse.setText(study.getCurso());
        fechaInicio.setText(study.getFechaInicio().toString());

        Boolean actually = false;

        if(study.getActualmente() == null || !study.getActualmente()){
            actually = false;
        }else{
            actually = true;
        }

        if(actually){
            fechaFinal.setText("Actualmente");
        }
        else{
            Date ended = study.getFechaFinal();
            fechaFinal.setText(ended.toString());
        }
        studyDescription.setText(study.getDescripcion());
    }

    @Override
    public void onFailure(Throwable t) {

    }
}

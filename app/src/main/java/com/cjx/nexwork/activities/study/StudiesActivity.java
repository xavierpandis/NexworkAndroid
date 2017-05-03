package com.cjx.nexwork.activities.study;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.cjx.nexwork.R;
import com.cjx.nexwork.activities.LoginActivity;
import com.cjx.nexwork.managers.TokenStoreManager;
import com.cjx.nexwork.managers.study.StudyManager;
import com.cjx.nexwork.model.Study;
import com.cjx.nexwork.managers.study.StudyCallback;

import java.util.Date;
import java.util.List;

public class StudiesActivity extends AppCompatActivity implements StudyCallback {

    private List<Study> studies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studies);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("YOUR STUDIES");
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        StudyManager.getInstance()
                .getUserStudies(TokenStoreManager.getInstance().getUsername(),StudiesActivity.this);
    }

    @Override
    public void onSuccess(List<Study> listStudies) {
        studies = listStudies;

        ListView listStudyView = (ListView) findViewById(R.id.list_study);

        StudiesAdapter adapter = new StudiesAdapter(this, studies);

        listStudyView.setAdapter(adapter);

        listStudyView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent detailIntent = new Intent(StudiesActivity.this, StudyDetailActivity.class);
                detailIntent.putExtra("id", studies.get(position).getId());
                startActivity(detailIntent);
            }
        });

    }

    @Override
    public void onFailure(Throwable t) {
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
        finish();
    }

    public class StudiesAdapter extends BaseAdapter {

        private Context context;
        private List<Study> studyList;

        public StudiesAdapter(Context context, List<Study> studyList) {
            this.context = context;
            this.studyList = studyList;
        }

        @Override public int getCount() { return studyList.size(); }
        @Override public Object getItem(int position) { return studyList.get(position); }
        @Override public long getItemId(int position) {
            Study study = studyList.get(position);
            return study.getId();
        }

        public class ViewHolder{
            public TextView studyName;
            public TextView studyDateStarted;
            public TextView studyDateEnded;
            public TextView studyDescription;
            public TextView studyQualification;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // RECICLAT DE VISTES
            View myView = convertView;
            if(myView == null){
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                myView = inflater.inflate(R.layout.list_study, parent, false);
                ViewHolder holder = new ViewHolder();
                holder.studyName = (TextView) myView.findViewById(R.id.nameStudy);
                holder.studyDateStarted = (TextView) myView.findViewById(R.id.dateStarted);
                holder.studyDateEnded = (TextView) myView.findViewById(R.id.dateEnded);
                holder.studyDescription = (TextView) myView.findViewById(R.id.description);
                holder.studyQualification = (TextView) myView.findViewById(R.id.qualification);
                myView.setTag(holder);
            }

            ViewHolder holder = (ViewHolder) myView.getTag();

            Study study = studyList.get(position);
            String name = study.getCurso();
            holder.studyName.setText(name);
            Date started = study.getFechaInicio();
            holder.studyDateStarted.setText(started.toString());

            Boolean actually = false;

            if(study.getActualmente() == null || !study.getActualmente()){
                actually = false;
            }else{
                actually = true;
            }

            if(actually){
                holder.studyDateEnded.setText("Actualmente");
            }
            else{
                Date ended = study.getFechaFinal();
                holder.studyDateEnded.setText(ended.toString());
            }

            Float qualification = study.getNota();
            holder.studyQualification.setText(qualification.toString());
            String description = study.getDescripcion();
            holder.studyDescription.setText(description);

            return myView;
        }
    }

}

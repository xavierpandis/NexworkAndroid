package com.cjx.nexwork.activities.work;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.cjx.nexwork.R;
import com.cjx.nexwork.activities.LoginActivity;
import com.cjx.nexwork.managers.work.WorkCallback;
import com.cjx.nexwork.managers.work.WorkManager;
import com.cjx.nexwork.model.Work;

import java.util.Date;
import java.util.List;

public class WorkActivity extends AppCompatActivity implements WorkCallback{

    private List<Work> works;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("YOUR WORKS");
    }
    @Override
    protected void onPostResume() {
        super.onPostResume();
        WorkManager.getInstance().getWorksUser(WorkActivity.this);
    }

    @Override
    public void onSuccess(List<Work> workList) {
        works = workList;

        ListView listWorkView = (ListView) findViewById(R.id.list_work);

        WorkActivity.WorksAdapter adapter = new WorkActivity.WorksAdapter(this, works);

        listWorkView.setAdapter(adapter);

        listWorkView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent detailIntent = new Intent(WorkActivity.this, WorkDetailActivity.class);
                detailIntent.putExtra("id", works.get(position).getId());
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

    public class WorksAdapter extends BaseAdapter {

        private Context context;
        private List<Work> workList;

        public WorksAdapter(Context context, List<Work> workList) {
            this.context = context;
            this.workList = workList;
        }

        @Override public int getCount() { return workList.size(); }
        @Override public Object getItem(int position) { return workList.get(position); }
        @Override public long getItemId(int position) {
            Work work = workList.get(position);
            return work.getId();
        }

        public class ViewHolder{
            public TextView workName;
            public TextView workDateStarted;
            public TextView workDateEnded;
            public TextView workDescription;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // RECICLAT DE VISTES
            View myView = convertView;
            if(myView == null){
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                myView = inflater.inflate(R.layout.list_work, parent, false);
                WorkActivity.WorksAdapter.ViewHolder holder = new WorkActivity.WorksAdapter.ViewHolder();
                holder.workName = (TextView) myView.findViewById(R.id.nameCargo);
                holder.workDateStarted = (TextView) myView.findViewById(R.id.dateStartedWork);
                holder.workDateEnded = (TextView) myView.findViewById(R.id.dateEndedWork);
                holder.workDescription = (TextView) myView.findViewById(R.id.descriptionWork);
                myView.setTag(holder);
            }

            WorkActivity.WorksAdapter.ViewHolder holder = (WorkActivity.WorksAdapter.ViewHolder) myView.getTag();

            Work work = workList.get(position);
            String name = "";
            if(work.getCargo() == null || work.getCargo().isEmpty()){
                name = "Sin nombre de cargo";
            } else {
                name = work.getCargo();
            }
            holder.workName.setText(name);
            Date started = work.getFechaInicio();
            holder.workDateStarted.setText(started.toString());

            Boolean actually = false;

            if(work.getActualmente() == null || !work.getActualmente()){
                actually = false;
            }else{
                actually = true;
            }

            if(actually){
                holder.workDateEnded.setText("Actualmente");
            }
            else{
                Date ended = work.getFechaFin();
                holder.workDateEnded.setText(ended.toString());
            }

            String description = work.getDescripcionCargo();
            holder.workDescription.setText(description);

            return myView;
        }
    }
}

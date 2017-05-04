package com.cjx.nexwork.adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cjx.nexwork.R;
import com.cjx.nexwork.fragments.study.FragmentDetailStudy;
import com.cjx.nexwork.fragments.work.FragmentDetailWork;
import com.cjx.nexwork.model.Study;
import com.cjx.nexwork.model.Work;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by xavi on 18/02/2017.
 */

public class StudyListAdapter extends RecyclerView.Adapter<StudyListAdapter.ViewHolder> {
    private List<Study> studyList;
    Fragment fragmentOne;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public final View mView;
        public TextView studyName;
        public TextView studyDateStarted;
        public TextView studyDateEnded;
        public TextView studyDescription;
        public TextView studyQualification;
        public Study study;

        public ViewHolder(View v) {
            super(v);
            mView = v;
            studyName = (TextView) v.findViewById(R.id.nameStudy);
            studyDateStarted = (TextView) v.findViewById(R.id.dateStarted);
            studyDateEnded = (TextView) v.findViewById(R.id.dateEnded);
            studyDescription = (TextView) v.findViewById(R.id.description);
            studyQualification = (TextView) v.findViewById(R.id.qualification);
        }
    }

    Context context;

    public void remove(String item) {
        int position = studyList.indexOf(item);
        studyList.remove(position);
        notifyItemRemoved(position);
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public StudyListAdapter(List<Study> studies, Fragment fragmentOne, Context cntxt) {
        studyList = studies;
        this.fragmentOne = fragmentOne;
        context = cntxt;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public StudyListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.study_list, parent, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.study = studyList.get(position);
        holder.studyName.setText(studyList.get(position).getCurso());

        Date start = studyList.get(position).getFechaInicio();
        Date end = studyList.get(position).getFechaFinal();

        SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");

        holder.studyDateStarted.setText(formateador.format(start));
        if (studyList.get(position).getActualmente() != null) {
            if(studyList.get(position).getActualmente()){
                holder.studyDateEnded.setText(R.string.current_work);
            }
            else{
                holder.studyDateEnded.setText(formateador.format(end));
            }
        }
        else holder.studyDateEnded.setText(formateador.format(end));

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new FragmentDetailStudy();
                Bundle args = new Bundle();
                args.putLong(FragmentDetailStudy.STUDY_ID, holder.study.getId());
                fragment.setArguments(args);
                FragmentTransaction transaction = fragmentOne.getActivity().getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                transaction.replace(R.id.content_main, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return studyList.size();
    }
}

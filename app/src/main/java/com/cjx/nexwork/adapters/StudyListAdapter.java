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
import com.cjx.nexwork.fragments.work.FragmentDetailWork;
import com.cjx.nexwork.model.Work;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by xavi on 18/02/2017.
 */

public class StudyListAdapter extends RecyclerView.Adapter<StudyListAdapter.ViewHolder> {
    private List<Work> workList;
    Fragment fragmentOne;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public final View mView;
        public TextView positionWork;
        public TextView startedDate;
        public TextView endDate;
        public TextView current;
        public Work work;

        public ViewHolder(View v) {
            super(v);
            mView = v;
            positionWork = (TextView) v.findViewById(R.id.workPosition);
            startedDate = (TextView) v.findViewById(R.id.startDate);
            endDate = (TextView) v.findViewById(R.id.endDate);
            current = (TextView) v.findViewById(R.id.currentWork);
        }
    }

    Context context;

    public void removeItem(int position) {
        workList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, workList.size());
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public StudyListAdapter(List<Work> works, Fragment fragmentOne, Context cntxt) {
        workList = works;
        this.fragmentOne = fragmentOne;
        context = cntxt;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public StudyListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.work_list, parent, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.work = workList.get(position);
        holder.positionWork.setText(workList.get(position).getCargo());

        Date start = workList.get(position).getFechaInicio();
        Date end = workList.get(position).getFechaFin();

        SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");

        holder.startedDate.setText(formateador.format(start));
        if(workList.get(position).getActualmente()){
            holder.endDate.setVisibility(View.GONE);
            holder.current.setText(R.string.current_work);
        }
        else{
            holder.endDate.setText(formateador.format(end));
            holder.current.setVisibility(View.GONE);
        }

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new FragmentDetailWork();
                Bundle args = new Bundle();
                args.putLong(FragmentDetailWork.WORK_ID, holder.work.getId());
                fragment.setArguments(args);
                FragmentTransaction transaction = fragmentOne.getActivity().getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                transaction.replace(R.id.fragment_work, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return workList.size();
    }
}

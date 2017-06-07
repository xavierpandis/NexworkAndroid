package com.cjx.nexwork.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cjx.nexwork.R;
import com.cjx.nexwork.fragments.work.FragmentDetailWork;
import com.cjx.nexwork.model.Work;
import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by xavi on 18/02/2017.
 */

public class WorkListAdapter extends RecyclerView.Adapter<WorkListAdapter.ViewHolder> {
    private List<Work> workList;
    Fragment fragmentOne;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public final View mView;
        public TextView positionWork;
        public TextView companyWork;
        public TextView dateWork;
        public Work work;

        public ViewHolder(View v) {
            super(v);
            mView = v;
            positionWork = (TextView) v.findViewById(R.id.workPosition);
            companyWork = (TextView) v.findViewById(R.id.companyWork);
            dateWork = (TextView) v.findViewById(R.id.dateWork);
        }
    }

    Context context;

    public void removeItem(int position) {
        workList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, workList.size());
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public WorkListAdapter(List<Work> works, Fragment fragmentOne, Context cntxt) {
        workList = works;
        this.fragmentOne = fragmentOne;
        context = cntxt;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public WorkListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.work_list, parent, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        holder.work = workList.get(position);

        holder.positionWork.setText(workList.get(position).getCargo());

        if(workList.get(position).getCompany() == null){
            holder.companyWork.setText(fragmentOne.getResources().getString(R.string.no_company));
        }else{
            if(workList.get(position).getCompany().getUbicacion() != null){
                holder.companyWork.setText(workList.get(position).getCompany().getNombre() +
                        " ("+workList.get(position).getCompany().getUbicacion()+")");
            }else{
                holder.companyWork.setText(workList.get(position).getCompany().getNombre());
            }
            //holder.companyWork.setText(workList.get(position).getCompany().getNombre());
        }

        //dateWork = (TextView) v.findViewById(R.id.dateWork);

        String dateWorkString = "";

        Date start = workList.get(position).getFechaInicio();
        Date end = workList.get(position).getFechaFin();

        SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");

        dateWorkString.concat(formateador.format(start));

        //holder.startedDate.setText(formateador.format(start));
        if(workList.get(position).getActualmente()){
            //holder.endDate.setVisibility(View.GONE);
            //holder.current.setText(R.string.current_work);
            String current = fragmentOne.getResources().getString(R.string.current_work);
            dateWorkString.concat(" to ");
            dateWorkString.concat(current);
        }
        else{
            dateWorkString.concat(" to ");
            dateWorkString.concat(formateador.format(end));
            //holder.endDate.setText(formateador.format(end));
           // holder.current.setVisibility(View.GONE);
        }

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new FragmentDetailWork();
                Bundle args = new Bundle();
                args.putLong(FragmentDetailWork.WORK_ID, holder.work.getId());
                fragment.setArguments(args);
                FragmentTransaction transaction = fragmentOne.getActivity().getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.swap_in_bottom, R.anim.swap_out_bottom);
                transaction.replace(R.id.content_main, fragment, "detailWork");
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

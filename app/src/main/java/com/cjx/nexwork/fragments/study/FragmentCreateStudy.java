package com.cjx.nexwork.fragments.study;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.DatePickerDialog;
import android.content.Context;
import android.net.Uri;
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
import com.cjx.nexwork.managers.work.WorkDetailCallback;
import com.cjx.nexwork.managers.work.WorkManager;
import com.cjx.nexwork.model.Work;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentCreateStudy.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentCreateStudy#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentCreateStudy extends Fragment implements View.OnClickListener, CheckBox.OnCheckedChangeListener, WorkDetailCallback {

    Calendar startCalendar = Calendar.getInstance();
    Calendar endCalendar = Calendar.getInstance();

    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    private EditText editPosition;
    private EditText editDateStarted;
    private EditText editDateEnd;
    private CheckBox checkWorking;
    private EditText editDescription;

    /*DatePickerDialog.OnDateSetListener dateStart = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            startCalendar.set(Calendar.YEAR, year);
            startCalendar.set(Calendar.MONTH, monthOfYear);
            startCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateStart();
        }

    };

    DatePickerDialog.OnDateSetListener dateEnd = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            endCalendar.set(Calendar.YEAR, year);
            endCalendar.set(Calendar.MONTH, monthOfYear);
            endCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateEnd();
        }

    };*/

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentCreateStudy() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static FragmentCreateStudy newInstance() {
        FragmentCreateStudy fragment = new FragmentCreateStudy();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_create_work,container, false);

        editPosition = (EditText) view.findViewById(R.id.editPosition);
        editDateStarted = (EditText) view.findViewById(R.id.editDateStarted);
        editDateEnd = (EditText) view.findViewById(R.id.editDateEnd);
        checkWorking = (CheckBox) view.findViewById(R.id.checkWorking);
        editDescription = (EditText) view.findViewById(R.id.editDescription);

        Button btnAddJob = (Button) view.findViewById(R.id.btn_create_company);
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
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onClick(final View v) {
        if(v.getId() == R.id.editPosition){
            Log.d("nxw", "position");
        }
        switch (v.getId())
        {
            case R.id.btn_create_company:
                addJob();
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

    private void addJob() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        try{
            Work work = new Work();
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

            WorkManager.getInstance().createWork(work, this);

        }
        catch (Exception e){
            e.printStackTrace();
        }

        /*getActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_work, new FragmentListStudy())
                .commit();*/
        Log.d("nxw", "add job");
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

    @Override
    public void onSuccess(Work work) {
        Log.d("nxw", work.toString());
        getActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.swap_in_bottom, R.anim.swap_out_bottom)
                .replace(R.id.fragment_work, new FragmentListStudy())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onFailure(Throwable t) {
        Log.d("nxw", t.getMessage());
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}

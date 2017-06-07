package com.cjx.nexwork.fragments.work;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.DatePickerDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;

import com.cjx.nexwork.R;
import com.cjx.nexwork.managers.TokenStoreManager;
import com.cjx.nexwork.managers.work.WorkDetailCallback;
import com.cjx.nexwork.managers.work.WorkManager;
import com.cjx.nexwork.model.Company;
import com.cjx.nexwork.model.Work;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentCreateWork.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentCreateWork#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentCreateWork extends Fragment implements View.OnClickListener, CheckBox.OnCheckedChangeListener, WorkDetailCallback {

    Calendar startCalendar = Calendar.getInstance();
    Calendar endCalendar = Calendar.getInstance();

    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    private EditText editPosition;
    private EditText editDateStarted;
    private EditText editDateEnd;
    private CheckBox checkWorking;
    private EditText editDescription;
    private Spinner spinnerCompanies;

    private List<Company> companies;

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

    ProgressBar progressBarSaved;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentCreateWork() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static FragmentCreateWork newInstance() {
        FragmentCreateWork fragment = new FragmentCreateWork();
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

        RelativeLayout relformMap = (RelativeLayout) view.findViewById(R.id.relformMap);
        relformMap.setVisibility(View.GONE);

        progressBarSaved = (ProgressBar) view.findViewById(R.id.progressBarSaved);
        progressBarSaved.setVisibility(View.GONE);
        progressBarSaved.setIndeterminate(true);

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

        //getAllCompanies

        spinnerCompanies = (Spinner) view.findViewById(R.id.spinnerSelCompany);

        Call<List<Company>> call = WorkManager.getInstance().getAllCompanies();
        call.enqueue(new Callback<List<Company>>() {
            @Override
            public void onResponse(Call<List<Company>> call, Response<List<Company>> response) {
                companies = response.body();
                ArrayAdapter<Company> spinnerArrayAdapter = new ArrayAdapter<Company>(
                        getContext(),
                        R.layout.custom_spinner_company,
                        companies){

                    @Override
                    public View getView(int position, View convertView, ViewGroup arg2) {
                        LayoutInflater inflater = LayoutInflater.from(getContext());
                        convertView = inflater.inflate(R.layout.custom_spinner_company, null);

                        TextView wv = (TextView) convertView.findViewById(R.id.companyNameSpinner);
                        wv.setText(companies.get(position).getNombre());

                        convertView.setTag(wv);

                        return convertView;
                    }

                    @Override
                    public View getDropDownView (int position, View convertView, ViewGroup parent){
                        View row = null;

                        LayoutInflater inflater = LayoutInflater.from(getContext());
                        convertView = inflater.inflate(R.layout.custom_spinner_company, null);

                        TextView wv = (TextView) convertView.findViewById(R.id.companyNameSpinner);
                        wv.setText(companies.get(position).getNombre());

                        TextView companyUbication = (TextView) convertView.findViewById(R.id.companyUbication);
                        companyUbication.setText(companies.get(position).getUbicacion());

                        convertView.setPadding(10, 10, 10, 10);

                        convertView.setTag(wv);

                        return convertView;
                    }

                    @Override
                    public int getCount(){
                        return companies.size();
                    }

                };
                spinnerArrayAdapter.setDropDownViewResource(R.layout.custom_spinner_company);
                spinnerArrayAdapter.notifyDataSetChanged();
                spinnerCompanies.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        Log.d("position companies", companies.get(position).getNombre());
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                spinnerCompanies.setAdapter(spinnerArrayAdapter);
            }

            @Override
            public void onFailure(Call<List<Company>> call, Throwable t) {

            }
        });

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

            int companySelected = spinnerCompanies.getSelectedItemPosition();
            Company company = companies.get(companySelected);
            work.setCompany(company);

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

            progressBarSaved.setVisibility(View.VISIBLE);

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
        progressBarSaved.setVisibility(View.GONE);

        Snackbar snackbar = Snackbar.make(getView(), "<span style='color: #ffffff;'>Work added</span>", Snackbar.LENGTH_LONG);
        View snackbarLayout = snackbar.getView();
        TextView textView = (TextView)snackbarLayout.findViewById(android.support.design.R.id.snackbar_text);
        textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.check_white, 0, 0, 0);
        textView.setCompoundDrawablePadding(getResources().getDimensionPixelOffset(android.R.dimen.app_icon_size));
        snackbar.show();

        //Snackbar.make(getView(), Html.fromHtml("<span style='color: #ffffff;'>Work added</span>"), Snackbar.LENGTH_SHORT).show();
        getActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.swap_in_bottom, R.anim.swap_out_bottom)
                .replace(R.id.content_main, new FragmentListWork(TokenStoreManager.getInstance().getUsername(), true))
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

package com.cjx.nexwork.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.cjx.nexwork.R;
import com.cjx.nexwork.managers.work.WorkManager;
import com.cjx.nexwork.model.Company;
import com.cjx.nexwork.util.CustomDatePicker;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Xavi on 06/06/2017.
 */

public class CreateCompanyActivity extends AppCompatActivity implements OnMapReadyCallback, CustomDatePicker.DatePickerListener {

    Context context;
    private GoogleMap mMap;
    private Company company;

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
               // mMap.addMarker(new MarkerOptions().position(latLng));
                CameraUpdate yourLocation = CameraUpdateFactory.newLatLngZoom(latLng, 15);
                mMap.animateCamera(yourLocation);
            }
        });

    }

    public void onMapSearch(String query) {
        String location = query;
        List<Address> addressList = null;

        if (location != null || !location.equals("")) {
            Geocoder geocoder = new Geocoder(this);
            try {
                addressList = geocoder.getFromLocationName(location, 1);

            } catch (IOException e) {
                e.printStackTrace();
            }

            Address address = addressList.get(0);
            mMap.clear();
            LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
            mMap.addMarker(new MarkerOptions().position(latLng).title("Marker"));
            //mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
            company.setLatitud(String.valueOf(address.getLatitude()));
            company.setLongitud(String.valueOf(address.getLongitude()));
            company.setUbicacion(address.getLocality());


            CameraUpdate yourLocation = CameraUpdateFactory.newLatLngZoom(latLng, 15);
            mMap.animateCamera(yourLocation);
        }
    }

    final SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
    ProgressBar progressBarSaved;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();  return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_company_activity);
        context = getApplicationContext();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_create_company);
        toolbar.setTitleTextColor(0xFFFFFFFF);
        toolbar.setTitle("Creating company");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.arrow_left_icon);
        company = new Company();
        progressBarSaved = (ProgressBar) findViewById(R.id.progressBarSaved);
        progressBarSaved.setVisibility(View.GONE);
        progressBarSaved.setIndeterminate(true);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);
        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
        AutocompleteFilter typeFilter = new AutocompleteFilter.Builder()
                .setTypeFilter(AutocompleteFilter.TYPE_FILTER_ADDRESS)
                .build();
        autocompleteFragment.setFilter(typeFilter);
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                Log.i("place", "Place: " + place.getName());//get place details here
                onMapSearch(place.getName().toString());
            }

            @Override
            public void onError(Status status) {
                Log.i("place", "An error occurred: " + status);
            }
        });


        Button btn_create_company = (Button) findViewById(R.id.btn_create_company);
        btn_create_company.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveCompany();
            }
        });

        final String fecha = format.format(Calendar.getInstance().getTime());

        EditText dateFundation = (EditText) findViewById(R.id.fechaFundación);
        dateFundation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker(fecha);
            }
        });


    }

    public void showDatePicker(String fecha){
        DialogFragment dialogFragment = new CustomDatePicker(this);
        Bundle bundle = new Bundle();
        bundle.putString("fecha", fecha);
        dialogFragment.setArguments(bundle);
        dialogFragment.show(getSupportFragmentManager(), "");
    }

    protected void saveCompany(){
        EditText dateFundation = (EditText) findViewById(R.id.fechaFundación);
        try {
            company.setFechaFundacion(format.parse(dateFundation.getText().toString()));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        EditText nombreCompany = (EditText) findViewById(R.id.nombreCompany);
        company.setNombre(nombreCompany.getText().toString());

        EditText numEmpleadosCo = (EditText) findViewById(R.id.numEmpleadosCo);
        company.setNumEmpleados(Integer.parseInt(numEmpleadosCo.getText().toString()));

        progressBarSaved.setVisibility(View.VISIBLE);

        Call<Company> call = WorkManager.getInstance().createCompany(company);
        call.enqueue(new Callback<Company>() {
            @Override
            public void onResponse(Call<Company> call, Response<Company> response) {
                Company company = response.body();
                Log.d("ok", company.toString());
                onBackPressed();
                finish();
            }

            @Override
            public void onFailure(Call<Company> call, Throwable t) {
                Log.d("error",t.getMessage());
            }
        });

    }

    @Override
    public void returnDate(String date) {
        EditText fecha = (EditText) findViewById(R.id.fechaFundación);
        fecha.setText(date);
    }
}

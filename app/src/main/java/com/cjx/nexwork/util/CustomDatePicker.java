package com.cjx.nexwork.util;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.app.DatePickerDialog;
import android.widget.DatePicker;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Carlos on 29/05/2017.
 */

@SuppressLint("ValidFragment")
public class CustomDatePicker extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    @SuppressLint("ValidFragment")
    public CustomDatePicker(DatePickerListener listener) {
        this.listener = listener;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        c.set(year, month, dayOfMonth);
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        Date date = c.getTime();
        listener.returnDate(format.format(date));
    }
    DatePickerListener listener;
    public interface DatePickerListener{
        public void returnDate(String date);
    }

    Calendar c = Calendar.getInstance();
    int year = c.get(Calendar.YEAR);
    int month = c.get(Calendar.MONTH);
    int dayOfMonth = c.get(Calendar.DAY_OF_MONTH);


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){

        Bundle bundle = getArguments();
        String date = bundle.getString("fecha");
        if(date != null){
            DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
            try {
                Date fecha= format.parse(date);
                c.setTime(fecha);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            year = c.get(Calendar.YEAR);
            month = c.get(Calendar.MONTH);
            dayOfMonth = c.get(Calendar.DAY_OF_MONTH);
        }

        DatePickerDialog dialog = new DatePickerDialog(getContext(), this, year, month, dayOfMonth);

        return dialog;
    }
}

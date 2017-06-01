package com.cjx.nexwork;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.cjx.nexwork.activities.MainActivity;
import com.cjx.nexwork.managers.user.UserDetailCallback;
import com.cjx.nexwork.managers.user.UserManager;
import com.cjx.nexwork.model.User;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */

public class EditPasswordFragment extends Fragment implements UserDetailCallback, View.OnClickListener {

    EditText key;
    Button saveButton;

    User user;

    public EditPasswordFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.edit_password, container, false);
        key = (EditText) view.findViewById(R.id.password);
        key.setText("");
        saveButton = (Button) view.findViewById(R.id.saveButton);

        UserManager.getInstance().getCurrentUser(this);


        return view;

    }

    @Override
    public void onSuccess(User user) {

        this.user = user;

        saveButton.setOnClickListener(this);

    }

    @Override
    public void onFailure(Throwable t) {

    }

    @Override
    public void onSuccessSaved(User user) {
        Snackbar.make(getView(), "Se han guardado bien tus datos", Snackbar.LENGTH_SHORT).show();
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onFailureSaved(Throwable t) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.saveButton:
                saveUserData();
                break;
        }
    }

    public void saveUserData(){

        if(!key.getText().toString().isEmpty()){
            String password = key.getText().toString();
            /*if(isValidPassword(key.getText().toString())){*/
               Call<ResponseBody> call = UserManager.getInstance().updatePassword(password);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    Log.d("OK", "OK");
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Log.d("OHHHHHHHHHH", "MYYY GOAAADDDD");
                }
            });
            /*}else {
                key.setError("No cumples los requisitos");
            }*/
        }

    }

    /*public boolean isValidPassword(final String password) {

        Pattern pattern;
        Matcher matcher;

        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$";

        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();

    }*/

}
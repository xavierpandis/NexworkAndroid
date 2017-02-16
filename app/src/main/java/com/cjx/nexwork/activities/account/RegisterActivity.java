package com.cjx.nexwork.activities.account;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.cjx.nexwork.R;
import com.cjx.nexwork.activities.LoginActivity;
import com.cjx.nexwork.activities.MainActivity;
import com.cjx.nexwork.managers.LoginCallback;
import com.cjx.nexwork.managers.RegisterCallback;
import com.cjx.nexwork.managers.RegisterManager;
import com.cjx.nexwork.managers.TokenStoreManager;
import com.cjx.nexwork.managers.UserLoginManager;
import com.cjx.nexwork.managers.user.UserDetailCallback;
import com.cjx.nexwork.model.User;
import com.cjx.nexwork.model.UserToken;

import okhttp3.ResponseBody;

public class RegisterActivity extends AppCompatActivity implements RegisterCallback,LoginCallback {

    private EditText usernameView;
    private AutoCompleteTextView emailView;
    private EditText passwordView;
    private EditText confirmPasswordView;
    private Boolean passwordMatching;
    private EditText firstNameView, lastNameView;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        progressDialog = new ProgressDialog(this);

        usernameView = (EditText) findViewById(R.id.username_registration);
        emailView = (AutoCompleteTextView) findViewById(R.id.email_registration);
        passwordView = (EditText) findViewById(R.id.password_registration);
        confirmPasswordView = (EditText) findViewById(R.id.confirm_password_registration);
        firstNameView = (EditText) findViewById(R.id.firstName_registration);
        lastNameView = (EditText) findViewById(R.id.lastName_registration);
        passwordMatching = false;

        Button registerBtn = (Button) findViewById(R.id.action_register_button);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptCreateAccount();
            }
        });

    }

    private void attemptCreateAccount() {

        progressDialog.show();
        progressDialog.setMessage("Creating account");
        progressDialog.setIndeterminate(true);

        // Reset errors
        usernameView.setError(null);
        passwordView.setError(null);
        emailView.setError(null);
        confirmPasswordView.setError(null);
        firstNameView.setError(null);
        lastNameView.setError(null);

        // Store values at the time of the register attempt.
        String username = usernameView.getText().toString();
        String email = emailView.getText().toString();
        String password = passwordView.getText().toString();
        String confirmPassword = confirmPasswordView.getText().toString();
        String firstName = firstNameView.getText().toString();
        String lastName = lastNameView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            passwordView.setError(getString(R.string.error_invalid_password));
            focusView = passwordView;
            cancel = true;
        }

        if(!TextUtils.isEmpty(firstName) && firstName.length() < 2){
            firstNameView.setError(getString(R.string.error_invalid_password));
            focusView = firstNameView;
            cancel = true;
        }

        if(!TextUtils.isEmpty(lastName) && lastName.length() < 2){
            lastNameView.setError(getString(R.string.error_invalid_password));
            focusView = lastNameView;
            cancel = true;
        }

        if (!TextUtils.isEmpty(confirmPassword) && !isPasswordValid(confirmPassword)) {
            confirmPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = confirmPasswordView;
            cancel = true;
        }

        if(!isConfirmPasswordMatching(password, confirmPassword)){
            confirmPasswordView.setError(getString(R.string.error_confirm_password));
            focusView = confirmPasswordView;
            cancel = true;
        }

        // Check for a valid username address.
        if (TextUtils.isEmpty(username)) {
            usernameView.setError(getString(R.string.error_field_required));
            focusView = usernameView;
            cancel = true;
        }

        if (TextUtils.isEmpty(email)) {
            emailView.setError(getString(R.string.error_field_required));
            focusView = emailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            emailView.setError(getString(R.string.error_invalid_email));
            focusView = emailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            progressDialog.dismiss();
            focusView.requestFocus();
        } else {
            Log.d("nxw => OK","");
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            //showProgress(true);
            //UserLoginManager.getInstance().performLogin(username, password, LoginActivity.this);
            User user = new User(username, password, email, firstName, lastName);
            RegisterManager.getInstance().createUser(user, RegisterActivity.this);
        }

    }

    private boolean isPasswordValid(String password) {
        return password.length() >= 4;
    }

    private boolean isConfirmPasswordMatching(String password, String confirmPassword) {
        if(confirmPassword.equals(password)){
            return true;
        }
        return false;
    }

    private boolean isEmailValid(String email) {
        return email.contains("@");
    }


    // Register success
    @Override
    public void onSuccessRegister(ResponseBody responseBody) {
        progressDialog.setMessage("Account created");
        String username = usernameView.getText().toString();
        String password = passwordView.getText().toString();
        // Login after register
        UserLoginManager.getInstance().performLogin(username, password, RegisterActivity.this);
    }

    @Override
    public void onFailureRegister(Throwable t) {

    }

    // Login success
    @Override
    public void onSuccess(UserToken userToken) {
        progressDialog.dismiss();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("accessToken",userToken.getAccessToken());
        editor.putString("refreshToken",userToken.getRefreshToken());
        editor.putString("tokenType", userToken.getTokenType());
        editor.putString("username", TokenStoreManager.getInstance().getUsername());
        editor.apply();

        TokenStoreManager.getInstance().setAccessToken(userToken.getAccessToken());
        TokenStoreManager.getInstance().setRefreshToken(userToken.getRefreshToken());
        TokenStoreManager.getInstance().setTokenType(userToken.getTokenType());

        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onFailure(Throwable t) {

    }
}

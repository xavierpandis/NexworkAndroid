package com.cjx.nexwork.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.cjx.nexwork.activities.account.RegisterActivity;
import com.cjx.nexwork.managers.LoginCallback;
import com.cjx.nexwork.R;
import com.cjx.nexwork.managers.TokenStoreManager;
import com.cjx.nexwork.managers.UserLoginManager;
import com.cjx.nexwork.model.UserToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.concurrent.Callable;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements LoginCallback {

    // UI references.
    private AutoCompleteTextView mUsernameView;
    private EditText mPasswordView;
    //private View mProgressView;
    private View mLoginFormView;
    private ProgressDialog progressDialog;
    CallbackManager callbackManager;
    LoginButton loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        // INTEGRACION FACEBOOK

        /* FacebookSdk.sdkInitialize(getApplicationContext());

       try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.cjx.nexwork",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }*/

        /*callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile"));

        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

            @Override
            public void onSuccess(LoginResult loginResult) {

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                Log.d("nxw-facebook - success", loginResult.getAccessToken().getUserId());
                Log.d("nxw-facebook - success", Profile.getCurrentProfile().getFirstName());

            }

            @Override
            public void onCancel() {
                // App code
                Log.d("nxw-facebook - cancel", "ERROR");
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
                Log.d("nxw-facebook exception", exception.getMessage());
            }
        });
        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions("email");
        */
        mUsernameView = (AutoCompleteTextView) findViewById(R.id.username);
        TextView createAccountButton = (TextView) findViewById(R.id.createAccount);

        createAccountButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(registerIntent);
            }
        });

        progressDialog = new ProgressDialog(this);

        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });



        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        //mProgressView = findViewById(R.id.login_progress);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {

        progressDialog.show();
        progressDialog.setMessage("Logging in");
        progressDialog.setIndeterminate(true);

        // Reset errors.
        mUsernameView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String username = mUsernameView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid username address.
        if (TextUtils.isEmpty(username)) {
            mUsernameView.setError(getString(R.string.error_field_required));
            focusView = mUsernameView;
            cancel = true;
        }

        if (cancel) {
            progressDialog.dismiss();
            createAlert("Login error","Try again!");
            focusView.requestFocus();
        } else {
            UserLoginManager.getInstance().performLogin(username, password, LoginActivity.this);
        }
    }

    public void createAlert(String title, String message){
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .show();
    }

    private boolean isPasswordValid(String password) {
        return password.length() >= 4;
    }

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

        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onFailure(Throwable t) {
        progressDialog.dismiss();
        createAlert("Login error","Wrong username or password");
        mPasswordView.setError("ERROR");
        mPasswordView.requestFocus();
    }

}


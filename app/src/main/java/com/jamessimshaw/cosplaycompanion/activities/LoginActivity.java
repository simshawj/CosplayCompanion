package com.jamessimshaw.cosplaycompanion.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.jamessimshaw.cosplaycompanion.CosplayCompanionApplication;
import com.jamessimshaw.cosplaycompanion.R;
import com.jamessimshaw.cosplaycompanion.presenters.LoginPresenter;
import com.jamessimshaw.cosplaycompanion.presenters.LoginPresenterImpl;
import com.jamessimshaw.cosplaycompanion.views.LoginView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class LoginActivity extends AppCompatActivity implements LoginView, GoogleApiClient.OnConnectionFailedListener {

    @BindView(R.id.login_edit_username) EditText mLoginNameEditText;
    @BindView(R.id.login_edit_password) EditText mPasswordEditText;

    public static int SIGN_IN_REQUEST_CODE = 2134;

    @Inject LoginPresenter mPresenter;

    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        ButterKnife.bind(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        ((CosplayCompanionApplication)getApplication()).getLoginComponent().inject(this);
        mPresenter.setView(this);
        mPresenter.verifyToken();
    }

    @Override
    protected void onStop() {
        super.onStop();

        mPresenter.removeView(this);
        mPresenter = null;
    }

    // Button onClicks

    /* TODO: NYI
    @OnClick(R.id.login_btn_forgot_password)
    public void forgotPassword() {
        Toast.makeText(this, "This feature is not yet implemented", Toast.LENGTH_LONG).show();
    } */

    @OnClick(R.id.login_btn_google)
    public void signin() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, SIGN_IN_REQUEST_CODE);
    }

    @OnClick(R.id.login_btn_register)
    public void register() {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
    }

    @OnClick(R.id.login_btn_sign_in)
    public void login() {
        mPresenter.login();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SIGN_IN_REQUEST_CODE) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            //handleSignInResult(result);
        }

    }

    // LoginView Methods

    @Override
    public String getLoginName() {
        return mLoginNameEditText.getText().toString();
    }

    @Override
    public String getPassword() {
        return mPasswordEditText.getText().toString();
    }

    @Override
    public void displayWarning(String warning) {
        Toast.makeText(this, warning, Toast.LENGTH_LONG).show();
    }

    @Override
    public void done() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
    }

    // GoogleApiClient.OnConnectionFailedListener method

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}

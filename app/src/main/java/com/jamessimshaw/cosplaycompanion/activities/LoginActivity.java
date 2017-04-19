package com.jamessimshaw.cosplaycompanion.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.jamessimshaw.cosplaycompanion.R;
import com.jamessimshaw.cosplaycompanion.presenters.LoginPresenter;
import com.jamessimshaw.cosplaycompanion.presenters.LoginPresenterImpl;
import com.jamessimshaw.cosplaycompanion.views.LoginView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class LoginActivity extends Activity implements LoginView {

    @BindView(R.id.login_edit_username) EditText mLoginNameEditText;
    @BindView(R.id.login_edit_password) EditText mPasswordEditText;

    private LoginPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        mPresenter = new LoginPresenterImpl();
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

    @OnClick(R.id.login_btn_register)
    public void register() {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.login_btn_sign_in)
    public void login() {
        mPresenter.login();
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
}

package com.jamessimshaw.cosplaycompanion.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.jamessimshaw.cosplaycompanion.R;
import com.jamessimshaw.cosplaycompanion.presenters.LoginPresenterImpl;
import com.jamessimshaw.cosplaycompanion.presenters.RegisterPresenter;
import com.jamessimshaw.cosplaycompanion.presenters.RegisterPresenterImpl;
import com.jamessimshaw.cosplaycompanion.views.RegisterView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends AppCompatActivity implements RegisterView {

    @BindView(R.id.register_email) EditText mEmailEditText;
    @BindView(R.id.register_username) EditText mUsernameEditText;
    @BindView(R.id.register_password) EditText mPassword;
    @BindView(R.id.register_verify_password) EditText mPasswordVerify;

    private RegisterPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        mPresenter = new RegisterPresenterImpl();
        mPresenter.setView(this);
    }

    @Override
    protected void onStop() {
        super.onStop();

        mPresenter.removeView(this);
        mPresenter = null;
    }

    @OnClick(R.id.register_register_button)
    public void register() {
        mPresenter.register();
    }

    // RegisterView methods

    @Override
    public String getEmail() {
        return mEmailEditText.getText().toString();
    }

    @Override
    public String getUsername() {
        return mUsernameEditText.getText().toString();
    }

    @Override
    public String getPassword() {
        return mPassword.getText().toString();
    }

    @Override
    public String getPasswordVerification() {
        return mPasswordVerify.getText().toString();
    }

    @Override
    public void displayWarning(String warning) {
        Toast.makeText(this, warning, Toast.LENGTH_LONG).show();
    }

    @Override
    public void done() {

    }
}

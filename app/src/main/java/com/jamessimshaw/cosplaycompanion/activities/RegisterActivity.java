package com.jamessimshaw.cosplaycompanion.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.jamessimshaw.cosplaycompanion.CosplayCompanionApplication;
import com.jamessimshaw.cosplaycompanion.R;
import com.jamessimshaw.cosplaycompanion.presenters.RegisterPresenter;
import com.jamessimshaw.cosplaycompanion.views.RegisterView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends AppCompatActivity implements RegisterView {

    @BindView(R.id.register_email) EditText mEmailEditText;
    @BindView(R.id.register_username) EditText mUsernameEditText;
    @BindView(R.id.register_password) EditText mPassword;
    @BindView(R.id.register_verify_password) EditText mPasswordVerify;
    @BindView(R.id.terms_checkbox) CheckBox mTermsCheckBox;

    @Inject RegisterPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);

        String htmlString = getString(R.string.terms_privacy_agreement);

        if (Build.VERSION.SDK_INT > 23)
            mTermsCheckBox.setText(Html.fromHtml(htmlString, Html.FROM_HTML_MODE_LEGACY));
        else
            mTermsCheckBox.setText(Html.fromHtml(htmlString));

        mTermsCheckBox.setClickable(true);
        mTermsCheckBox.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @Override
    protected void onStart() {
        super.onStart();

        ((CosplayCompanionApplication)getApplication()).getLoginComponent().inject(this);
        mPresenter.setView(this);
    }

    @Override
    protected void onStop() {
        super.onStop();

        mPresenter.detachView();
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
    public boolean getAgreementStatus() {
        return mTermsCheckBox.isChecked();
    }

    @Override
    public void displayWarning(String warning) {
        Toast.makeText(this, warning, Toast.LENGTH_LONG).show();
    }

    @Override
    public void done() {
        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
    }
}

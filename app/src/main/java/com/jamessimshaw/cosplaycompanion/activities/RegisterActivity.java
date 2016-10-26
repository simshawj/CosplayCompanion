package com.jamessimshaw.cosplaycompanion.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
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
    @BindView(R.id.terms_checkbox) CheckBox mTermsCheckBox;
    @BindView(R.id.terms_textview) TextView mTermsTextView;

    private RegisterPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);

        //TODO: Extract this out to a resource
        String htmlString = "I agree to the <a href='http://www.jamessimshaw.com/cc_tou.html'>Terms of Use</a>"
                + " and the <a href='http://www.jamessimshaw.com/cc_privacy.html'>Privacy Policy</a>";

        if (Build.VERSION.SDK_INT > 23)
            mTermsTextView.setText(Html.fromHtml(htmlString, Html.FROM_HTML_MODE_LEGACY));
        else
            mTermsTextView.setText(Html.fromHtml(htmlString));

        mTermsTextView.setClickable(true);
        mTermsTextView.setMovementMethod(LinkMovementMethod.getInstance());
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
        startActivity(intent);
    }
}

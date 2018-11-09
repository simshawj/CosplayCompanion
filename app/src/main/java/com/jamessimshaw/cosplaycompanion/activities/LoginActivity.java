package com.jamessimshaw.cosplaycompanion.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.jamessimshaw.cosplaycompanion.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    public static int SIGN_IN_REQUEST_CODE = 2134;

    private GoogleApiClient mGoogleApiClient;
    private FirebaseAuth mFirebaseAuth;

    @BindView(R.id.termsTextView) TextView mTermsTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mFirebaseAuth = FirebaseAuth.getInstance();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        ButterKnife.bind(this);

        String htmlString = getString(R.string.terms_privacy_agreement);

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

        FirebaseUser user = mFirebaseAuth.getCurrentUser();
        verifyUser(user);
    }

    private void verifyUser(FirebaseUser user) {
        if (user != null) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    // Button onClicks

    @OnClick(R.id.login_btn_google)
    public void signin() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, SIGN_IN_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SIGN_IN_REQUEST_CODE) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                GoogleSignInAccount googleAccount = result.getSignInAccount();
                AuthCredential authCredential = GoogleAuthProvider.getCredential(googleAccount.getIdToken(), null);
                mFirebaseAuth.signInWithCredential(authCredential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mFirebaseAuth.getCurrentUser();
                            verifyUser(user);
                        }
                    }
                });

            } else {
                Toast.makeText(this, "Unable to sign in", Toast.LENGTH_SHORT).show();
            }
            //handleSignInResult(result);
        }

    }

    // GoogleApiClient.OnConnectionFailedListener method

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}

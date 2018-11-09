package com.jamessimshaw.cosplaycompanion.activities

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import android.text.Html
import android.text.method.LinkMovementMethod
import android.widget.TextView
import android.widget.Toast

import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.jamessimshaw.cosplaycompanion.R

import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick


class LoginActivity : AppCompatActivity(), GoogleApiClient.OnConnectionFailedListener {

    private var mGoogleApiClient: GoogleApiClient? = null
    private var mFirebaseAuth: FirebaseAuth? = null

    @BindView(R.id.termsTextView)
    internal var mTermsTextView: TextView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mFirebaseAuth = FirebaseAuth.getInstance()

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()

        mGoogleApiClient = GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build()

        ButterKnife.bind(this)

        val htmlString = getString(R.string.terms_privacy_agreement)

        if (Build.VERSION.SDK_INT > 23)
            mTermsTextView!!.text = Html.fromHtml(htmlString, Html.FROM_HTML_MODE_LEGACY)
        else
            mTermsTextView!!.text = Html.fromHtml(htmlString)

        mTermsTextView!!.isClickable = true
        mTermsTextView!!.movementMethod = LinkMovementMethod.getInstance()
    }

    override fun onStart() {
        super.onStart()

        val user = mFirebaseAuth!!.currentUser
        verifyUser(user)
    }

    private fun verifyUser(user: FirebaseUser?) {
        if (user != null) {
            val intent = Intent(this@LoginActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    // Button onClicks

    @OnClick(R.id.login_btn_google)
    fun signin() {
        val signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient)
        startActivityForResult(signInIntent, SIGN_IN_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == SIGN_IN_REQUEST_CODE) {
            val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
            if (result.isSuccess) {
                val googleAccount = result.signInAccount
                val authCredential = GoogleAuthProvider.getCredential(googleAccount!!.idToken, null)
                mFirebaseAuth!!.signInWithCredential(authCredential).addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val user = mFirebaseAuth!!.currentUser
                        verifyUser(user)
                    }
                }

            } else {
                Toast.makeText(this, "Unable to sign in", Toast.LENGTH_SHORT).show()
            }
            //handleSignInResult(result);
        }

    }

    // GoogleApiClient.OnConnectionFailedListener method

    override fun onConnectionFailed(@NonNull connectionResult: ConnectionResult) {

    }

    companion object {
        var SIGN_IN_REQUEST_CODE = 2134
    }
}

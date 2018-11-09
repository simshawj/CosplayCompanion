package com.jamessimshaw.cosplaycompanion.activities

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.text.method.LinkMovementMethod
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.jamessimshaw.cosplaycompanion.R


class LoginActivity : AppCompatActivity() {

    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var firebaseAuth: FirebaseAuth

    @BindView(R.id.termsTextView)
    internal var termsTextView: TextView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        firebaseAuth = FirebaseAuth.getInstance()

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        ButterKnife.bind(this)

        val htmlString = getString(R.string.terms_privacy_agreement)

        if (Build.VERSION.SDK_INT > 23)
            termsTextView!!.text = Html.fromHtml(htmlString, Html.FROM_HTML_MODE_LEGACY)
        else
            termsTextView!!.text = Html.fromHtml(htmlString)

        termsTextView!!.isClickable = true
        termsTextView!!.movementMethod = LinkMovementMethod.getInstance()
    }

    override fun onStart() {
        super.onStart()

        val user = firebaseAuth.currentUser
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
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, SIGN_IN_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == SIGN_IN_REQUEST_CODE) {
            val googleSignInTask = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = googleSignInTask.getResult(ApiException::class.java)
                val authCredential = GoogleAuthProvider.getCredential(account!!.idToken, null)
                firebaseAuth.signInWithCredential(authCredential).addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val user = firebaseAuth.currentUser
                        verifyUser(user)
                    }
                }
            } catch (e: ApiException) {
                // TODO: Google Sign In failed, update UI appropriately
                Log.d("Signin", "Google sign in failed", e)
            }
        }

    }

    companion object {
        var SIGN_IN_REQUEST_CODE = 2134
    }
}

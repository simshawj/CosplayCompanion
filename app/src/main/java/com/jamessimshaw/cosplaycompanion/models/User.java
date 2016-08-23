package com.jamessimshaw.cosplaycompanion.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by james on 8/23/16.
 */
public class User {
    @SerializedName("username")
    private String mUsername;
    @SerializedName("email")
    private String mEmail;
    @SerializedName("uid")
    private String mUid;
    private SessionToken mToken;

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String username) {
        mUsername = username;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public String getUid() {
        return mUid;
    }

    public void setUid(String uid) {
        mUid = uid;
    }

    public SessionToken getToken() {
        return mToken;
    }

    public void setToken(SessionToken token) {
        mToken = token;
    }
}

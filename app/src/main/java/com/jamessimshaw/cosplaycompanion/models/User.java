package com.jamessimshaw.cosplaycompanion.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by james on 8/23/16.
 */
public class User {
    @SerializedName("id")
    private int mId;
    @SerializedName("email")
    private String mEmail;
    @SerializedName("username")
    private String mUsername;
    @SerializedName("uid")
    private String mUid;


    public User(int id, String email, String username, String uid) {
        mUsername = username;
        mEmail = email;
        mUid = uid;
        mId = id;
    }

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

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }
}

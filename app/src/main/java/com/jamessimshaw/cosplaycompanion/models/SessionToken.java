package com.jamessimshaw.cosplaycompanion.models;

/**
 * Created by james on 8/23/16.
 */
public class SessionToken {
    private String mAccessToken;
    private String mClient;
    private String mExpiry;
    private String mUid;

    public SessionToken() {
        mAccessToken = "";
        mClient = "";
        mExpiry = "";
        mUid = "";
    }

    public String getAccessToken() {
        return mAccessToken;
    }

    public void setAccessToken(String accessToken) {
        mAccessToken = accessToken;
    }

    public String getClient() {
        return mClient;
    }

    public void setClient(String client) {
        mClient = client;
    }

    public String getExpiry() {
        return mExpiry;
    }

    public void setExpiry(String expiry) {
        mExpiry = expiry;
    }

    public String getUid() {
        return mUid;
    }

    public void setUid(String uid) {
        mUid = uid;
    }
}

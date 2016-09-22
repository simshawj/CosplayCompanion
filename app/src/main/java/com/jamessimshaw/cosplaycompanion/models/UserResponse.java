package com.jamessimshaw.cosplaycompanion.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by james on 9/21/16.
 */
public class UserResponse {
    @SerializedName("data")
    User mUser;

    public User getUser() {
        return mUser;
    }

    public void setUser(User user) {
        mUser = user;
    }
}

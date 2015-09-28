package com.jamessimshaw.cosplaycompanion.models;

import android.graphics.Bitmap;

/**
 * Created by james on 9/25/15.
 */
public class Convention {
    private String mName;
    private String mDescription;
    private Bitmap mLogo;
    private long mId;

    public Convention(long id, String name, String description, Bitmap logo) {
        mId = id;
        mName = name;
        mDescription = description;
        mLogo = logo;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public Bitmap getLogo() {
        return mLogo;
    }

    public void setLogo(Bitmap logo) {
        mLogo = logo;
    }

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        mId = id;
    }
}

package com.jamessimshaw.cosplaycompanion.models;

import android.graphics.Bitmap;

/**
 * Created by james on 9/25/15.
 */
public class Convention {
    private String mName;
    private String mDescription;
    private Bitmap mLogo;
    private long id;

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
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}

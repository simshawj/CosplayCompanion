package com.jamessimshaw.cosplaycompanion.models;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.ByteArrayOutputStream;

/**
 * Created by james on 9/25/15.
 */
public class Convention implements Parcelable {
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

    public Convention(String name, String description, Bitmap logo) {
        mName = name;
        mDescription = description;
        mLogo = logo;
    }

    public Convention (Parcel in) {
        mName = in.readString();
        mLogo = in.readParcelable(Bitmap.class.getClassLoader());
        mDescription = in.readString();
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mName);
        dest.writeParcelable(mLogo, 0);
        dest.writeString(mDescription);
    }

    public static final Parcelable.Creator<Convention> CREATOR
            = new Parcelable.Creator<Convention>() {

        @Override
        public Convention createFromParcel(Parcel source) {
            return new Convention(source);
        }

        @Override
        public Convention[] newArray(int size) {
            return new Convention[size];
        }
    };
}

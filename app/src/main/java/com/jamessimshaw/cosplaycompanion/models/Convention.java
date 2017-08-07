package com.jamessimshaw.cosplaycompanion.models;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.Exclude;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by james on 9/25/15.
 */
public class Convention implements Parcelable {
    @Exclude
    private String mName;

    private String mDescription;

    private String mLogoUriString;

    public Convention(String name, String description, String logoUriString) {
        mName = name;
        mDescription = description;
        mLogoUriString = logoUriString;
    }

    // TODO: Fixme!!!
    public Convention (Parcel in) {
        mName = in.readString();
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

    public String getLogoUriString() {
        return mLogoUriString;
    }

    public void setLogoUriString(String logoUriString) {
        mLogoUriString = logoUriString;
    }

    public void setName(String name) {
        mName = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mName);
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

    // TODO: FIXME!!!!
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Convention))
            return false;

        Convention otherConvention = (Convention) o;

        return mName.equals(otherConvention.mName) &&
                mDescription.equals(otherConvention.mDescription);
    }

    // TODO: FIXME!!!!
    @Override
    public int hashCode() {
        int result = 17;

        result = 37 * result + mName.hashCode();
        result = 37 * result + mDescription.hashCode();

        return result;
    }
}

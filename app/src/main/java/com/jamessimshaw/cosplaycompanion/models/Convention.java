package com.jamessimshaw.cosplaycompanion.models;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by james on 9/25/15.
 */
public class Convention implements Parcelable {
    @SerializedName("name")
    @Expose
    private String mName;
    @SerializedName("description")
    @Expose
    private String mDescription;
    private Uri mLogoUri;
    @SerializedName("id")
    private long mId;

    public Convention(long id, String name, String description, Uri logoUri) {
        mId = id;
        mName = name;
        mDescription = description;
        mLogoUri = logoUri;
    }

    public Convention(String name, String description, Uri logoUri) {
        mName = name;
        mDescription = description;
        mLogoUri = logoUri;
        mId = -1;
    }

    public Convention (Parcel in) {
        mName = in.readString();
        mLogoUri = in.readParcelable(Bitmap.class.getClassLoader());
        mDescription = in.readString();
        mId = in.readLong();
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

    public Uri getLogoUri() {
        return mLogoUri;
    }

    public void setLogoUri(Uri logoUri) {
        mLogoUri = logoUri;
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
        dest.writeParcelable(mLogoUri, 0);
        dest.writeString(mDescription);
        dest.writeLong(mId);
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

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Convention))
            return false;

        Convention otherConvention = (Convention) o;

        return mName.equals(otherConvention.mName) &&
                mDescription.equals(otherConvention.mDescription) &&
                mLogoUri.equals(otherConvention.mLogoUri) &&
                (mId == otherConvention.mId);
    }

    @Override
    public int hashCode() {
        int result = 17;

        result = 37 * result + mName.hashCode();
        result = 37 * result + mDescription.hashCode();
        result = 37 * result + mLogoUri.hashCode();
        result = 37 * result + (int) (mId ^ (mId >>> 32));

        return result;
    }
}

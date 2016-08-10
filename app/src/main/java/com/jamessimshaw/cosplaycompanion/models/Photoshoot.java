package com.jamessimshaw.cosplaycompanion.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by james on 9/25/15.
 */
public class Photoshoot implements Parcelable {
    @SerializedName("series")
    private String mSeries;
    @SerializedName("start")
    private Date mStart;
    @SerializedName("location")
    private String mLocation;
    @SerializedName("description")
    private String mDescription;
    @SerializedName("id")
    private long mId;
    @SerializedName("convention_year_id")
    private long mConventionYearId;

    public Photoshoot(long id, String series, Date start, String location, String description,
                      long conventionYearId) {
        mSeries = series;
        mStart = start;
        mLocation = location;
        mDescription = description;
        mId = id;
        mConventionYearId = conventionYearId;
    }

    public Photoshoot(String series, Date start, String location, String description,
                      long conventionYearId) {
        mSeries = series;
        mStart = start;
        mLocation = location;
        mDescription = description;
        mConventionYearId = conventionYearId;
    }

    public Photoshoot(Parcel in) {
        mSeries = in.readString();
        mStart = new Date(in.readLong());
        mLocation = in.readString();
        mDescription = in.readString();
        mId = in.readLong();
        mConventionYearId = in.readLong();
    }

    public String getSeries() {
        return mSeries;
    }

    public void setSeries(String series) {
        mSeries = series;
    }

    public Date getStart() {
        return mStart;
    }

    public void setStart(Date start) {
        mStart = start;
    }

    public String getLocation() {
        return mLocation;
    }

    public void setLocation(String location) {
        mLocation = location;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        mId = id;
    }

    public long getConventionYearId() {
        return mConventionYearId;
    }

    public void setConventionYearId(long conventionYearId) {
        mConventionYearId = conventionYearId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mSeries);
        dest.writeLong(mStart.getTime());
        dest.writeString(mLocation);
        dest.writeString(mDescription);
        dest.writeLong(mId);
        dest.writeLong(mConventionYearId);
    }

    public static final Parcelable.Creator<Photoshoot> CREATOR = new Creator<Photoshoot>() {
        @Override
        public Photoshoot createFromParcel(Parcel source) {
            return new Photoshoot(source);
        }

        @Override
        public Photoshoot[] newArray(int size) {
            return new Photoshoot[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Photoshoot))
            return false;

        Photoshoot otherPhotoshoot = (Photoshoot) o;

        return mSeries.equals(otherPhotoshoot.mSeries) &&
                mStart.equals(otherPhotoshoot.mStart) &&
                mLocation.equals(otherPhotoshoot.mLocation) &&
                mDescription.equals(otherPhotoshoot.mDescription) &&
                (mConventionYearId == otherPhotoshoot.mConventionYearId) &&
                (mId == otherPhotoshoot.mId);
    }

    @Override
    public int hashCode() {
        int result = 17;

        result = 37 * result + mSeries.hashCode();
        result = 37 * result + mStart.hashCode();
        result = 37 * result + mLocation.hashCode();
        result = 37 * result + mDescription.hashCode();
        result = 37 * result + (int) (mConventionYearId ^ (mConventionYearId >>> 32));
        result = 37 * result + (int) (mId ^ (mId >>> 32));

        return result;
    }
}

package com.jamessimshaw.cosplaycompanion.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by james on 9/25/15.
 */
public class Photoshoot implements Parcelable {
    private String mSeries;
    private long mStart;
    private String mLocation;
    private String mDescription;
    private String mEventId;
    private String mSubmitted;

    public Photoshoot() {
        // Required default constructor
    }

    public Photoshoot(String series, Date start, String location, String description, String eventId, String user) {
        mSeries = series;
        mStart = start.getTime();
        mLocation = location;
        mDescription = description;
        mEventId = eventId;
        mSubmitted = user;
    }

    public Photoshoot(Parcel in) {
        mSeries = in.readString();
        mStart = in.readLong();
        mLocation = in.readString();
        mDescription = in.readString();
    }

    public String getSeries() {
        return mSeries;
    }

    public void setSeries(String series) {
        mSeries = series;
    }

    public long getStart() {
        return mStart;
    }

    public void setStart(long start) {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mSeries);
        dest.writeLong(mStart);
        dest.writeString(mLocation);
        dest.writeString(mDescription);
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

    // TODO: Verify
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Photoshoot))
            return false;

        Photoshoot otherPhotoshoot = (Photoshoot) o;

        return mSeries.equals(otherPhotoshoot.mSeries) &&
                mLocation.equals(otherPhotoshoot.mLocation) &&
                mDescription.equals(otherPhotoshoot.mDescription);
    }

    // TODO: Verify
    @Override
    public int hashCode() {
        int result = 17;

        result = 37 * result + mSeries.hashCode();
        result = 37 * result + mLocation.hashCode();
        result = 37 * result + mDescription.hashCode();

        return result;
    }
}

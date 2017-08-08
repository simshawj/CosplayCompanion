package com.jamessimshaw.cosplaycompanion.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.Exclude;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by james on 9/25/15.
 */
public class ConventionYear implements Parcelable {
    @Exclude
    private String mYear;

    @Exclude
    private String mName;

    private Date mStart;
    private Date mEnd;
    private String mLocation;
    private String mDisplayName;

    public ConventionYear() {
        // Required Default Constructor
    }

    public ConventionYear(Date start, Date end, String location, String displayName) {
        mStart = start;
        mEnd = end;
        mLocation = location;
        mDisplayName = displayName;
    }

    public ConventionYear(Parcel in) {
        mStart = new Date(in.readLong());
        mEnd = new Date(in.readLong());
        mLocation = in.readString();
    }

    public Date getStartDate() {
        return mStart;
    }

    public void setStart(Date start) {
        mStart = start;
    }

    public Date getEndDate() {
        return mEnd;
    }

    public void setEnd(Date end) {
        mEnd = end;
    }

    public String getLocation() {
        return mLocation;
    }

    public void setLocation(String location) {
        mLocation = location;
    }

    public String getDisplayName() {
        return mDisplayName;
    }

    public void setDisplayName(String displayName) {
        mDisplayName = displayName;
    }

    @Exclude
    public String getYearAsString() {
        return Integer.toString(getYear());
    }

    @Exclude
    public int getYear() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(mStart);
        return calendar.get(Calendar.YEAR);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(mStart.getTime());
        dest.writeLong(mEnd.getTime());
        dest.writeString(mLocation);
    }

    public static final Parcelable.Creator<ConventionYear> CREATOR
            = new Parcelable.Creator<ConventionYear>() {

        @Override
        public ConventionYear createFromParcel(Parcel source) {
            return new ConventionYear(source);
        }

        @Override
        public ConventionYear[] newArray(int size) {
            return new ConventionYear[size];
        }
    };

    //TODO: Double Check
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof ConventionYear))
            return false;

        ConventionYear otherYear = (ConventionYear) o;

        return mStart.equals(otherYear.mStart) &&
                mEnd.equals(otherYear.mEnd) &&
                mLocation.equals(otherYear.mLocation);
    }

    //TODO: Double Check
    @Override
    public int hashCode() {
        int result = 17;

        result = 37 * result + mStart.hashCode();
        result = 37 * result + mEnd.hashCode();
        result = 37 * result + mLocation.hashCode();

        return result;
    }
}

package com.jamessimshaw.cosplaycompanion.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by james on 9/25/15.
 */
public class ConventionYear implements Parcelable {
    @SerializedName("start")
    private Date mStart;
    @SerializedName("finish")
    private Date mEnd;
    @SerializedName("id")
    private long mId;
    @SerializedName("convention_id")
    private long mConventionId;
    @SerializedName("location")
    private String mLocation;
    @SerializedName("display")
    private String mDisplayName;

    public ConventionYear(long id, Date start, Date end, long conventionId, String location,
                          String displayName) {
        mStart = start;
        mEnd = end;
        mId = id;
        mConventionId = conventionId;
        mLocation = location;
        mDisplayName = displayName;
    }

    public ConventionYear(Date start, Date end, long conventionId, String location, String displayName) {
        mStart = start;
        mEnd = end;
        mConventionId = conventionId;
        mLocation = location;
        mDisplayName = displayName;
    }

    public ConventionYear(Parcel in) {
        mStart = new Date(in.readLong());
        mEnd = new Date(in.readLong());
        mId = in.readLong();
        mConventionId = in.readLong();
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

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        mId = id;
    }

    public long getConventionId() {
        return mConventionId;
    }

    public void setConventionId(long conventionId) {
        mConventionId = conventionId;
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

    public String getYearAsString() {
        return Integer.toString(getYear());
    }

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
        dest.writeLong(mId);
        dest.writeLong(mConventionId);
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

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof ConventionYear))
            return false;

        ConventionYear otherYear = (ConventionYear) o;

        return mStart.equals(otherYear.mStart) &&
                mEnd.equals(otherYear.mEnd) &&
                mLocation.equals(otherYear.mLocation) &&
                (mConventionId == otherYear.mConventionId) &&
                (mId == otherYear.mId);
    }

    @Override
    public int hashCode() {
        int result = 17;

        result = 37 * result + mStart.hashCode();
        result = 37 * result + mEnd.hashCode();
        result = 37 * result + mLocation.hashCode();
        result = 37 * result + (int) (mConventionId ^ (mConventionId >>> 32));
        result = 37 * result + (int) (mId ^ (mId >>> 32));

        return result;
    }
}

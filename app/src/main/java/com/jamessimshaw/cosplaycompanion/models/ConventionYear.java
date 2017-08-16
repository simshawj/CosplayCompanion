package com.jamessimshaw.cosplaycompanion.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.Exclude;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by james on 9/25/15.
 */
public class ConventionYear implements Parcelable {
    private long mStart;
    private long mEnd;
    private String mLocation;
    private String mDisplayName;
    private String mConventionId;
    private String mSubmitted;
    private Map<String, Boolean> mPhotoshoots;

    public ConventionYear() {
        // Required Default Constructor
    }

    public ConventionYear(Date start, Date end, String location, String displayName, String conventionId, String user) {
        mStart = start.getTime();
        mEnd = end.getTime();
        mLocation = location;
        mDisplayName = displayName;
        mConventionId = conventionId;
        mSubmitted = user;
        mPhotoshoots = new HashMap<>();
    }

    public ConventionYear(Parcel in) {
        mStart = in.readLong();
        mEnd = in.readLong();
        mLocation = in.readString();
        mDisplayName = in.readString();
    }

    public long getStartDate() {
        return mStart;
    }

    public void setStartDate(long start) {
        mStart = start;
    }

    public long getEndDate() {
        return mEnd;
    }

    public void setEndDate(long end) {
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

    public String getConventionId() {
        return mConventionId;
    }

    public String getSubmitted() {
        return mSubmitted;
    }

    public Map<String, Boolean> getPhotoshoots() {
        return mPhotoshoots;
    }

    public void addPhotoshoot(String id) {
        mPhotoshoots.put(id, true);
    }

    @Exclude
    public int getYear() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(mStart));
        return calendar.get(Calendar.YEAR);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(mStart);
        dest.writeLong(mEnd);
        dest.writeString(mLocation);
        dest.writeString(mDisplayName);
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

        return mStart == otherYear.mStart &&
                mEnd == otherYear.mEnd &&
                mLocation.equals(otherYear.mLocation);
    }

    //TODO: Double Check
    @Override
    public int hashCode() {
        int result = 17;

//        result = 37 * result + mStart;
//        result = 37 * result + mEnd;
        result = 37 * result + mLocation.hashCode();

        return result;
    }
}

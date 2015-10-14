package com.jamessimshaw.cosplaycompanion.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by james on 9/25/15.
 */
public class ConventionYear implements Parcelable {
    private Date mDate;
    private int mDays;
    private long mId;
    private long mConventionId;
    private String mLocation;

    public ConventionYear(long id, Date date, int days, long conventionId, String location) {
        mDate = date;
        mDays = days;
        mId = id;
        mConventionId = conventionId;
        mLocation = location;
    }

    public ConventionYear(Date date, int days, long conventionId, String location) {
        mDate = date;
        mDays = days;
        mConventionId = conventionId;
        mLocation = location;
    }

    public ConventionYear(Parcel in) {
        mDate = new Date(in.readLong());
        mDays = in.readInt();
        mId = in.readLong();
        mConventionId = in.readLong();
        mLocation = in.readString();
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public int getDays() {
        return mDays;
    }

    public void setDays(int days) {
        mDays = days;
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

    public String getYearAsString() {
        return Integer.toString(getYear());
    }

    public int getYear() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(mDate);
        return calendar.get(Calendar.YEAR);
    }

    public Date getEndDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(mDate);
        calendar.add(Calendar.DAY_OF_MONTH, mDays - 1);
        return calendar.getTime();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(mDate.getTime());
        dest.writeInt(mDays);
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
}

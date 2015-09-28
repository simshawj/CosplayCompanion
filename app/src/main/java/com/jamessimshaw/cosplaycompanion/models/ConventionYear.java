package com.jamessimshaw.cosplaycompanion.models;

import java.util.Date;

/**
 * Created by james on 9/25/15.
 */
public class ConventionYear {
    private Date mDate;
    private int mDays;
    private long mId;
    private long mConventionId;

    public ConventionYear(long id, Date date, int days, long conventionId) {
        mDate = date;
        mDays = days;
        mId = id;
        mConventionId = conventionId;
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
}

package com.jamessimshaw.cosplaycompanion.models;

import java.util.Date;

/**
 * Created by james on 9/25/15.
 */
public class ConventionYear {
    private Date mDate;
    private int mDays;
    private int mId;
    private int mConventionId;

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

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public int getConventionId() {
        return mConventionId;
    }

    public void setConventionId(int conventionId) {
        mConventionId = conventionId;
    }
}

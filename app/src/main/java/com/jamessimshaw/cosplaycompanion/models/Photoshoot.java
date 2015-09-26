package com.jamessimshaw.cosplaycompanion.models;

import java.util.Date;

/**
 * Created by james on 9/25/15.
 */
public class Photoshoot {
    private String mSeries;
    private Date mStart;
    private String mLocation;
    private String mDescription;
    private int mId;
    private int mConventionYearId;

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

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public int getConventionYearId() {
        return mConventionYearId;
    }

    public void setConventionYearId(int conventionYearId) {
        mConventionYearId = conventionYearId;
    }
}

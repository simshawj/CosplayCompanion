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
    private long mId;
    private long mConventionYearId;

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
}

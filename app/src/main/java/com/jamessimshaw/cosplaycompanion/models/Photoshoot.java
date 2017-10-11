package com.jamessimshaw.cosplaycompanion.models;

import java.util.Date;

/**
 * Created by james on 9/25/15.
 */
public class Photoshoot {
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

    public String getSubmitted() {
        return mSubmitted;
    }

    public void setSubmitted(String mSubmitted) {
        this.mSubmitted = mSubmitted;
    }

    public String getEventId() {
        return mEventId;
    }

    public void setEventId(String mEventId) {
        this.mEventId = mEventId;
    }
}

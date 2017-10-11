package com.jamessimshaw.cosplaycompanion.models;

import com.google.firebase.database.Exclude;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by james on 9/25/15.
 */
public class ConventionYear {
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

    public void setConventionId(String conventionId) {
        mConventionId = conventionId;
    }

    public String getSubmitted() {
        return mSubmitted;
    }

    public void setSubmitted(String submitted) {
        mSubmitted = submitted;
    }

    public Map<String, Boolean> getPhotoshoots() {
        return mPhotoshoots;
    }

    public void setPhotoshoots(Map<String, Boolean> photoshoots) {
        mPhotoshoots = photoshoots;
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
}

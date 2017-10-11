package com.jamessimshaw.cosplaycompanion.models;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by james on 9/25/15.
 */
public class Convention {

    private String mName;
    private String mDescription;
    private String mLogoUriString;
    private String mSubmitted;
    private Map<String, Boolean> mEvents;

    public Convention() {
        // Required empty constructor
    }

    public Convention(String name, String description, String logoUriString, String submitted) {
        mName = name;
        mDescription = description;
        mLogoUriString = logoUriString;
        mSubmitted = submitted;
        mEvents = new HashMap<>();
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getLogoUriString() {
        return mLogoUriString;
    }

    public void setLogoUriString(String logoUriString) {
        mLogoUriString = logoUriString;
    }

    public String getSubmitted() {
        return mSubmitted;
    }

    public void setSubmitted(String submitted) {
        mSubmitted = submitted;
    }

    public Map<String, Boolean> getEvents() {
        return mEvents;
    }

    public void setEvents(Map<String, Boolean> events) {
        mEvents = events;
    }

    public void addEvent(String eventId) {
        mEvents.put(eventId, true);
    }
}

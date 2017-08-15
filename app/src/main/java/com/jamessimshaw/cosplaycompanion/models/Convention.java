package com.jamessimshaw.cosplaycompanion.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Map;

/**
 * Created by james on 9/25/15.
 */
public class Convention implements Parcelable {

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
    }

    public Convention (Parcel in) {
        mName = in.readString();
        mDescription = in.readString();
        mLogoUriString = in.readString();
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

    public String getLogoUriString() {
        return mLogoUriString;
    }

    public void setLogoUriString(String logoUriString) {
        mLogoUriString = logoUriString;
    }

    public void setName(String name) {
        mName = name;
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

    @Override
    public int describeContents() {
        return 0;
    }

    // TODO: Verify this
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mName);
        dest.writeString(mDescription);
        dest.writeString(mLogoUriString);
    }

    public static final Parcelable.Creator<Convention> CREATOR
            = new Parcelable.Creator<Convention>() {

        @Override
        public Convention createFromParcel(Parcel source) {
            return new Convention(source);
        }

        @Override
        public Convention[] newArray(int size) {
            return new Convention[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Convention))
            return false;

        Convention otherConvention = (Convention) o;

        return mName.equals(otherConvention.mName) &&
                mDescription.equals(otherConvention.mDescription) &&
                mLogoUriString.equals(otherConvention.mLogoUriString);
    }

    @Override
    public int hashCode() {
        int result = 17;

        result = 37 * result + mName.hashCode();
        result = 37 * result + mDescription.hashCode();
        result = 37 * result + mLogoUriString.hashCode();

        return result;
    }
}

package com.jamessimshaw.cosplaycompanion.presenters;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jamessimshaw.cosplaycompanion.models.ConventionYear;
import com.jamessimshaw.cosplaycompanion.models.Photoshoot;
import com.jamessimshaw.cosplaycompanion.views.ModifyPhotoshootView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.inject.Inject;

/**
 * Created by james on 2/19/16.
 */
public class ModifyPhotoshootPresenterImpl implements ModifyPhotoshootPresenter {
    private ModifyPhotoshootView mView;
    private Photoshoot mPhotoshoot;
    private ConventionYear mConventionYear;
    private Calendar mStart;
    private DatabaseReference mDatabaseReference;

    @Inject
    public ModifyPhotoshootPresenterImpl() {
        mDatabaseReference = FirebaseDatabase.getInstance().getReference("photoshoots");
    }

    // ModifyPhotoshootPresenter methods

    @Override
    public void setView(ModifyPhotoshootView view) {
        mView = view;
    }

    @Override
    public void setConventionYear(ConventionYear conventionYear) {
        mConventionYear = conventionYear;
    }

    @Override
    public void setPhotoshoot(Photoshoot photoshoot) {
        mPhotoshoot = photoshoot;
    }

    @Override
    public void detachView() {
        mView = null;
    }

    @Override
    public void requestInitialData() {
        mStart = Calendar.getInstance();
        if(mPhotoshoot != null) {
            mStart.setTime(new Date(mPhotoshoot.getStart()));
            mView.displayLocation(mPhotoshoot.getLocation());
            mView.displaySeries(mPhotoshoot.getSeries());
            mView.displayDescription(mPhotoshoot.getDescription());
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE MMMM dd", Locale.getDefault());
        mView.updateDate(dateFormat.format(mStart.getTime()));

        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm aa", Locale.getDefault());
        mView.updateTime(timeFormat.format(mStart.getTime()));
    }

    @Override
    public void submit() {
        String series = mView.getSeries();
        String location = mView.getLocation();
        String description = mView.getDescription();
        if (mPhotoshoot == null) {
            mPhotoshoot = new Photoshoot(series, mStart.getTime(), location, description);
        } else {
            mPhotoshoot.setDescription(description);
            mPhotoshoot.setLocation(location);
            mPhotoshoot.setSeries(series);
            mPhotoshoot.setStart(mStart.getTimeInMillis());
        }
        String conYearDisplayName = mConventionYear.getDisplayName();
        mDatabaseReference.child(conYearDisplayName.substring(0, conYearDisplayName.length()-4))
                .child(conYearDisplayName.substring(conYearDisplayName.length() - 5))
                .child(mPhotoshoot.getSeries() + " " + mPhotoshoot.getStart()).setValue(mPhotoshoot);
    }

    @Override
    public void dateChanged(int year, int month, int day) {
        mStart.set(year, month, day);
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE MMMM dd", Locale.getDefault());
        mView.updateDate(dateFormat.format(mStart.getTime()));
    }

    @Override
    public void timeChanged(int hour, int minute) {
        mStart.set(Calendar.HOUR_OF_DAY, hour);
        mStart.set(Calendar.MINUTE, minute);
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm aa", Locale.getDefault());
        mView.updateTime(timeFormat.format(mStart.getTime()));
    }
}

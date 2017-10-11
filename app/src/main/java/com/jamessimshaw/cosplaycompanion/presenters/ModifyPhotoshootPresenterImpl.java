package com.jamessimshaw.cosplaycompanion.presenters;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
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
    private DatabaseReference mUsersRef;
    private ModifyPhotoshootView mView;
    private Photoshoot mPhotoshoot;
    private DatabaseReference mPhotoshootRef;
    private DatabaseReference mConventionYearRef;
    private Calendar mStart;
    private DatabaseReference mDatabaseReference;

    @Inject
    public ModifyPhotoshootPresenterImpl() {
        mDatabaseReference = FirebaseDatabase.getInstance().getReference("photoshoots");
        mUsersRef = FirebaseDatabase.getInstance().getReference("users");
    }

    // ModifyPhotoshootPresenter methods

    @Override
    public void setView(ModifyPhotoshootView view) {
        mView = view;
    }

    @Override
    public void setConventionYear(DatabaseReference conventionYearRef) {
        mConventionYearRef = conventionYearRef;
    }

    @Override
    public void setPhotoshoot(DatabaseReference photoshootRef) {
        mPhotoshootRef = photoshootRef;
    }

    @Override
    public void detachView() {
        mView = null;
    }

    @Override
    public void requestInitialData() {
        mStart = Calendar.getInstance();

        if (mPhotoshootRef != null) {
            mPhotoshootRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    mPhotoshoot = dataSnapshot.getValue(Photoshoot.class);
                    if (mView != null) {
                        mStart.setTime(new Date(mPhotoshoot.getStart()));
                        mView.displayLocation(mPhotoshoot.getLocation());
                        mView.displaySeries(mPhotoshoot.getSeries());
                        mView.displayDescription(mPhotoshoot.getDescription());

                        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE MMMM dd", Locale.getDefault());
                        mView.updateDate(dateFormat.format(mStart.getTime()));

                        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm aa", Locale.getDefault());
                        mView.updateTime(timeFormat.format(mStart.getTime()));
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

        if (mView != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE MMMM dd", Locale.getDefault());
            mView.updateDate(dateFormat.format(mStart.getTime()));

            SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm aa", Locale.getDefault());
            mView.updateTime(timeFormat.format(mStart.getTime()));
        }
    }

    @Override
    public void submit() {
        String series = mView.getSeries();
        String location = mView.getLocation();
        String description = mView.getDescription();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (mPhotoshootRef == null) {
            mPhotoshootRef = mDatabaseReference.push();
            mUsersRef.child(user.getUid()).child("photoshoots").child(mPhotoshootRef.getKey()).setValue(true);
            mConventionYearRef.child("photoshoots").child(mPhotoshootRef.getKey()).setValue(true);
        } else {
            // TODO: Verify we can edit
        }

        if (mPhotoshoot == null) {
            mPhotoshoot = new Photoshoot(series, mStart.getTime(), location, description, mConventionYearRef.getKey(), user.getUid());
        } else {
            mPhotoshoot.setDescription(description);
            mPhotoshoot.setLocation(location);
            mPhotoshoot.setSeries(series);
            mPhotoshoot.setStart(mStart.getTimeInMillis());
        }
        mPhotoshootRef.setValue(mPhotoshoot);
        mView.done();
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

    @Override
    public boolean isEditMode() {
        return mPhotoshootRef != null;
    }
}

package com.jamessimshaw.cosplaycompanion.presenters;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jamessimshaw.cosplaycompanion.models.Convention;
import com.jamessimshaw.cosplaycompanion.models.ConventionYear;
import com.jamessimshaw.cosplaycompanion.views.ModifyConventionYearView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.inject.Inject;

/**
 * Created by james on 2/18/16.
 */
public class ModifyConventionYearPresenterImpl implements ModifyConventionYearPresenter {
    private DatabaseReference mUsersRef;
    private ModifyConventionYearView mView;
    private DatabaseReference mConventionRef;
    private DatabaseReference mConventionYearRef;
    private ConventionYear mConventionYear;
    private Convention mConvention;
    private SimpleDateFormat mDateFormat;
    private Date mStartDate;
    private Date mEndDate;
    private DatabaseReference mDatabaseReference;

    @Inject
    public ModifyConventionYearPresenterImpl() {
        mDatabaseReference = FirebaseDatabase.getInstance().getReference("events");
        mUsersRef = FirebaseDatabase.getInstance().getReference("users");
    }

    @Override
    public void setView(ModifyConventionYearView view) {
        mView = view;
    }

    @Override
    public void setConvention(DatabaseReference convention) {
        mConventionRef = convention;
    }

    @Override
    public void setConventionYear(DatabaseReference conventionYear) {
        mConventionYearRef = conventionYear;
    }

    @Override
    public void detachView() {
        mView = null;
    }

    @Override
    public void requestInitialData() {
        mDateFormat = new SimpleDateFormat("EEEE MMMM dd yyyy", Locale.getDefault());
        Calendar calendar = Calendar.getInstance();
        mStartDate = calendar.getTime();
        mEndDate = calendar.getTime();
        mView.displayStartDate(mDateFormat.format(mStartDate));
        mView.displayFinishDate(mDateFormat.format(mEndDate));

        if (mConventionYearRef != null) {
            mConventionYearRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    mConventionYear = dataSnapshot.getValue(ConventionYear.class);
                    mStartDate = new Date(mConventionYear.getStartDate());
                    mEndDate = new Date(mConventionYear.getEndDate());
                    if (mView != null) {
                        mView.displayStartDate(mDateFormat.format(mStartDate));
                        mView.displayFinishDate(mDateFormat.format(mEndDate));
                        mView.displayLocation(mConventionYear.getLocation());
                        mView.displayDisplayName(mConventionYear.getDisplayName());
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

        if (mConventionRef != null) {
            mConventionRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    mConvention = dataSnapshot.getValue(Convention.class);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }

    @Override
    public void setStartDate(Date date) {
        mStartDate = date;
        mView.displayStartDate(mDateFormat.format(mStartDate));
    }

    @Override
    public void setFinishDate(Date date) {
        mEndDate = date;
        mView.displayFinishDate(mDateFormat.format(mEndDate));
    }

    @Override
    public void submit() {
        String location = mView.getLocation();
        String displayName = mView.getDisplayName();

        if (mStartDate.after(mEndDate)) {
            mView.displayMessage("End date must be after the start date");
            return;
        }
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (mConventionYearRef == null) {
            // Create it
            mConventionYearRef = mDatabaseReference.push();
            mUsersRef.child(user.getUid()).child("events").child(mConventionYearRef.getKey()).setValue(true);
            mConventionRef.child("events").child(mConventionYearRef.getKey()).setValue(true);
        } else {
            // TODO: Verify we can edit
        }


        if (mConventionYear == null) {
            mConventionYear = new ConventionYear(mStartDate, mEndDate, location, displayName, mConventionRef.getKey(), user.getUid());
        } else {
            mConventionYear.setStartDate(mStartDate.getTime());
            mConventionYear.setEndDate(mEndDate.getTime());
            mConventionYear.setLocation(location);
            mConventionYear.setDisplayName(displayName);
        }
        mConventionYearRef.setValue(mConventionYear);
        mView.done();
    }
}

package com.jamessimshaw.cosplaycompanion.presenters;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jamessimshaw.cosplaycompanion.models.ConventionYear;
import com.jamessimshaw.cosplaycompanion.views.ListPhotoshootsView;

/**
 * Created by james on 7/23/16.
 */
public class ListPhotoshootsPresenterImpl implements ListPhotoshootsPresenter {

    private ListPhotoshootsView mView;
    private DatabaseReference mDatabaseReference;
    private DatabaseReference mConventionYearRef;

    public ListPhotoshootsPresenterImpl() {
        mDatabaseReference = FirebaseDatabase.getInstance().getReference("photoshoots");
    }

    @Override
    public void setView(ListPhotoshootsView view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }

    @Override
    public DatabaseReference getPhotoshootListRef() {
        return mConventionYearRef.child("photoshoots");
    }

    @Override
    public void setConventionYearRef(DatabaseReference conventionYearRef) {
        mConventionYearRef = conventionYearRef;

        mConventionYearRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ConventionYear event = dataSnapshot.getValue(ConventionYear.class);
                if (mView != null && event != null) {
                    mView.setTitle(event.getDisplayName());
                    mView.updateDates(event.getStartDate(), event.getEndDate());
                    mView.updateTitle(event.getDisplayName());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public DatabaseReference getPhotoshootDataRef() {
        return mDatabaseReference;
    }
}

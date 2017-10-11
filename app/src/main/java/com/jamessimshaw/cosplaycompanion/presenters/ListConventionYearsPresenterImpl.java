package com.jamessimshaw.cosplaycompanion.presenters;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jamessimshaw.cosplaycompanion.models.Convention;
import com.jamessimshaw.cosplaycompanion.views.ListConventionYearsView;

import javax.inject.Inject;

/**
 * Created by james on 2/23/16.
 */
public class ListConventionYearsPresenterImpl implements ListConventionYearsPresenter {

    private ListConventionYearsView mView;
    private DatabaseReference mDatabaseReference;
    private DatabaseReference mConventionReference;

    @Inject
    public ListConventionYearsPresenterImpl() {
        mDatabaseReference = FirebaseDatabase.getInstance().getReference("events");
    }

    @Override
    public void setView(ListConventionYearsView view) {
        mView = view;
        if (mConventionReference != null) {
            mConventionReference.addValueEventListener(mConventionListenter);
        }
    }

    @Override
    public void detachView() {
        mView = null;
        if (mConventionReference != null) {
            mConventionReference.removeEventListener(mConventionListenter);
        }
    }

    @Override
    public DatabaseReference getFirebaseReference(Convention convention) {
        return mDatabaseReference.child(convention.getName());
    }

    @Override
    public void setConventionReference(DatabaseReference conventionReference) {
        mConventionReference = conventionReference;
        mConventionReference.addValueEventListener(mConventionListenter);
    }

    @Override
    public DatabaseReference getEventsRef() {
        return mConventionReference.child("events");
    }

    @Override
    public DatabaseReference getEventsDataRef() {
        return mDatabaseReference;
    }

    private ValueEventListener mConventionListenter = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            Convention convention = dataSnapshot.getValue(Convention.class);
            if (mView != null && convention != null) {
                mView.setTitle(convention.getName());
                mView.updateConventionName(convention.getName());
                mView.updateConventionLogo(convention.getLogoUriString());
                mView.updateConventionDescription(convention.getDescription());
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };
}

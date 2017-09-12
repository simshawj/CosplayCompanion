package com.jamessimshaw.cosplaycompanion.presenters;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jamessimshaw.cosplaycompanion.models.Convention;
import com.jamessimshaw.cosplaycompanion.models.ConventionYear;
import com.jamessimshaw.cosplaycompanion.views.ListConventionYearsView;

import java.util.ArrayList;

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
        mDatabaseReference = FirebaseDatabase.getInstance().getReference("convention_years");
    }

    @Override
    public void setView(ListConventionYearsView view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }

    @Override
    public DatabaseReference getFirebaseReference(Convention convention) {
        return mDatabaseReference.child(convention.getName());
    }

    @Override
    public void setConventionReference(DatabaseReference conventionReference) {
        mConventionReference = conventionReference;
        mConventionReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (mView != null) {
                    mView.updateData(new ArrayList<ConventionYear>());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}

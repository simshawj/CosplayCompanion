package com.jamessimshaw.cosplaycompanion.presenters;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jamessimshaw.cosplaycompanion.models.Convention;
import com.jamessimshaw.cosplaycompanion.views.ListConventionYearsView;

import javax.inject.Inject;

/**
 * Created by james on 2/23/16.
 */
public class ListConventionYearsPresenterImpl implements ListConventionYearsPresenter {

    private ListConventionYearsView mView;
    private DatabaseReference mDatabaseReference;

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
}

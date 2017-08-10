package com.jamessimshaw.cosplaycompanion.presenters;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jamessimshaw.cosplaycompanion.models.ConventionYear;
import com.jamessimshaw.cosplaycompanion.views.ListPhotoshootsView;

/**
 * Created by james on 7/23/16.
 */
public class ListPhotoshootsPresenterImpl implements ListPhotoshootsPresenter {

    private ListPhotoshootsView mView;
    private DatabaseReference mDatabaseReference;

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
    public DatabaseReference getFirebaseReference(ConventionYear conventionYear) {
        String displayname = conventionYear.getDisplayName();
        return mDatabaseReference.child(displayname.substring(0, displayname.length() - 5))
                .child(displayname.substring(displayname.length() - 4, displayname.length()));
    }
}

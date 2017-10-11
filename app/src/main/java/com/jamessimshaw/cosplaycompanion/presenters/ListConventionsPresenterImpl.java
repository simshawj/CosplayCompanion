package com.jamessimshaw.cosplaycompanion.presenters;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jamessimshaw.cosplaycompanion.views.ListConventionsView;

import javax.inject.Inject;

/**
 * Created by james on 2/21/16.
 */
public class ListConventionsPresenterImpl implements ListConventionsPresenter {
    private ListConventionsView mView;
    private DatabaseReference mConventionsReference;

    @Inject
    public ListConventionsPresenterImpl() {
        mConventionsReference = FirebaseDatabase.getInstance().getReference("conventions");
    }

    @Override
    public void setView(ListConventionsView view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }

    @Override
    public DatabaseReference getFirebaseReference() {
        return mConventionsReference;
    }
}

package com.jamessimshaw.cosplaycompanion.presenters;

import com.google.firebase.database.DatabaseReference;
import com.jamessimshaw.cosplaycompanion.models.Convention;
import com.jamessimshaw.cosplaycompanion.views.ListConventionYearsView;

/**
 * Created by james on 2/23/16.
 */
public interface ListConventionYearsPresenter extends Presenter<ListConventionYearsView> {
    DatabaseReference getFirebaseReference(Convention convention);
    void setConventionReference(DatabaseReference conventionReference);
}

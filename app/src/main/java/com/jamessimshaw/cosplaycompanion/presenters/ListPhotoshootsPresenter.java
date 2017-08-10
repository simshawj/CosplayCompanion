package com.jamessimshaw.cosplaycompanion.presenters;

import com.google.firebase.database.DatabaseReference;
import com.jamessimshaw.cosplaycompanion.models.ConventionYear;
import com.jamessimshaw.cosplaycompanion.views.ListPhotoshootsView;

/**
 * Created by james on 7/23/16.
 */
public interface ListPhotoshootsPresenter extends Presenter<ListPhotoshootsView> {
    DatabaseReference getFirebaseReference(ConventionYear conventionYear);
}

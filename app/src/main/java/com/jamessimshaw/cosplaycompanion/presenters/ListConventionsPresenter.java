package com.jamessimshaw.cosplaycompanion.presenters;

import com.google.firebase.database.DatabaseReference;
import com.jamessimshaw.cosplaycompanion.views.ListConventionsView;

/**
 * Created by james on 2/21/16.
 */
public interface ListConventionsPresenter extends Presenter<ListConventionsView> {
    DatabaseReference getFirebaseReference();
}

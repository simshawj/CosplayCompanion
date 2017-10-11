package com.jamessimshaw.cosplaycompanion.presenters;

import com.google.firebase.database.DatabaseReference;
import com.jamessimshaw.cosplaycompanion.views.ModifyConventionView;

/**
 * Created by james on 2/18/16.
 */
public interface ModifyConventionPresenter extends Presenter<ModifyConventionView> {
    void requestInitialData();
    void setConvention(DatabaseReference convention);
    void submit();

    boolean isEditMode();
}

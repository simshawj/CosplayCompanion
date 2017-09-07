package com.jamessimshaw.cosplaycompanion.presenters;

import com.google.firebase.database.DatabaseReference;
import com.jamessimshaw.cosplaycompanion.models.ConventionYear;
import com.jamessimshaw.cosplaycompanion.models.Photoshoot;
import com.jamessimshaw.cosplaycompanion.views.ModifyPhotoshootView;

/**
 * Created by james on 2/19/16.
 */
public interface ModifyPhotoshootPresenter extends Presenter<ModifyPhotoshootView> {
    void setConventionYear(DatabaseReference conventionYearRef);
    void setPhotoshoot(DatabaseReference photoshootRef);
    void requestInitialData();
    void submit();
    void dateChanged(int year, int month, int day);
    void timeChanged(int hour, int minute);
}

package com.jamessimshaw.cosplaycompanion.presenters;

import com.google.firebase.database.DatabaseReference;
import com.jamessimshaw.cosplaycompanion.views.ModifyConventionYearView;

import java.util.Date;

/**
 * Created by james on 2/18/16.
 */
public interface ModifyConventionYearPresenter extends Presenter<ModifyConventionYearView> {
    void setConvention(DatabaseReference convention);
    void setConventionYear(DatabaseReference conventionYear);
    void requestInitialData();
    void setStartDate(Date date);
    void setFinishDate(Date date);
    void submit();

    boolean isEditMode();
}

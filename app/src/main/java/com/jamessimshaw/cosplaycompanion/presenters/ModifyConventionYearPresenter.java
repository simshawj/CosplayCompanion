package com.jamessimshaw.cosplaycompanion.presenters;

import com.jamessimshaw.cosplaycompanion.models.Convention;
import com.jamessimshaw.cosplaycompanion.models.ConventionYear;
import com.jamessimshaw.cosplaycompanion.views.ModifyConventionYearView;

import java.util.Date;

/**
 * Created by james on 2/18/16.
 */
public interface ModifyConventionYearPresenter {
    void setView(ModifyConventionYearView view);
    void setConvention(Convention convention);
    void setConventionYear(ConventionYear conventionYear);
    void removeView(ModifyConventionYearView view);
    void requestInitialData();
    void setStartDate(Date date);
    void setFinishDate(Date date);
    void submit();
}

package com.jamessimshaw.cosplaycompanion.presenters;

import com.jamessimshaw.cosplaycompanion.models.ConventionYear;
import com.jamessimshaw.cosplaycompanion.models.Photoshoot;
import com.jamessimshaw.cosplaycompanion.views.ModifyPhotoshootView;

/**
 * Created by james on 2/19/16.
 */
public interface ModifyPhotoshootPresenter {
    void setView(ModifyPhotoshootView view);
    void setConventionYear(ConventionYear convention);
    void setPhotoshoot(Photoshoot conventionYear);
    void removeView(ModifyPhotoshootView view);
    void requestInitialData();
    void submit();
    void dateChanged(int year, int month, int day);
    void timeChanged(int hour, int minute);
}

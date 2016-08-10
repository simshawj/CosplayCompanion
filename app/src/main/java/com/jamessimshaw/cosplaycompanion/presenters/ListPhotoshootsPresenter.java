package com.jamessimshaw.cosplaycompanion.presenters;

import com.jamessimshaw.cosplaycompanion.models.ConventionYear;
import com.jamessimshaw.cosplaycompanion.views.ListPhotoshootsView;

/**
 * Created by james on 7/23/16.
 */
public interface ListPhotoshootsPresenter extends Presenter<ListPhotoshootsView> {
    void requestPhotoshoots();
    void requestNewPhotoshoots();
    void setConventionYear(ConventionYear convention);
}

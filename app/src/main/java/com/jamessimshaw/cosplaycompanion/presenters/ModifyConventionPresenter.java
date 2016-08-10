package com.jamessimshaw.cosplaycompanion.presenters;

import com.jamessimshaw.cosplaycompanion.views.ModifyConventionView;

/**
 * Created by james on 2/18/16.
 */
public interface ModifyConventionPresenter extends Presenter<ModifyConventionView> {
    void requestInitialData();
    void submit();
}

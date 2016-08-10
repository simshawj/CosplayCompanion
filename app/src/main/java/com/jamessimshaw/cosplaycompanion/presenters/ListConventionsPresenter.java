package com.jamessimshaw.cosplaycompanion.presenters;

import com.jamessimshaw.cosplaycompanion.views.ListConventionsView;

/**
 * Created by james on 2/21/16.
 */
public interface ListConventionsPresenter extends Presenter<ListConventionsView> {
    void requestConventions();
    void requestNewConventions();
}

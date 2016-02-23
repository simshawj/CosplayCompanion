package com.jamessimshaw.cosplaycompanion.presenters;

import com.jamessimshaw.cosplaycompanion.views.ListConventionsView;

/**
 * Created by james on 2/21/16.
 */
public interface ListConventionsPresenter {
    void requestConventions();
    void requestNewConventions();
    void setView(ListConventionsView view);
    void removeView(ListConventionsView view);
}

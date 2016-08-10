package com.jamessimshaw.cosplaycompanion.presenters;

import com.jamessimshaw.cosplaycompanion.views.MVPView;

/**
 * Created by james on 2/23/16.
 */
public interface Presenter<V extends MVPView> {
    void setView(V view);
    void removeView(V view);
}

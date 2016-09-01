package com.jamessimshaw.cosplaycompanion.presenters;

import com.jamessimshaw.cosplaycompanion.views.LoginView;

/**
 * Created by james on 8/19/16.
 */
public interface LoginPresenter extends Presenter<LoginView> {
    void login();
    void verifyToken();
}

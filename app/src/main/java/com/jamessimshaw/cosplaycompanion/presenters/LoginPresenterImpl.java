package com.jamessimshaw.cosplaycompanion.presenters;

import com.jamessimshaw.cosplaycompanion.views.LoginView;

/**
 * Created by james on 8/19/16.
 */
public class LoginPresenterImpl implements LoginPresenter {

    private LoginView mView;

    // LoginPresenter methods

    @Override
    public void login() {

    }

    @Override
    public void setView(LoginView view) {
        mView = view;
    }

    @Override
    public void removeView(LoginView view) {
        if(mView.equals(view))
            mView = null;
    }
}

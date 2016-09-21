package com.jamessimshaw.cosplaycompanion.dagger.components;

import com.jamessimshaw.cosplaycompanion.dagger.modules.CosplayCompanionAPIModule;
import com.jamessimshaw.cosplaycompanion.dagger.modules.TokenManagerModule;
import com.jamessimshaw.cosplaycompanion.dagger.modules.UserManagerModule;
import com.jamessimshaw.cosplaycompanion.presenters.LoginPresenterImpl;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by james on 9/20/16.
 */
@Singleton
@Component(modules = {
        UserManagerModule.class,
        CosplayCompanionAPIModule.class,
        TokenManagerModule.class
})
public interface LoginComponents {
    void inject(LoginPresenterImpl presenter);
}

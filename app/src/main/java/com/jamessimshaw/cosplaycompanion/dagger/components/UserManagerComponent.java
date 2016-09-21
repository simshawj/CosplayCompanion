package com.jamessimshaw.cosplaycompanion.dagger.components;

import com.jamessimshaw.cosplaycompanion.dagger.modules.UserManagerModule;
import com.jamessimshaw.cosplaycompanion.presenters.LoginPresenterImpl;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by james on 9/20/16.
 */
@Singleton
@Component(modules = { UserManagerModule.class })
public interface UserManagerComponent {
    //void inject(LoginPresenterImpl presenter);
}

package com.jamessimshaw.cosplaycompanion.dagger.components;

/**
 * Created by james on 8/29/16.
 */

import com.jamessimshaw.cosplaycompanion.dagger.modules.ApplicationModule;
import com.jamessimshaw.cosplaycompanion.dagger.modules.PreferenceModule;
import com.jamessimshaw.cosplaycompanion.datasources.TokenManagerImpl;
import com.jamessimshaw.cosplaycompanion.datasources.UserManagerImpl;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {PreferenceModule.class} )
public interface PreferenceComponent {
    void inject(TokenManagerImpl tokenManager);
    void inject(UserManagerImpl userManager);
}

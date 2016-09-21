package com.jamessimshaw.cosplaycompanion.dagger.modules;

import com.jamessimshaw.cosplaycompanion.datasources.UserManager;
import com.jamessimshaw.cosplaycompanion.datasources.UserManagerImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by james on 9/20/16.
 */
@Module
public class UserManagerModule {
    @Provides
    @Singleton
    UserManager provideUserManager() {
        return new UserManagerImpl();
    }
}

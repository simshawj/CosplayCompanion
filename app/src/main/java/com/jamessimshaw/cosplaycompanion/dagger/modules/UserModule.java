package com.jamessimshaw.cosplaycompanion.dagger.modules;

import com.jamessimshaw.cosplaycompanion.models.User;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by james on 8/24/16.
 */
@Module
public class UserModule {

    @Provides
    @Singleton
    User providesUser() {
        return new User();
    }
}

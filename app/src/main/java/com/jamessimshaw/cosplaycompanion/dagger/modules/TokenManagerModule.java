package com.jamessimshaw.cosplaycompanion.dagger.modules;

import com.jamessimshaw.cosplaycompanion.datasources.TokenManager;
import com.jamessimshaw.cosplaycompanion.datasources.TokenManagerImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by james on 8/25/16.
 */
@Module
public class TokenManagerModule {
    @Provides
    @Singleton
    TokenManager providesTokenManager() {
        return new TokenManagerImpl();
    }
}

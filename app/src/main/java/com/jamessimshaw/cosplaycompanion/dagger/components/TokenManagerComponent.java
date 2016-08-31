package com.jamessimshaw.cosplaycompanion.dagger.components;

import com.jamessimshaw.cosplaycompanion.dagger.modules.TokenManagerModule;
import com.jamessimshaw.cosplaycompanion.datasources.interceptors.AuthenticationInterceptor;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by james on 8/25/16.
 */
@Singleton
@Component(modules = {TokenManagerModule.class})
public interface TokenManagerComponent {
    void inject(AuthenticationInterceptor authenticationInterceptor);
}

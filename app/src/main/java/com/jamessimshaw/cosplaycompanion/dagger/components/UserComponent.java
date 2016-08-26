package com.jamessimshaw.cosplaycompanion.dagger.components;

import com.jamessimshaw.cosplaycompanion.dagger.modules.UserModule;
import com.jamessimshaw.cosplaycompanion.datasources.interceptors.AuthenticationInterceptor;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by james on 8/24/16.
 */
@Singleton
@Component(modules = {UserModule.class})
public interface UserComponent {
    void inject(AuthenticationInterceptor inteceptor);
}

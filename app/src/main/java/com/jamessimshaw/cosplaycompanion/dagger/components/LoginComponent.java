package com.jamessimshaw.cosplaycompanion.dagger.components;

import com.jamessimshaw.cosplaycompanion.activities.LoginActivity;
import com.jamessimshaw.cosplaycompanion.activities.RegisterActivity;
import com.jamessimshaw.cosplaycompanion.dagger.modules.CosplayCompanionAPIModule;
import com.jamessimshaw.cosplaycompanion.dagger.modules.LoginModule;
import com.jamessimshaw.cosplaycompanion.dagger.modules.PreferenceModule;
import com.jamessimshaw.cosplaycompanion.dagger.modules.TokenManagerModule;
import com.jamessimshaw.cosplaycompanion.dagger.modules.UserManagerModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by james on 9/20/16.
 */
@Singleton
@Component(modules = { LoginModule.class, CosplayCompanionAPIModule.class, UserManagerModule.class,
        TokenManagerModule.class })
public interface LoginComponent {
    void inject(LoginActivity activity);
    void inject(RegisterActivity activity);
}
